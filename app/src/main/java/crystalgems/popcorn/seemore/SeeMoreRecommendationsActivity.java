package crystalgems.popcorn.seemore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import crystalgems.popcorn.R;
import crystalgems.popcorn.homepage.HomeRecyclerViewAdapter;

/**
 * Created by Alex on 25/03/2017.
 */

public class SeeMoreRecommendationsActivity extends Activity {
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter rvAdapter;

    //TODO : Replace with real data
    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.see_more_recommendations);

        homeRecyclerView = (RecyclerView)findViewById(R.id.movie_card_recycler_view_recommendation);

        // Improve performance if we know components will have fixed size, which is the case
        homeRecyclerView.setHasFixedSize(true);

        manager = new StaggeredGridLayoutManager(2, 1);
        homeRecyclerView.setLayoutManager(manager);

        // specify an adapter to create views for items in the recycler view
        rvAdapter = new HomeRecyclerViewAdapter(customDataset);
        homeRecyclerView.setAdapter(rvAdapter);

    }
}