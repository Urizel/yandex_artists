package com.portfolio.sanchellios.yandexmusictraining.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.portfolio.sanchellios.yandexmusictraining.R;
import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;
import com.portfolio.sanchellios.yandexmusictraining.artist.Oeuvre;
import com.portfolio.sanchellios.yandexmusictraining.string_formating.ArtistInfoFormatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aleksandrvasilenko on 08.04.16.
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder>{

    private ArrayList<Artist> artists;
    private Context context;
    private ArtistClickListener listener;

    public static interface ArtistClickListener{
        public void onClick(Artist artist);
    }

    public ArtistListAdapter(ArrayList<Artist> artists, Context context){
        this.context = context;
        this.artists = artists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView view){
            super(view);
            cardView = view;
        }
    }

    @Override
    public ArtistListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        ImageView smallCover = (ImageView)cardView.findViewById(R.id.small_artist_image);
        TextView name = (TextView)cardView.findViewById(R.id.artist_name);
        TextView genres = (TextView)cardView.findViewById(R.id.artist_styles);
        TextView songsAndAlbums = (TextView)cardView.findViewById(R.id.albums_and_songs);

        final Artist artist = artists.get(position);
        Picasso.with(context)
                .load(artist.getCover().getSmallCover())
                .placeholder(R.drawable.ic_music_note_black_48dp)
                .fit()
                .centerInside()
                .into(smallCover);

        fillScreenWithData(artist, name, genres, songsAndAlbums);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(artist);
                }
            }
        });
    }

    private void fillScreenWithData(Artist artist,
                                    TextView name,
                                    TextView genres,
                                    TextView songsAndAlbums){
        name.setText(artist.getName());
        genres.setText(ArtistInfoFormatter.formGenreString(artist.getGenres()));
        songsAndAlbums.setText(
                ArtistInfoFormatter.getAlbumsAndSongsForCard(new Oeuvre(artist)));
    }

    public void setListener(ArtistClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
