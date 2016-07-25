package com.portfolio.sanchellios.yandexmusictraining.database;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;

/**
 * Created by aleksandrvasilenko on 19.04.16.
 */
// XXX Why service?
public class ArtistCacheService extends IntentService {
    public static final String ARTIST = "ARTIST";
    public ArtistCacheService(){
        super("ArtistCacheService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Why not this?
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        Artist artist = intent.getParcelableExtra(ARTIST);
        ArtistDbManager dbManager = new ArtistDbManager(dbHelper);
        Log.d("Artist to DB: ", artist.getName());
        dbManager.saveArtistToDb(artist);
    }
}
