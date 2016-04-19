package com.portfolio.sanchellios.yandexmusictraining.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by aleksandrvasilenko on 17.04.16.
 */
public class ImageDbManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ImageDbManager(Context context){
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public void insertSmallCoverIntoDb(Bitmap image){
        insertCover(image, DBContracts.ArtistTable.SMALL_COVER);
    }

    public void insertBigCoverIntoDb(Bitmap image){
        insertCover(image, DBContracts.ArtistTable.BIG_COVER);
    }

    public Bitmap getSmallCoverFromDb(int artistId){
        return getCover(artistId, DBContracts.ArtistTable.SMALL_COVER);
    }

    public Bitmap getBigCoverFromDb(int artistId){
        return getCover(artistId, DBContracts.ArtistTable.BIG_COVER);
    }

    private void insertCover(Bitmap image, String coverColumn){
        byte[] coverBytes = getBitmapAsByteArray(image);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(coverColumn, coverBytes);
        db.insert(DBContracts.ArtistTable.TABLE_NAME,
                null,
                values);
    }

    private Bitmap getCover(int artistId, String columnName){
        db = dbHelper.getReadableDatabase();
        String query = "SELECT " + columnName +
                " FROM " + DBContracts.ArtistTable.TABLE_NAME +
                " WHERE " + DBContracts.ArtistTable._ID + " = " + artistId + ";";

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
}
