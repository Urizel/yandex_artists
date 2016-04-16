package com.portfolio.sanchellios.yandexmusictraining;

/**
 * Created by aleksandrvasilenko on 16.04.16.
 */
public class SongsGrammarController extends GrammarController {
    @Override
    public String getCorrectString(int capacity) {
        int remainder = capacity % 10;
        String twoToFourSongs = "песни";
        String fiveToTenSongs = "песен";
        switch (remainder){
            case 1:
                if(capacity%100 == 11){
                    return fiveToTenSongs;
                }else {
                    return "песня";
                }
            case 2:
            case 3:
            case 4:
                return twoToFourSongs;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 0:
                return fiveToTenSongs;
            default:
                return fiveToTenSongs;
        }
    }
}
