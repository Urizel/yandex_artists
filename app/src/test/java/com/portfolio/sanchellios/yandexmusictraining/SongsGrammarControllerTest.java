package com.portfolio.sanchellios.yandexmusictraining;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandrvasilenko on 16.04.16.
 */
public class SongsGrammarControllerTest {
    String oneSong = "песня";
    String twoToFourSong = "песни";
    String fiveToTenSong = "песен";
    String testString = "";
    SongsGrammarController sgs = new SongsGrammarController();

    @Test
    public void testOneSong(){
        setTestString(oneSong);
        int[] numArray = {1, 21, 31, 41, 51, 61, 71, 81, 91, 101, 1001};
        for(int i = 0; i < numArray.length; i++){
            Assert.assertEquals(testString, sgs.getCorrectString(numArray[i]));
        }
    }
    @Test
    public void testTwoToFourSong(){
        setTestString(twoToFourSong);
        int[] numArray = {2, 13, 24, 32, 43, 52, 62, 73, 84, 92, 103, 1004};
        for(int i = 0; i < numArray.length; i++){
            Assert.assertEquals(testString, sgs.getCorrectString(numArray[i]));
        }
    }

    @Test
    public void testFiveToTenSong(){
        setTestString(fiveToTenSong);
        int[] numArray = {5, 16, 27, 38, 49, 50, 65, 76, 87, 98, 109, 1000};
        for(int i = 0; i < numArray.length; i++){
            Assert.assertEquals(testString, sgs.getCorrectString(numArray[i]));
        }
    }

    @Test
    public void testElevenSong(){
        setTestString(fiveToTenSong);
        int[] numArray = {11, 111, 211, 311, 411, 511, 1011, 10011, 100011};
        for(int i = 0; i < numArray.length; i++){
            Assert.assertEquals(testString, sgs.getCorrectString(numArray[i]));
        }
    }

    private void setTestString(String text){
        this.testString = text;
    }
}