package crystalgems.popcorn.homepage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import crystalgems.popcorn.connexion.Connexion;
import crystalgems.popcorn.queriesManagement.JSONAsyncTask;
import crystalgems.popcorn.R;

/**
 * Created by Alex on 25/03/2017.
 */

public class HomePageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;
    private HomeRecyclerViewAdapter rvTextsAdapter;

    private JSONAsyncTask jsonTextAsyncTask;


    private int mPage;

    public static HomePageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.home_fragment_page, container, false);

        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_card_recycler_view);

        // Improve performance if we know components will have fixed size, which is the case
        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setItemViewCacheSize(20);
        homeRecyclerView.setDrawingCacheEnabled(true);
        //homeRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        homeLayoutManager = new StaggeredGridLayoutManager(2, 1);
        homeRecyclerView.setLayoutManager(homeLayoutManager);

        // specify an adapter to create views for items in the recycler view
        rvTextsAdapter = new HomeRecyclerViewAdapter();
        homeRecyclerView.setAdapter(rvTextsAdapter);

        runAsyncTasks(mPage);


        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.itemProgressBar);
        rvTextsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                progressBar.setVisibility(View.GONE);
            }
        });
        return rootView;
    }

    private void runAsyncTasks(int mPage) {
        //runs AsyncTasks in parallel
        jsonTextAsyncTask = new JSONAsyncTask(rvTextsAdapter);
        switch (mPage) {
            case 1:
                jsonTextAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/movie-list");
                break;
            case 2:
                System.out.println("user id : " + Connexion.getInstance().getUserId());
                jsonTextAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/user-personal-recommendation-list/?userId="
                        + Connexion.getInstance().getUserId()
                        + "&length=30");
                break;
            case 3:
                jsonTextAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/movie-list");
                break;
            default:
                jsonTextAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/movie-list");
                break;
        }
    }
}