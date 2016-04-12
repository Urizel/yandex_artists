package com.portfolio.sanchellios.yandexmusictraining;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by aleksandrvasilenko on 03.04.16.
 */
public class Artist implements Parcelable{
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

    protected Artist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        genres = in.createStringArrayList();
        tracks = in.readInt();
        albums = in.readInt();
        link = in.readString();
        description = in.readString();
        cover = in.readParcelable(Cover.class.getClassLoader());
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

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

    @Override
    public String toString() {
        return "" + getId() + "::" + getName() + "::" + getDescription();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeStringList(genres);
        dest.writeInt(tracks);
        dest.writeInt(albums);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeParcelable(cover, flags);
    }
}
