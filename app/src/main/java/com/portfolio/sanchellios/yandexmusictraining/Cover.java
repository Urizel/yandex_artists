package com.portfolio.sanchellios.yandexmusictraining;

/**
 * Created by aleksandrvasilenko on 03.04.16.
 */
public class Cover {
    private String small;
    private String big;
    public Cover(String small, String big){
        this.small = small;
        this.big = big;
    }

    public String getSmallCover() {
        //TODO:We should get a image
        return small;
    }

    public String getBigCover() {
        //TODO:We should get a image
        return big;
    }
}
