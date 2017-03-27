package crystalgems.popcorn.moviedetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import crystalgems.popcorn.R;

/**
 * Created by Alex on 26/03/2017.
 */

public class MovieDetailsActivity extends Activity {
    private RecyclerView movieDetailsGeneralRecommendationsRecyclerView;
    private RecyclerView movieDetailsGenreRecommendationsRecyclerView;
    private RecyclerView.Adapter rvAdapter;
    private TextView releaseDateValue;
    private TextView directorValue;
    private TextView actorsValue;
    private TextView categoriesValue;
    private TextView nationalityValue;


    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        releaseDateValue = (TextView) findViewById(R.id.releaseDateValue);
        directorValue = (TextView) findViewById(R.id.directorValue);
        actorsValue = (TextView) findViewById(R.id.actorsValue);
        categoriesValue = (TextView) findViewById(R.id.categoriesValue);
        nationalityValue = (TextView) findViewById(R.id.nationalityValue);

        movieDetailsGeneralRecommendationsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_general_recommendations_recycler_view);
        movieDetailsGenreRecommendationsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_genre_recommendations_recycler_view);

        // Improve performance if we know RecyclerView will have fixed size, which is the case
        movieDetailsGeneralRecommendationsRecyclerView.setHasFixedSize(true);
        movieDetailsGenreRecommendationsRecyclerView.setHasFixedSize(true);

        movieDetailsGeneralRecommendationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        movieDetailsGenreRecommendationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));

        // specify an adapter to create views for items in the recycler view
        rvAdapter = new MovieDetailsRecyclerViewAdapter(customDataset);
        movieDetailsGeneralRecommendationsRecyclerView.setAdapter(rvAdapter);
        movieDetailsGenreRecommendationsRecyclerView.setAdapter(rvAdapter); //TODO different adapter to match genre recommendations



    }
}
