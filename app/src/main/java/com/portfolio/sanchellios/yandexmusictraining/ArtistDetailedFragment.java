package com.portfolio.sanchellios.yandexmusictraining;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailedFragment extends Fragment {
    private static final String ARTIST = "ARTIST";
    private Artist artist;

    private ImageView cover;
    private TextView genres;
    private TextView albumsAndSongs;
    private TextView bio;

    public static ArtistDetailedFragment newInstance(Artist currentArtist){
        Bundle args = new Bundle();
        args.putParcelable(ARTIST, currentArtist);
        ArtistDetailedFragment fragment = new ArtistDetailedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        artist = getArguments().getParcelable(ARTIST);
        View view = inflater.inflate(R.layout.fragment_artist_detailed, container, false);

        cover = (ImageView)view.findViewById(R.id.d_frag_cover_image_view);
        genres = (TextView)view.findViewById(R.id.d_frag_artist_genres);
        albumsAndSongs = (TextView)view.findViewById(R.id.d_frag_artist_albums_and_songs);
        bio = (TextView)view.findViewById(R.id.d_frag_artist_bio);

        Log.d("Big_image", artist.getCover().getBigCover());
        Picasso.with(getActivity().getApplicationContext())
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
        return view;
    }

}
