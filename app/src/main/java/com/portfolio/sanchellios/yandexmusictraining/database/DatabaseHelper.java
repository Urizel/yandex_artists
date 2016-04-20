package com.portfolio.sanchellios.yandexmusictraining.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.*;

/**
 * Created by aleksandrvasilenko on 17.04.16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "artists.db";
    public static final int SCHEMA = 5;
    private static DatabaseHelper instance = null;

    final String SQL_CREATE_ARTIST_TABLE_SCRIPT =
            "CREATE TABLE " + ArtistTable.TABLE_NAME + " (" +
                    ArtistTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ArtistTable.NAME + " TEXT NOT NULL," +
                    ArtistTable.GENRES + " TEXT NOT NULL," +
                    ArtistTable.TRACKS + " INTEGER NOT NULL," +
                    ArtistTable.ALBUMS + " INTEGER NOT NULL," +
                    ArtistTable.LINK + " TEXT," +
                    ArtistTable.DESCRIPTION + " TEXT NOT NULL," +
                    ArtistTable.SMALL_COVER_LINK + " TEXT NOT NULL," +
                    ArtistTable.BIG_COVER_LINK + " TEXT NOT NULL, " +
                    ArtistTable.ARTIST_ID + " INTEGER" +");";
    final String SQL_CREATE_CACHE_REG_TABLE_SCRIPT =
            "CREATE TABLE " + CacheRegistryTable.TABLE_NAME + " (" +
                    CacheRegistryTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CacheRegistryTable.TIME + " TEXT NOT NULL);";
    final String SQL_CREATE_SMALL_COVER_TABLE_SCRIPT =
            "CREATE TABLE " + SmallCoverTable.TABLE_NAME + " (" +
                    SmallCoverTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SmallCoverTable.COVER + " BLOB NOT NULL, " +
                    SmallCoverTable.ARTIST_NAME + " TEXT NOT NULL, "+
                    SmallCoverTable.ARTIST_ID + " INTEGER NOT_NULL);";
    final String SQL_CREATE_BIG_COVER_TABLE_SCRIPT =
            "CREATE TABLE " + BigCoverTable.TABLE_NAME + " (" +
                    BigCoverTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BigCoverTable.COVER + " BLOB NOT NULL, " +
                    BigCoverTable.ARTIST_NAME + " TEXT NOT NULL, "+
                    BigCoverTable.ARTIST_ID + " INTEGER NOT_NULL);";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTIST_TABLE_SCRIPT);
        db.execSQL(SQL_CREATE_CACHE_REG_TABLE_SCRIPT);
        db.execSQL(SQL_CREATE_SMALL_COVER_TABLE_SCRIPT);
        db.execSQL(SQL_CREATE_BIG_COVER_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String ALTER_TABLE = "ALTER TABLE";
        final String DROP_TABLE = "DROP TABLE";
        final String TMP_TABLE = "tmp_table";
        final char _ = ' ';
        if(oldVersion == 1 && newVersion == 2){
            db.execSQL(ALTER_TABLE + _ + ArtistTable.TABLE_NAME + _ + "RENAME TO" + _ + TMP_TABLE + ";");
            db.execSQL(SQL_CREATE_ARTIST_TABLE_SCRIPT);
            db.execSQL(DROP_TABLE + _ + TMP_TABLE + ";");
        }

        if(oldVersion == 3 && newVersion == 4){
            db.execSQL(ALTER_TABLE + _ + ArtistTable.TABLE_NAME + _ + "RENAME TO" + _ + TMP_TABLE + ";");
            db.execSQL(SQL_CREATE_ARTIST_TABLE_SCRIPT);
            db.execSQL(DROP_TABLE + _ + TMP_TABLE + ";");
        }

        if(oldVersion == 4 && newVersion == 5){
            //db.execSQL(DROP_TABLE + _ + "image_blob_table" + ";");
            db.execSQL(SQL_CREATE_SMALL_COVER_TABLE_SCRIPT);
            db.execSQL(SQL_CREATE_BIG_COVER_TABLE_SCRIPT);
        }
    }

    public void deleteFromTables(){
        final String DELETE_FROM = "DELETE FROM ";
        final String END = ";";
        SQLiteDatabase db = instance.getWritableDatabase();
        db.execSQL(DELETE_FROM + ArtistTable.TABLE_NAME + END);
        db.execSQL(DELETE_FROM + CacheRegistryTable.TABLE_NAME + END);
        db.execSQL(DELETE_FROM + SmallCoverTable.TABLE_NAME + END);
        db.execSQL(DELETE_FROM + BigCoverTable.TABLE_NAME + END);
        Log.d(DELETE_FROM, "tables are truncated");
    }
}
