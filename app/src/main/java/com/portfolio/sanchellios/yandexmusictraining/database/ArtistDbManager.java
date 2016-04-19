package com.portfolio.sanchellios.yandexmusictraining.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;
import com.portfolio.sanchellios.yandexmusictraining.artist.Cover;

import java.util.ArrayList;
import java.util.Iterator;

import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.ArtistTable;

/**
 * Created by aleksandrvasilenko on 17.04.16.
 */
public class ArtistDbManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    public ArtistDbManager(Context context){
        this.dbHelper = DatabaseHelper.getInstance(context);
    }
    public ArtistDbManager(DatabaseHelper dbHelper){this.dbHelper = dbHelper;}

    public int countTheArtists(){
        db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + ArtistTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void saveArtistToDb(Artist artist){
        if(!isArtistExistInDb(artist.getName())){
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ArtistTable.ARTIST_ID, artist.getId());
            values.put(ArtistTable.NAME, artist.getName());
            values.put(ArtistTable.GENRES, formatGenres(artist.getGenres()));
            values.put(ArtistTable.TRACKS, artist.getNumberOfTracks());
            values.put(ArtistTable.ALBUMS, artist.getNumberOfAlbums());
            values.put(ArtistTable.LINK, artist.getLink());
            values.put(ArtistTable.DESCRIPTION, artist.getDescription());
            values.put(ArtistTable.SMALL_COVER, artist.getCover().getSmallCover());
            values.put(ArtistTable.BIG_COVER, artist.getCover().getBigCover());
            db.insert(ArtistTable.TABLE_NAME,
                    null,
                    values);
        }
    }

    public ArrayList<Artist> getArtistsFromDb(){
        db = dbHelper.getReadableDatabase();
        ArrayList<Artist> artists = new ArrayList<>();
        String query = "SELECT "+ ArtistTable.ARTIST_ID +
                " FROM " + ArtistTable.TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        Log.d("Number of Artists in db", "" + count);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            artists.add(getArtistFromDb(cursor.getInt(cursor.getColumnIndex(ArtistTable.ARTIST_ID))));
            cursor.moveToNext();
        }
        cursor.close();
        return artists;
    }

    public static int getArtistCount(Context context){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String query = "SELECT "+ ArtistTable.ARTIST_ID +
                " FROM " + ArtistTable.TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    private Artist getArtistFromDb(int artistId){
        String query = "SELECT * FROM " + ArtistTable.TABLE_NAME +
                " WHERE " + ArtistTable.ARTIST_ID + "= '" + artistId +"';";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Artist artist = getArtist(cursor);
        cursor.close();
        return artist;
    }

    private Artist getArtist(Cursor cursor){
        int id, tracks, albums;
        String name, link, description;
        ArrayList<String> genres = splitGenresFromString(cursor);
        id = cursor.getInt(cursor.getColumnIndex(ArtistTable.ARTIST_ID));
        tracks = cursor.getInt(cursor.getColumnIndex(ArtistTable.TRACKS));
        albums = cursor.getInt(cursor.getColumnIndex(ArtistTable.ALBUMS));
        name = cursor.getString(cursor.getColumnIndex(ArtistTable.NAME));
        link = cursor.getString(cursor.getColumnIndex(ArtistTable.LINK));
        description = cursor.getString(cursor.getColumnIndex(ArtistTable.DESCRIPTION));
        return new Artist(id, name, genres, tracks, albums, link, description, getCoverURLs(cursor));
    }

    private Cover getCoverURLs(Cursor cursor){
        String smallCover, bigCover;
        smallCover = cursor.getString(cursor.getColumnIndex(ArtistTable.SMALL_COVER));
        bigCover = cursor.getString(cursor.getColumnIndex(ArtistTable.BIG_COVER));
        return new Cover(smallCover, bigCover);
    }

    private String formatGenres(ArrayList<String> genres){
        Iterator<String> iterator = genres.iterator();
        StringBuilder result = new StringBuilder();
        String comma = ", ";
        while (iterator.hasNext()){
            result.append(iterator.next());
            if(iterator.hasNext()){
                result.append(comma);
            }
        }
        return result.toString();
    }

    private ArrayList<String> splitGenresFromString(Cursor cursor){
        ArrayList<String> genresList = new ArrayList<>();
        String genresString = cursor.getString(cursor.getColumnIndex(ArtistTable.GENRES));
        String[] tokens = genresString.split(", ");
        for(int i = 0; i < tokens.length; i++){
            genresList.add(tokens[i]);
        }
        return genresList;
    }

    private boolean isArtistExistInDb(String name){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ArtistTable.TABLE_NAME+" WHERE "+ArtistTable.NAME+"='"+name+"';", null);
        int counter = cursor.getCount();
        cursor.close();
        if(counter > 0){
            return true;
        }else {
            return false;
        }
    }
}
