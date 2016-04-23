package com.portfolio.sanchellios.yandexmusictraining.views;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.portfolio.sanchellios.yandexmusictraining.R;
import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;
import com.portfolio.sanchellios.yandexmusictraining.database.ArtistDbManager;
import com.portfolio.sanchellios.yandexmusictraining.database.DatabaseHelper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfArtistsActivity extends AppCompatActivity implements ArtistListFragment.ArtistListViewer {
    public static final String LAYOUT_POSITION = "LAYOUT_POSITION";
    private String saveArtistListToDbState = ArtistListFragment.SAVE_TO_DB_STATE;
    private final String ARTISTS = "ARTISTS";
    private ArrayList<Artist> artists = new ArrayList<>();
    private AsyncTask task;
    private final String LOAD_ARTISTS = "Load artists: ";
    private final String Y_URL = "http://cache-default04g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    private TimeEvaluator timeEvaluator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_of_artists);

        if(savedInstanceState == null){
            timeEvaluator = new TimeEvaluator(getApplicationContext());
            if(timeEvaluator.shouldRegisterTime()){
                loadArtistsFromInternet(timeEvaluator);
            }else {
                if(ArtistDbManager.getArtistCount(getApplicationContext()) > 0){
                    loadArtistsFromDb();
                }else {
                    loadArtistsFromInternet(timeEvaluator);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                DatabaseHelper dbHelper = DatabaseHelper.getInstance(getApplicationContext());
                dbHelper.deleteFromTables();
                if (timeEvaluator == null) {
                    timeEvaluator = new TimeEvaluator(getApplicationContext());
                }
                loadArtistsFromInternet(timeEvaluator);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadArtistsFromInternet(TimeEvaluator timeEvaluator){
        saveArtistListToDbState = ArtistListFragment.SAVE_TO_DB_STATE;
        Log.d(LOAD_ARTISTS, "loading from Internet");
        timeEvaluator.registerCurrentTimeToDb();
        task = new JSONLoader().execute(Y_URL);
    }

    private void loadArtistsFromDb(){
        Log.d(LOAD_ARTISTS, "loading from Database");
        saveArtistListToDbState = ArtistListFragment.IGNORE_SAVE;
        ArtistDbManager artistDbManager = new ArtistDbManager(getApplicationContext());
        artists = artistDbManager.getArtistsFromDb();
        initArtistListFrag();
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

    @Override
    public String getArtistSaveToDbState() {
        return saveArtistListToDbState;
    }

    void setArtists(ArrayList<Artist> artists){
        this.artists = artists;
    }

    private class JSONLoader extends AsyncTask<String, Void, ArrayList<Artist>> {
        ArrayList<Artist> artistsCollection = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutPositionManager positionSaver = new LayoutPositionManager(getApplicationContext());
            positionSaver.resetPosition();
        }

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
