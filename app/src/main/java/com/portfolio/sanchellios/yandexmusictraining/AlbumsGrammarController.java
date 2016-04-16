package com.portfolio.sanchellios.yandexmusictraining;

/**
 * Created by aleksandrvasilenko on 16.04.16.
 */
public class AlbumsGrammarController extends GrammarController {
    @Override
    public String getCorrectString(int capacity) {
        int remainder = capacity % 10;
        String albumsAEnding = "альбома";
        String albumsOvEnding = "альбомов";
        switch (remainder){
            case 1:
                if(capacity%100 == 11){
                    return albumsOvEnding;
                }else {
                    return "альбом";
                }

            case 2:
            case 3:
            case 4:
                return albumsAEnding;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 0:
                return albumsOvEnding;
            default:
                return albumsOvEnding;
        }
    }
}
