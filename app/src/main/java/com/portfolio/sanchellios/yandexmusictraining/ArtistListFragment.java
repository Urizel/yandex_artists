package com.portfolio.sanchellios.yandexmusictraining;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by aleksandrvasilenko on 10.04.16.
 */
public class ArtistListFragment extends Fragment {
    private static final String ARTISTS = "ARTISTS";
    private static final String RECYCLER_ARTIST = "RECYCLER_ARTIST";
    private ArrayList<Artist> artists = new ArrayList<>();
    private RecyclerView artistRecycler;

    public static ArtistListFragment newInstance(ArrayList<Artist> artists){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARTISTS, artists);
        ArtistListFragment fragment = new ArtistListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        artists = getArguments().getParcelableArrayList(ARTISTS);
        artistRecycler = (RecyclerView)inflater
                .inflate(R.layout.artist_list_fragment,
                        container,
                        false);

        ArtistListAdapter adapter = new ArtistListAdapter(artists, getContext().getApplicationContext());
        artistRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistRecycler.setLayoutManager(layoutManager);
        return artistRecycler;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_ARTIST, artistRecycler.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            Parcelable savedRecyclerState = savedInstanceState.getParcelable(RECYCLER_ARTIST);
            artistRecycler.getLayoutManager().onRestoreInstanceState(savedRecyclerState);
        }
    }
}
