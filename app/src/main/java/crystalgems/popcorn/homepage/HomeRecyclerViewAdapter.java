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

import crystalgems.popcorn.queriesManagement.JSONAsyncTask;
import crystalgems.popcorn.moviedetails.MovieDetailsActivity;
import crystalgems.popcorn.R;

/**
 * Created by Alex on 26/03/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements JSONAsyncTask.StringConsumer {
    private String[] dataset;
    private JSONArray jsonParentArray = new JSONArray();
    private JSONArray jsonMovieArray = new JSONArray();
    private JSONArray jsonPosterArray = new JSONArray();
    private Context context;
    private View view;
    private String movieTitle;
    private String movieYear;
    private ArrayList<JSONObject> jsonMovieArrayList;

    public HomeRecyclerViewAdapter(String[] dataset) {
        this.dataset = dataset;
    }

    public HomeRecyclerViewAdapter() {
        jsonMovieArrayList = new ArrayList<>();
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
        System.out.println("onBindViewHolder");
        try {
            // Get movie information from popcorn response
            if (jsonMovieArray != null) {
                movieTitle = ((JSONObject) jsonMovieArray.get(position)).getString("titleImdb");
                if (!movieTitle.equals("")) {
                    System.out.println("Movie Title : " + movieTitle);
                    holder.setTitleElements(movieTitle);
                }
                movieYear = ((JSONObject) jsonMovieArray.get(position)).getString("year");
                if (!movieYear.equals("")) {
                    System.out.println("Movie Year : " + movieYear);
                    holder.setYearElements(movieYear);
                }
            }
            // Get Poster url
            if (jsonPosterArray != null && ((JSONObject) jsonPosterArray.get(position)).getBoolean("Response")) {
                JSONArray jsonImdbMovies = new JSONArray(((JSONObject) jsonPosterArray.get(position)).getString("Search"));
                holder.setPosterElements(((JSONObject)jsonImdbMovies.get(0)).getString("Poster"));
            } else
                holder.setPosterElements();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ARRAYLIST:" + jsonMovieArrayList);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                try {
                    intent.putExtra("TITLE", jsonMovieArrayList.get(holder.getAdapterPosition()).getString("titleImdb"));
                    intent.putExtra("YEAR", jsonMovieArrayList.get(holder.getAdapterPosition()).getString("year"));
                    intent.putExtra("POSTERURL", jsonMovieArrayList.get(holder.getAdapterPosition()).getString("Poster"));
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
                //popcorn movie array
                jsonMovieArray = new JSONArray(jsonStringArrayList.get(0));

                //Imdb json
                for (int i = 1; i < jsonStringArrayList.size(); i++) {
                    jsonPosterArray.put(new JSONObject(jsonStringArrayList.get(i)));
                }

                System.out.println("json movie Array ! " + jsonMovieArray);
                System.out.println("json poster Array ! " + jsonPosterArray);
                notifyDataSetChanged();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView movieTitleTextView;
        private TextView movieYearTextView;
        private ImageView moviePosterImageView;

        public ViewHolder(final View vhView) {
            super(vhView);
            view = vhView;
            movieTitleTextView = (TextView) vhView.findViewById(R.id.movieTitle);
            movieYearTextView = (TextView) vhView.findViewById(R.id.movieYear);
            moviePosterImageView = (ImageView) vhView.findViewById(R.id.movie_picture);
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

        /**
         * set Poster element to default picture
         */
        public void setPosterElements() {
            moviePosterImageView.setImageResource(R.color.cardview_dark_background);
        }
    }
}
