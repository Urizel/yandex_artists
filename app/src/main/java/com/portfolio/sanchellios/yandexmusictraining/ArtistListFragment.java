package com.portfolio.sanchellios.yandexmusictraining;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by aleksandrvasilenko on 10.04.16.
 */
public class ArtistListFragment extends Fragment {
    private ArrayList<Artist> artists;
    private AsyncTask task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView artistRecycler = (RecyclerView)inflater
                .inflate(R.layout.activity_list_of_artists,
                        container,
                        false);
        //task = new JSONLoader().execute("http://cache-default04g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json");
        ArtistListAdapter adapter = new ArtistListAdapter(artists, getContext().getApplicationContext());
        artistRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistRecycler.setLayoutManager(layoutManager);
        return artistRecycler;
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
        }
    }
}
