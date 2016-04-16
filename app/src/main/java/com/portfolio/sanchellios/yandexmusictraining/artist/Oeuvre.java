package com.portfolio.sanchellios.yandexmusictraining.artist;

import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;

/**
 * Created by aleksandrvasilenko on 16.04.16.
 */
public class Oeuvre {
    private int tracks;
    private int albums;

    public Oeuvre(){}
    public Oeuvre(Artist artist){
        this.tracks = artist.getNumberOfTracks();
        this.albums = artist.getNumberOfAlbums();
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }
}
