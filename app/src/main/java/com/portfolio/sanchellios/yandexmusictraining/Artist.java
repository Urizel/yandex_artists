package com.portfolio.sanchellios.yandexmusictraining;

import java.util.ArrayList;

/**
 * Created by aleksandrvasilenko on 03.04.16.
 */
public class Artist {
    private int id;
    private String name;
    private ArrayList<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    public Artist(int id,
                  String name,
                  ArrayList<String> genres,
                  int tracks,
                  int albums,
                  String link,
                  String description,
                  Cover cover){
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getNumberOfTracks() {
        return tracks;
    }

    public int getNumberOfAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Cover getCover() {
        return cover;
    }
}
