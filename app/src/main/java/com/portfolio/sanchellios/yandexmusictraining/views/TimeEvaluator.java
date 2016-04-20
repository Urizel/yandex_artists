package com.portfolio.sanchellios.yandexmusictraining.views;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.portfolio.sanchellios.yandexmusictraining.database.DBContracts;
import com.portfolio.sanchellios.yandexmusictraining.database.DatabaseHelper;

import java.util.Calendar;

import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.CacheRegistryTable.TABLE_NAME;
import static com.portfolio.sanchellios.yandexmusictraining.database.DBContracts.CacheRegistryTable.TIME;

/**
 * Created by aleksandrvasilenko on 19.04.16.
 */
public class TimeEvaluator {
    private TimeHolder currentTime;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Calendar calendar;

    public TimeEvaluator(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
        calendar = Calendar.getInstance();
        updateTime();
    }

    public boolean shouldRegisterTime(){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContracts.CacheRegistryTable.TABLE_NAME + ";",
                null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            Log.d("Counting registry: ", "" + cursor.getCount());
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            TimeHolder timeFromDb = parseTimeString(time);
            cursor.close();
            return isFifteenMinPassed(timeFromDb);
        }else {
            cursor.close();
            return true;
        }
    }

    public void registerCurrentTimeToDb(){
        updateTime();
        dbHelper.deleteFromTables();
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, getCurrentTime());
        db.insert(TABLE_NAME, null, values);
    }

    public String getCurrentTime(){
        return ""+currentTime.dayOfWeek+":"+currentTime.hours+":"+currentTime.minutes;
    }

    private boolean isFifteenMinPassed(TimeHolder registeredTime){
        if(currentTime.dayOfWeek != registeredTime.dayOfWeek){
            return true;
        }

        if(currentTime.hours > registeredTime.hours){
            return true;
        }else if((currentTime.minutes - registeredTime.minutes) > 15){
            return true;
        }else {
            return false;
        }
    }

    private TimeHolder parseTimeString(String time){
        String[] tokens = time.split(":");
        int dayOfWeek = Integer.parseInt(tokens[0]);
        int hours = Integer.parseInt(tokens[1]);
        int minutes = Integer.parseInt(tokens[2]);
        return new TimeHolder(dayOfWeek, hours, minutes);
    }

    private void updateTime(){
        currentTime = new TimeHolder(
                calendar.get(Calendar.DAY_OF_WEEK),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE)
        );
    }
}
