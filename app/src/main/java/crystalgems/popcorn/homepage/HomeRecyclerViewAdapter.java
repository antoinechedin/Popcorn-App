package crystalgems.popcorn.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import crystalgems.popcorn.R;
import crystalgems.popcorn.moviedetails.MovieDetailsActivity;
import crystalgems.popcorn.queriesManagement.JSONAsyncTask;

/**
 * Created by Alex on 26/03/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements JSONAsyncTask.StringConsumer {
    private String[] dataset;
    private JSONArray jsonParentArray = new JSONArray();
    private JSONArray jsonChildArray = new JSONArray();
    private JSONObject jsonPopcornObject;
    private JSONObject jsonImdbSearchObject;
    private JSONObject imdbResponse;
    private Context context;
    private View view;
    private String movieTitle;
    private String movieYear;
    private String posterUrl;
    private int movieId;
    private ArrayList<JSONObject> jsonMovieArrayList = new ArrayList<>();

    public HomeRecyclerViewAdapter(String[] dataset) {
        this.dataset = dataset;
    }

    public HomeRecyclerViewAdapter() {
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        this.context = parent.getContext();

        // create a new view
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_movie_card_view, parent, false);
        // We always can set the view's size, margins, paddings and layout parameters here

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            jsonChildArray = (JSONArray) jsonParentArray.get(0);
            jsonPopcornObject = (JSONObject) jsonChildArray.get(position);

            if (jsonParentArray.length() > 1) {
                jsonChildArray = (JSONArray) jsonParentArray.get(position + 1);
                jsonImdbSearchObject = (JSONObject) jsonChildArray.get(0);
            }

            if (jsonPopcornObject != null) {
                //Movie id
                movieId = jsonPopcornObject.getInt("id");

                //Movie title
                movieTitle = jsonPopcornObject.getString("titleImdb");
                if (!movieTitle.equals(""))
                    holder.setTitleElements(movieTitle);

                // Movie Year
                movieYear = jsonPopcornObject.getString("year");
                holder.setYearElements(movieYear);

                // Movie Json
                holder.setMovieJSON(jsonPopcornObject);
            }
            if (jsonImdbSearchObject != null) {
                // Movie Poster
                posterUrl = jsonImdbSearchObject.getString("Poster");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", movieId);
            jsonObject.put("titleImdb", movieTitle);
            jsonObject.put("year", movieYear);
            jsonObject.put("Poster", posterUrl);
            jsonMovieArrayList.add(jsonObject);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        //Movie poster
        try {
            if (jsonImdbSearchObject != null) {
                String posterUrl = jsonImdbSearchObject.getString("Poster");
                holder.setPosterElements(posterUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MovieDetailsActivity.class);
                try {
                    intent.putExtra("id", jsonMovieArrayList.get(holder.getAdapterPosition()).getString("id"));
                    intent.putExtra("title", jsonMovieArrayList.get(holder.getAdapterPosition()).getString("titleImdb"));
                    intent.putExtra("year", jsonMovieArrayList.get(holder.getAdapterPosition()).getString("year"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO add content/flags to intent
                context.startActivity(intent);
            }
        });
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (jsonParentArray != null)
            try {
                JSONArray jsonArrayToReturn = (JSONArray) jsonParentArray.get(0);
                return jsonArrayToReturn.length();
            } catch (JSONException e) {
                return 0;
            }
        else
            return 0;
    }

    @Override
    public void setJSONString(ArrayList<String> jsonStringArrayList) throws JSONException {
        if (jsonStringArrayList != null) {
            if (!jsonStringArrayList.isEmpty()) {


                //If it is from popcorn json
                jsonParentArray.put(new JSONArray(jsonStringArrayList.get(0)));

                //Else it is from Imdb json
                for (int i = 1; i < jsonStringArrayList.size(); i++) {
                    JSONObject searchObject = new JSONObject(jsonStringArrayList.get(i));
                    JSONArray arraySearchObject = new JSONArray();

                    try {
                        //if (searchObject.get("Response") == "True") //TODO
                        arraySearchObject = (JSONArray) searchObject.get("Search");
                    } catch (Exception ignored) {
                    }

                    jsonParentArray.put(arraySearchObject);
                }

                notifyDataSetChanged();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView movieTitleTextView;
        private TextView movieYearTextView;
        private ImageView moviePosterImageView;
        private JSONObject movieJSON;

        public ViewHolder(final View vhView) {
            super(vhView);
            view = vhView;
            movieTitleTextView = (TextView) vhView.findViewById(R.id.movieTitle);
            movieYearTextView = (TextView) vhView.findViewById(R.id.movieYear);
            moviePosterImageView = (ImageView) vhView.findViewById(R.id.movie_picture);
        }

        public JSONObject getMovieJSON() {
            return movieJSON;
        }

        public void setMovieJSON(JSONObject movieJSON) {
            this.movieJSON = movieJSON;
        }

        public void setTitleElements(String movieTitle) {
            movieTitleTextView.setText(movieTitle);
        }

        public void setYearElements(String moveYear) {
            movieYearTextView.setText(moveYear);
        }

        /**
         * set Poster element to to picture at posterUrl
         */
        public void setPosterElements(String posterUrl) {
            Picasso.with(view.getContext()).load(posterUrl).into(moviePosterImageView);
        }
    }
}
