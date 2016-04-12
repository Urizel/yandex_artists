package com.portfolio.sanchellios.yandexmusictraining;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by aleksandrvasilenko on 08.04.16.
 */
public class ArtistInfoFormatter {
    public static String formGenreString(ArrayList<String> genres){
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = genres.iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next());
            if(iterator.hasNext()){
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public static String getAlbumsAndSongsForCard(int tracks, int albums){
        return tracks + " песен, " + albums + " альбомов";
    }
}
