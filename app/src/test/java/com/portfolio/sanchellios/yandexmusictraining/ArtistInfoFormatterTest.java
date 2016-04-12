package com.portfolio.sanchellios.yandexmusictraining;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by aleksandrvasilenko on 08.04.16.
 */
public class ArtistInfoFormatterTest {
    final String RAVE = "Rave";
    final String HEAVY_METAL = "Heavy-Metal";
    final String SALSA = "Salsa";
    final String BEBOP = "Be-Bop";
    final String C_SEP = ", ";
    final String TRACKS = " песен, ";
    final char interpunct = 183;
    final String TRACKS_AND_INTERPUNCT = " песен " + interpunct + " ";
    final String ALBUMS = " альбомов";

    private ArrayList<String> genres = new ArrayList<>();
    private String testString;

    @Before
    public void setUp(){
        genres.add(RAVE);
        genres.add(HEAVY_METAL);
        genres.add(SALSA);
        genres.add(BEBOP);

    }

    @Test
    public void testFormGenreString(){
        testString = RAVE + C_SEP + HEAVY_METAL + C_SEP + SALSA + C_SEP + BEBOP;
        Assert.assertEquals(testString, ArtistInfoFormatter.formGenreString(genres));
    }

    @Test
    public void testGetAlbumsAndSongsForCard(){
        testString = 20 + TRACKS_AND_INTERPUNCT + 20 + ALBUMS;
        System.out.print(testString);
        Assert.assertEquals(testString, ArtistInfoFormatter.getAlbumsAndSongsWithInterpunct(20, 20));
    }

    @Test
    public void testGetAlbumsAndSongsWithInterpunct(){
        testString = 20 + TRACKS + 20 + ALBUMS;
        Assert.assertEquals(testString, ArtistInfoFormatter.getAlbumsAndSongsForCard(20, 20));
    }

    @After
    public void tearDown(){

    }

}