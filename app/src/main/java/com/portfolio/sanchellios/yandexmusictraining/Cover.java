package com.portfolio.sanchellios.yandexmusictraining;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aleksandrvasilenko on 03.04.16.
 */
public class Cover implements Parcelable{
    private String small;
    private String big;
    public Cover(String small, String big){
        this.small = small;
        this.big = big;
    }

    protected Cover(Parcel in) {
        small = in.readString();
        big = in.readString();
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel in) {
            return new Cover(in);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };

    public String getSmallCover() {
        //TODO:We should get a image
        return small;
    }

    public String getBigCover() {
        //TODO:We should get a image
        return big;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(small);
        dest.writeString(big);
    }
}
