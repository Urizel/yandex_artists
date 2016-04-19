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
        public static final String SMALL_COVER = "small_cover";
        public static final String BIG_COVER = "big_cover";
        public static final String ARTIST_ID = "artist_id";
    }

    public static final class CacheRegistryTable implements BaseColumns{
        public static final String TABLE_NAME = "cache_reg";
        public static final String TIME = "time";
    }

    public static final class ImageBlobsTable implements BaseColumns{
        public static final String TABLE_NAME = "image_blobs";
        public static final String ARTIST_ID = "artist_id";
        public static final String SMALL_COVER_BLOB = "small_cover";
        public static final String BIG_COVER_BLOB = "big_cover";
        public static final String ARTIST_NAME = "artist_name";
    }
}
