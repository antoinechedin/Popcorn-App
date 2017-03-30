package crystalgems.popcorn.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import crystalgems.popcorn.R;
import crystalgems.popcorn.seemore.SeeMoreRecommendationsActivity;

/**
 * Created by Alex on 26/03/2017.
 */

public class MovieDetailsActivity extends Activity {
    private Context context;

    private RecyclerView movieDetailsGeneralRecommendationsRecyclerView;
    private RecyclerView movieDetailsActorsRecommendationsRecyclerView;
    private RecyclerView movieDetailsDirectorsRecommendationsRecyclerView;
    private RecyclerView movieDetailsGenresRecommendationsRecyclerView;
    private RecyclerView.Adapter rvAdapter;

    private TextView releaseDateValue;
    private TextView directorValue;
    private TextView actorsValue;
    private TextView categoriesValue;
    private TextView nationalityValue;
    private AppCompatButton generalRecommendationsSeeMoreButton;
    private AppCompatButton actorsRecommendationsSeeMoreButton;
    private AppCompatButton directorsRecommendationsSeeMoreButton;
    private AppCompatButton genresRecommendationsSeeMoreButton;


    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        context = this;

        releaseDateValue = (TextView) findViewById(R.id.releaseDate);
        directorValue = (TextView) findViewById(R.id.directorValue);
        actorsValue = (TextView) findViewById(R.id.actorsValue);
        categoriesValue = (TextView) findViewById(R.id.categoriesValue);
        nationalityValue = (TextView) findViewById(R.id.nationalityValue);
        generalRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.generalRecommendationsSeeMoreButton);
        actorsRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.actorsRecommendationsSeeMoreButton);
        directorsRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.directorsRecommendationsSeeMoreButton);
        genresRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.genresRecommendationsSeeMoreButton);

        generalRecommendationsSeeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeMoreRecommendationsActivity.class);
                //TODO intent put extra or set flags
                startActivity(intent);
            }
        });

        actorsRecommendationsSeeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeMoreRecommendationsActivity.class);
                //TODO intent put extra or set flags
                startActivity(intent);
            }
        });

        directorsRecommendationsSeeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeMoreRecommendationsActivity.class);
                //TODO intent put extra or set flags
                startActivity(intent);
            }
        });

        generalRecommendationsSeeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeeMoreRecommendationsActivity.class);
                //TODO intent put extra or set flags
                startActivity(intent);
            }
        });

        movieDetailsGeneralRecommendationsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_general_recommendations_recycler_view);
        movieDetailsActorsRecommendationsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_actors_recommendations_recycler_view);
        movieDetailsDirectorsRecommendationsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_directors_recommendations_recycler_view);
        movieDetailsGenresRecommendationsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_genres_recommendations_recycler_view);

        // Improve performance if we know RecyclerView will have fixed size, which is the case
        movieDetailsGeneralRecommendationsRecyclerView.setHasFixedSize(true);
        movieDetailsActorsRecommendationsRecyclerView.setHasFixedSize(true);
        movieDetailsDirectorsRecommendationsRecyclerView.setHasFixedSize(true);
        movieDetailsGenresRecommendationsRecyclerView.setHasFixedSize(true);

        movieDetailsGeneralRecommendationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        movieDetailsActorsRecommendationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        movieDetailsDirectorsRecommendationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        movieDetailsGenresRecommendationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));

        // specify an adapter to create views for items in the recycler view
        rvAdapter = new MovieDetailsRecyclerViewAdapter(customDataset);
        movieDetailsGeneralRecommendationsRecyclerView.setAdapter(rvAdapter);
        movieDetailsActorsRecommendationsRecyclerView.setAdapter(rvAdapter); //TODO different adapter to match actors recommendations
        movieDetailsDirectorsRecommendationsRecyclerView.setAdapter(rvAdapter); //TODO different adapter to match directors recommendations
        movieDetailsGenresRecommendationsRecyclerView.setAdapter(rvAdapter); //TODO different adapter to match genres recommendations

    }

}
