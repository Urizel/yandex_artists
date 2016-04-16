package com.portfolio.sanchellios.yandexmusictraining;

import android.content.Context;
import android.content.Intent;
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
 * Created by Alexander Vasilenko on 10.04.16.
 */
public class ArtistListFragment extends Fragment {
    private static final String ARTISTS = "ARTISTS";
    private static final String RECYCLER_ARTIST = "RECYCLER_ARTIST";
    private ArrayList<Artist> artists = new ArrayList<>();
    private RecyclerView artistRecycler;
    private TaskKiller killer;

    public interface TaskKiller{
        void killTask();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        killer = (TaskKiller)context;
    }

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
        setUpRecycler();
        return artistRecycler;
    }

    private void setUpRecycler(){
        ArtistListAdapter adapter = new ArtistListAdapter(artists, getContext().getApplicationContext());
        adapter.setListener(new ArtistListAdapter.ArtistClickListener(){
            @Override
            public void onClick(Artist artist){
                killer.killTask();
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                intent.putExtra(DetailedActivity.ARTIST, artist);
                getActivity().startActivity(intent);
            }
        });
        artistRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistRecycler.setLayoutManager(layoutManager);
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
