package com.portfolio.sanchellios.yandexmusictraining.string_formating;

import com.portfolio.sanchellios.yandexmusictraining.artist.Oeuvre;
import com.portfolio.sanchellios.yandexmusictraining.string_formating.AlbumsGrammarController;
import com.portfolio.sanchellios.yandexmusictraining.string_formating.SongsGrammarController;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by aleksandrvasilenko on 08.04.16.
 */
public class ArtistInfoFormatter {
    // XXX Magic number?
    private static final char interpunct = 183;
    private static final char _ = ' ';
    private static final char cma = ',';
    private static String tracks = "песен"; // XXX res?
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

    // XXX another way
    public static String getAlbumsAndSongsForCard(Oeuvre oeuvre){
        formCorrectSongsAndAlbumsStrings(oeuvre);
        return "" + oeuvre.getAlbums() + _ + albums + cma + _ + oeuvre.getTracks() + _ + tracks;
    }

    public static String getAlbumsAndSongsWithInterpunct(Oeuvre oeuvre){
        formCorrectSongsAndAlbumsStrings(oeuvre);
        return "" + oeuvre.getAlbums() + _ + albums + _ + interpunct + _ + oeuvre.getTracks() + _ + tracks;
    }

    private static void formCorrectSongsAndAlbumsStrings(Oeuvre oeuvre){
        // XXX instance?
        tracks = new SongsGrammarController().getCorrectString(oeuvre.getTracks());
        albums = new AlbumsGrammarController().getCorrectString(oeuvre.getAlbums());
    }
}
