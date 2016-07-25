package com.portfolio.sanchellios.yandexmusictraining.views;

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

import com.portfolio.sanchellios.yandexmusictraining.R;
import com.portfolio.sanchellios.yandexmusictraining.artist.Artist;
import com.portfolio.sanchellios.yandexmusictraining.database.ArtistCacheService;

import java.util.ArrayList;

/**
 * Created by Alexander Vasilenko on 10.04.16.
 */
// XXX Fragment purpose?
public class ArtistListFragment extends Fragment {
    // XXX resources?
    private static final int VERTICAL_ITEM_SPACE = 4;
    public static final String SAVE_TO_DB_STATE = "SAVE_TO_DB_STATE";
    public static final String IGNORE_SAVE = "IGNORE_SAVE";
    private static final String ARTISTS = "ARTISTS";
    private static final String RECYCLER_ARTIST = "RECYCLER_ARTIST";
    private LinearLayoutManager layoutManager;
    private ArrayList<Artist> artists = new ArrayList<>();
    private RecyclerView artistRecycler;
    private ArtistListViewer viewer;

    public interface ArtistListViewer {
        void killTask();
        ArrayList<Artist> getArtistList();
        String getArtistSaveToDbState();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewer = (ArtistListViewer)context;
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
        layoutManager = new LinearLayoutManager(getActivity());
        artistRecycler = (RecyclerView)inflater
                .inflate(R.layout.artist_list_fragment,
                        container,
                        false);

        setUpRecycler();

        LayoutPositionManager positionManager = new LayoutPositionManager(getActivity());
        layoutManager.scrollToPositionWithOffset(positionManager.getPosition(), 0);

        return artistRecycler;
    }

    private void setUpRecycler(){
        // XXX app context?
        ArtistListAdapter adapter = new ArtistListAdapter(artists, getContext().getApplicationContext());
        adapter.setListener(new ArtistListAdapter.ArtistClickListener() {
            @Override
            public void onClick(Artist artist) {
                viewer.killTask();
                if (viewer.getArtistSaveToDbState().equals(SAVE_TO_DB_STATE)) {
                    Intent intentService;
                    ArrayList<Artist> artists = viewer.getArtistList();
                    // XXX performance?
                    // XXX why here?
                    for (int i = 0; i < artists.size(); i++) {
                        intentService = new Intent(getActivity(), ArtistCacheService.class);
                        intentService.putExtra(ArtistCacheService.ARTIST, artists.get(i));
                        getActivity().startService(intentService);

                    }
                }
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                intent.putExtra(DetailedActivity.ARTIST, artist);
                getActivity().startActivity(intent);

            }
        });

        artistRecycler.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        artistRecycler.setAdapter(adapter);
        artistRecycler.setLayoutManager(layoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // XXX two managers?
        layoutManager.onSaveInstanceState();
        outState.putParcelable(RECYCLER_ARTIST, artistRecycler.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        layoutManager.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            Parcelable savedRecyclerState = savedInstanceState.getParcelable(RECYCLER_ARTIST);
            artistRecycler.getLayoutManager().onRestoreInstanceState(savedRecyclerState);
        }
    }
}
