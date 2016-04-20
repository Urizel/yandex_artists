package com.portfolio.sanchellios.yandexmusictraining.database;

import android.provider.BaseColumns;

/**
 * Created by aleksandrvasilenko on 17.04.16.
 */
public class DBContracts {
    public static final class ArtistTable implements BaseColumns{
        public static final String TABLE_NAME = "artist";
        public static final String NAME = "name";
        public static final String GENRES = "genres";
        public static final String TRACKS = "tracks";
        public static final String ALBUMS = "albums";
        public static final String LINK = "link";
        public static final String DESCRIPTION = "description";
        public static final String SMALL_COVER_LINK = "small_cover";
        public static final String BIG_COVER_LINK = "big_cover";
        public static final String ARTIST_ID = "artist_id";
    }

    public static final class CacheRegistryTable implements BaseColumns{
        public static final String TABLE_NAME = "cache_reg";
        public static final String TIME = "time";
    }

    public static class CoverTable implements BaseColumns {
        public static final String COVER = "cover";
        public static final String ARTIST_NAME = "artist_name";
        public static final String ARTIST_ID = "artist_id";
    }

    public static final class SmallCoverTable extends CoverTable implements BaseColumns{
        public static final String TABLE_NAME = "small_cover";
    }

    public static final class BigCoverTable extends CoverTable implements BaseColumns{
        public static final String TABLE_NAME = "big_cover";
    }
}
