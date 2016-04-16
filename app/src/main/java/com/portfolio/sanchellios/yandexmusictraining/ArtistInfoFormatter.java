package com.portfolio.sanchellios.yandexmusictraining;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by aleksandrvasilenko on 08.04.16.
 */
public class ArtistInfoFormatter {
    private static final char interpunct = 183;
    private static final char _ = ' ';
    private static final char cma = ',';
    private static String tracks = "песен";
    private static String albums = "альбомов";
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

    public static String getAlbumsAndSongsForCard(int tracksNum, int albumsNum){
        formCorrectSongsAndAlbumsStrings(tracksNum, albumsNum);
        return "" + albumsNum + _ + albums + cma + _ + tracksNum + _ + tracks;
    }

    public static String getAlbumsAndSongsWithInterpunct(int tracksNum, int albumsNum){
        formCorrectSongsAndAlbumsStrings(tracksNum, albumsNum);
        return "" + tracksNum + _ + albums + _ + interpunct + _ + albumsNum + _ + tracks;
    }

    private static void formCorrectSongsAndAlbumsStrings(int tracksNum, int albumsNum){
        tracks = new SongsGrammarController().getCorrectString(tracksNum);
        albums = new AlbumsGrammarController().getCorrectString(albumsNum);
    }
}
