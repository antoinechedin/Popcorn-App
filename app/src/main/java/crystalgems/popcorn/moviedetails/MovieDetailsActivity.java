package crystalgems.popcorn.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import crystalgems.popcorn.R;
import crystalgems.popcorn.queriesManagement.AsyncTaskListener;
import crystalgems.popcorn.queriesManagement.SimpleJSONAsyncTask;
import crystalgems.popcorn.seemore.SeeMoreRecommendationsActivity;

/**
 * Created by Alex on 26/03/2017.
 */

public class MovieDetailsActivity extends Activity implements AsyncTaskListener {
    private Context context;

    private RecyclerView movieDetailsGeneralRecommendationsRecyclerView;
    private RecyclerView movieDetailsActorsRecommendationsRecyclerView;
    private RecyclerView movieDetailsDirectorsRecommendationsRecyclerView;
    private RecyclerView movieDetailsGenresRecommendationsRecyclerView;

    private RatingBar ratingBar;

    private TextView titleValue;
    private TextView rateValue;
    private TextView releaseDateValue;
    private TextView directorValue;
    private TextView actorsValue;
    private TextView categoriesValue;
    private TextView nationalityValue;
    private ImageView moviePicture;
    private AppCompatButton generalRecommendationsSeeMoreButton;
    private AppCompatButton actorsRecommendationsSeeMoreButton;
    private AppCompatButton directorsRecommendationsSeeMoreButton;
    private AppCompatButton genresRecommendationsSeeMoreButton;

    private String posterURL;


    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        context = this;

        Intent intent = getIntent();
       /* try {
            jsonPopcornObject = new JSONObject(intent.getStringExtra("movieJSONString"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        posterURL = intent.getStringExtra("posterURL");*/

        titleValue = (TextView) findViewById(R.id.movieTitle);
        rateValue = (TextView) findViewById(R.id.rateTextView);
        ratingBar = (RatingBar) findViewById(R.id.movieScoreRatingBar);
        releaseDateValue = (TextView) findViewById(R.id.releaseDate);
        directorValue = (TextView) findViewById(R.id.directorValue);
        actorsValue = (TextView) findViewById(R.id.actorsValue);
        categoriesValue = (TextView) findViewById(R.id.categoriesValue);
        nationalityValue = (TextView) findViewById(R.id.nationalityValue);
        moviePicture = (ImageView) findViewById(R.id.moviePicture);
        generalRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.generalRecommendationsSeeMoreButton);
        actorsRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.actorsRecommendationsSeeMoreButton);
        directorsRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.directorsRecommendationsSeeMoreButton);
        genresRecommendationsSeeMoreButton = (AppCompatButton) findViewById(R.id.genresRecommendationsSeeMoreButton);

        // Init values form intent
        titleValue.setText(intent.getStringExtra("title"));
        releaseDateValue.setText("Date : " + intent.getStringExtra("year"));
        Picasso.with(context).load(intent.getStringExtra("posterUrl")).into(moviePicture);
           /* double rating = Double.parseDouble(jsonPopcornObject.getString("totalScore")) / Double.parseDouble(jsonPopcornObject.getString("ratingNum"));
            rating = round(rating, 1);
            ratingBar.setRating((float) rating);
            rateValue.setText(String.valueOf(rating));*/

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

        // General similar movie
        MovieDetailsRecyclerViewAdapter rvAdapter1 = new MovieDetailsRecyclerViewAdapter();
        movieDetailsGeneralRecommendationsRecyclerView.setAdapter(rvAdapter1);
        new SimpleJSONAsyncTask(rvAdapter1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/nearest-movie-list?movieId="
                + intent.getStringExtra("id") + "&length=10");

        // Actor similar movie
        MovieDetailsRecyclerViewAdapter rvAdapter2 = new MovieDetailsRecyclerViewAdapter();
        movieDetailsActorsRecommendationsRecyclerView.setAdapter(rvAdapter2);
        new SimpleJSONAsyncTask(rvAdapter2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/nearest-movie-list-by-actor?movieId="
                + intent.getStringExtra("id") + "&length=10");

        // Director similar movie
        MovieDetailsRecyclerViewAdapter rvAdapter3 = new MovieDetailsRecyclerViewAdapter();
        movieDetailsDirectorsRecommendationsRecyclerView.setAdapter(rvAdapter3);
        new SimpleJSONAsyncTask(rvAdapter3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/nearest-movie-list-by-director?movieId="
                + intent.getStringExtra("id") + "&length=10");

        // Genre similar movie
        MovieDetailsRecyclerViewAdapter rvAdapter4 = new MovieDetailsRecyclerViewAdapter();
        movieDetailsGenresRecommendationsRecyclerView.setAdapter(rvAdapter4);
        new SimpleJSONAsyncTask(rvAdapter4).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/nearest-movie-list-by-genre?movieId="
                + intent.getStringExtra("id") + "&length=10");


        // Send http requests for other information
        new SimpleJSONAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/director?movieId=" + intent.getStringExtra("id"));
        new SimpleJSONAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/actor?movieId=" + intent.getStringExtra("id"));
        new SimpleJSONAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/genre?movieId=" + intent.getStringExtra("id"));
        new SimpleJSONAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/country?movieId=" + intent.getStringExtra("id"));
        new SimpleJSONAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/movie?id=" + intent.getStringExtra("id"));


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

    @Override
    public void onPostAsyncTask(String jsonString) throws JSONException {
        if (jsonString != null && !"".equals(jsonString)) {
            // It's a movie json
            if (jsonString.charAt(0) != '[') {
                JSONObject jsonObject = new JSONObject(jsonString);
                rateValue.setText(String.valueOf(round(jsonObject.getDouble("totalScore") / jsonObject.getDouble("ratingNum"), 1)));
            } else {
                JSONArray jsonArray = new JSONArray(jsonString);
                if (jsonArray.length() > 0) {
                    JSONObject first = (JSONObject) jsonArray.get(0);
                    // Director
                    if (first.optInt("movieQuantity") == 1)
                        for (int i = 0; i < jsonArray.length(); i++)
                            directorValue.setText(directorValue.getText() + " " + ((JSONObject) jsonArray.get(i)).getString("firstName") + " " + ((JSONObject) jsonArray.get(i)).getString("lastName") + ",");
                        // Actor
                    else if (first.optInt("movieQuantity") == 2)
                        for (int i = 0; i < jsonArray.length(); i++)
                            actorsValue.setText(actorsValue.getText() + " " + ((JSONObject) jsonArray.get(i)).getString("firstName") + " " + ((JSONObject) jsonArray.get(i)).getString("lastName") + ",");
                        // Genre
                    else if (!first.optString("genre").equals(""))
                        for (int i = 0; i < jsonArray.length(); i++)
                            categoriesValue.setText(categoriesValue.getText() + " " + ((JSONObject) jsonArray.get(i)).getString("genre") + ",");
                        // Country
                    else if (!first.optString("country").equals(""))
                        for (int i = 0; i < jsonArray.length(); i++)
                            nationalityValue.setText(nationalityValue.getText() + " " + ((JSONObject) jsonArray.get(i)).getString("country") + ",");
                }
            }
        }
    }
}
