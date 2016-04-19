package com.portfolio.sanchellios.yandexmusictraining.database;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by aleksandrvasilenko on 19.04.16.
 */
public class ArtistCacheService extends IntentService {
    public static final String ARTIST_LIST = "ARTIST_LIST";
    public ArtistCacheService(){
        super("ArtistCacheService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(getApplicationContext());
        ArrayList<Artist> list = intent.getParcelableArrayListExtra(ARTIST_LIST);
        Iterator iterator = list.iterator();
        ArtistDbManager dbManager = new ArtistDbManager(dbHelper);
        while (iterator.hasNext()){
            Artist artist = (Artist)iterator.next();
            Log.d("Artist to DB: ", artist.getName());
            dbManager.saveArtistToDb(artist);
        }
    }
}
