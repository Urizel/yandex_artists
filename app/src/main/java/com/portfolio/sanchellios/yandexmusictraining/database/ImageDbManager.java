package com.portfolio.sanchellios.yandexmusictraining.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;

import java.io.ByteArrayOutputStream;

import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.BigCoverTable;
import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.CoverTable;
import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.SmallCoverTable;

/**
 * Created by aleksandrvasilenko on 17.04.16.
 */
public class ImageDbManager {
    public static final String BIG_COVER = "BIG_COVER";
    public static final String SMALL_COVER = "SMALL_COVER";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Artist artist;

    public ImageDbManager(Context context){
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public void insertSmallCoverIntoDb(Bitmap image, Artist artist){
        this.artist = artist;
        insertCover(image, SmallCoverTable.COVER);
    }

    public void insertBigCoverIntoDb(Bitmap image, Artist artist){
        this.artist = artist;
        insertCover(image, BigCoverTable.COVER);
    }

    public Bitmap getSmallCoverFromDb(int artistId, String artistName){
        return getCover(artistId, artistName, SmallCoverTable.TABLE_NAME);
    }

    public Bitmap getBigCoverFromDb(int artistId, String artistName){
        return getCover(artistId, artistName, BigCoverTable.TABLE_NAME);
    }

    private void insertCover(Bitmap image, String tableName){
        byte[] coverBytes = getBitmapAsByteArray(image);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CoverTable.COVER, coverBytes);
        values.put(CoverTable.ARTIST_ID, artist.getId());
        values.put(CoverTable.ARTIST_NAME, artist.getName());
        db.insert(tableName,
                null,
                values);
    }

    private Bitmap getCover(int artistId, String artistName, String tableNameTag){
        String tableName;
        if(tableNameTag.equals(BIG_COVER)){
            tableName = BigCoverTable.TABLE_NAME;
        }else {
            tableName = SmallCoverTable.TABLE_NAME;
        }
        db = dbHelper.getReadableDatabase();
        String query = "SELECT " + CoverTable.COVER +
                " FROM " + tableName +
                " WHERE " + CoverTable.ARTIST_ID + " = " + artistId +
                " AND " + CoverTable.ARTIST_NAME + " = '" + artistName+ "';";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            cursor.close();
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }

        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public boolean isImageExistInDb(int artistId, String imageSizeTag){
        final String tableName;
        if(imageSizeTag.equals(BIG_COVER)){
            tableName = BigCoverTable.TABLE_NAME;
        }else {
            tableName = SmallCoverTable.TABLE_NAME;
        }
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ CoverTable.ARTIST_ID +
                " FROM " + tableName + " WHERE " +
                CoverTable.ARTIST_ID + "=" + artistId + ";", null);
        int count = cursor.getCount();
        cursor.close();
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }
}
