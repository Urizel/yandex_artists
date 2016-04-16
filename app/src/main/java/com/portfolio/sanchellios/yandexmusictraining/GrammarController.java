package com.portfolio.sanchellios.yandexmusictraining;

/**
 * Created by aleksandrvasilenko on 16.04.16.
 */
public abstract class GrammarController{
    protected final String oneSong = "песня";
    protected final String twoToFourSongs = "песни";
    protected final String fiveToTenSongs = "песен";
    protected final String oneAlbum = "альбом";
    protected final String twoToFourAlbums = "альбома";
    protected final String fiveToTenAlbums = "альбомов";

    abstract public String getCorrectString(int capacity);
}
