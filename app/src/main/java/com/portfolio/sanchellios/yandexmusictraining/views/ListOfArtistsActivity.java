package com.portfolio.sanchellios.yandexmusictraining.views;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.portfolio.sanchellios.yandexmusictraining.R;
import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;
import com.portfolio.sanchellios.yandexmusictraining.database.DatabaseHelper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfArtistsActivity extends AppCompatActivity implements ArtistListFragment.TaskKiller{
    private final String ARTISTS = "ARTISTS";
    private ArrayList<Artist> artists = new ArrayList<>();
    private AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_of_artists);

        //          FOR DEBUG - > DELETE AFTER
        truncateTables();


        final String YANDEX_URL = "http://cache-default04g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
        if(savedInstanceState == null){
            task = new JSONLoader().execute(YANDEX_URL);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARTISTS, artists);
        Log.d(ARTISTS, "Artists are saved: " + artists.size() + " instances");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        artists = savedInstanceState.getParcelableArrayList(ARTISTS);
        try {
            int size =  artists.size();
            Log.d(ARTISTS, "Artists are loaded: " + size + " instances");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void initArtistListFrag(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.artist_container, ArtistListFragment.newInstance(artists))
                .commit();
    }

    private void truncateTables(){
        DatabaseHelper helper = DatabaseHelper.getInstance(getApplicationContext());
        helper.deleteFromTables();
    }

    @Override
    protected void onDestroy() {
        killTask();
        super.onDestroy();
    }

    @Override
    public void killTask(){
        if(task != null){
            task.cancel(false);
        }
    }

    @Override
    public ArrayList<Artist> getArtistList() {
        return this.artists;
    }

    void setArtists(ArrayList<Artist> artists){
        this.artists = artists;
    }

    private class JSONLoader extends AsyncTask<String, Void, ArrayList<Artist>> {
        ArrayList<Artist> artistsCollection = new ArrayList<>();
        @Override
        protected ArrayList<Artist> doInBackground(String... params) {
            try{
                Gson gson = new Gson();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(params[0]).build();
                Response response = client.newCall(request).execute();

                if(response.isSuccessful()){
                    InputStream in = response.body().byteStream();
                    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                    reader.beginArray();
                    while (reader.hasNext()){
                        Artist artist = gson.fromJson(reader, Artist.class);
                        Log.d("ARTIST", artist.toString());
                        artistsCollection.add(artist);
                    }
                    reader.endArray();
                    reader.close();
                }else {
                    throw new IOException("Unexpected code" + response);
                }
                return artistsCollection;

            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Artist> artists) {
            setArtists(artists);
            initArtistListFrag();
        }
    }
}
