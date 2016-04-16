package com.portfolio.sanchellios.yandexmusictraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {
    public static String ARTIST = "ARTIST";
    private Artist artist;
    private ImageView cover;
    private TextView genres;
    private TextView albumsAndSongs;
    private TextView bio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();
        artist = intent.getParcelableExtra(ARTIST);
        cover = (ImageView)findViewById(R.id.d_frag_cover_image_view);
        genres = (TextView)findViewById(R.id.d_frag_artist_genres);
        albumsAndSongs = (TextView)findViewById(R.id.d_frag_artist_albums_and_songs);
        bio = (TextView)findViewById(R.id.d_frag_artist_bio);
        Picasso.with(getApplicationContext())
                .load(artist.getCover().getBigCover())
                .fit()
                .placeholder(R.drawable.unknown_artist)
                .centerInside()
                .into(cover);
        genres.setText(ArtistInfoFormatter.formGenreString(artist.getGenres()));
        albumsAndSongs.setText(
                ArtistInfoFormatter
                        .getAlbumsAndSongsWithInterpunct(
                                artist.getNumberOfTracks(),
                                artist.getNumberOfAlbums()));
        bio.setText(artist.getDescription());
    }
}
