package crystalgems.popcorn.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

    private RatingBar ratingBar;

    private TextView titleValue;
    private TextView rateValue;
    private TextView releaseDateValue;
    private TextView directorValue;
    private TextView actorsValue;
    private TextView categoriesValue;
    private TextView nationalityValue;
    private AppCompatButton generalRecommendationsSeeMoreButton;
    private AppCompatButton actorsRecommendationsSeeMoreButton;
    private AppCompatButton directorsRecommendationsSeeMoreButton;
    private AppCompatButton genresRecommendationsSeeMoreButton;

    private JSONObject jsonPopcornObject;
    private String posterURL;


    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        context = this;

        Intent intent = getIntent();
        try {
            jsonPopcornObject = new JSONObject(intent.getStringExtra("movieJSONString"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        posterURL = intent.getStringExtra("posterURL");

        titleValue = (TextView) findViewById(R.id.movieTitle);
        rateValue = (TextView) findViewById(R.id.rateTextView);
        ratingBar = (RatingBar) findViewById(R.id.movieScoreRatingBar);
        releaseDateValue = (TextView) findViewById(R.id.releaseDate);
        directorValue = (TextView) findViewById(R.id.directorValue);
        actorsValue = (TextView) findViewById(R.id.actorsValue);
        categoriesValue = (TextView) findViewById(R.id.categoriesValue);
        nationalityValue = (TextView) findViewById(R.id.nationalityValue);
        generalRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.generalRecommendationsSeeMoreButton);
        actorsRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.actorsRecommendationsSeeMoreButton);
        directorsRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.directorsRecommendationsSeeMoreButton);
        genresRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.genresRecommendationsSeeMoreButton);

        // init values
        try {
            titleValue.setText(jsonPopcornObject.getString("titleImdb"));
            releaseDateValue.setText("Date : " + jsonPopcornObject.getString("year"));
            double rating = Double.parseDouble(jsonPopcornObject.getString("totalScore")) / Double.parseDouble(jsonPopcornObject.getString("ratingNum"));
            rating = round(rating, 1);
            ratingBar.setRating((float) rating);
            rateValue.setText(String.valueOf(rating));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    /*private void runAsyncTasks() {
        //runs AsyncTasks in parallel
        jsonTextAsyncTask = new JSONAsyncTask(rvTextsAdapter);
        jsonTextAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/movie-list");
    }*/

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
