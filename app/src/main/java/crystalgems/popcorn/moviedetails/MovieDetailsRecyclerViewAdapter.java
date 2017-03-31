package crystalgems.popcorn.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import crystalgems.popcorn.R;
import crystalgems.popcorn.queriesManagement.AsyncTaskListener;

/**
 * Created by Alex on 27/03/2017.
 */

public class MovieDetailsRecyclerViewAdapter extends RecyclerView.Adapter<MovieDetailsRecyclerViewAdapter.ViewHolder> implements AsyncTaskListener {
    private Context context;
    private String jsonString = null;

    public MovieDetailsRecyclerViewAdapter() {
    }

    @Override
    public void onPostAsyncTask(String jsonString) throws JSONException {
        if (jsonString != null && !"".equals(jsonString)) {
            this.jsonString = jsonString;
            notifyDataSetChanged();
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieDetailsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        this.context = parent.getContext();

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details_page_movie_card_view, parent, false);
        // We always can set the view's size, margins, paddings and layout parameters here

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        MovieDetailsRecyclerViewAdapter.ViewHolder viewHolder = new MovieDetailsRecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MovieDetailsRecyclerViewAdapter.ViewHolder holder, int position) {
        // Movie picture
        ImageView moviePicture = (ImageView) holder.view.findViewById(R.id.movie_picture);
        moviePicture.setImageResource(R.drawable.la_la_land);

        // Movie title
        TextView movieTitle = (TextView) holder.view.findViewById(R.id.movieTitle);
        try {
            if (jsonString != null) {
                JSONArray jsonArray = new JSONArray(jsonString);
                movieTitle.setText(((JSONObject) ((JSONObject) jsonArray.get(position)).get("object")).getString("titleImdb"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (jsonString != null)
            try {
                return new JSONArray(jsonString).length();
            } catch (JSONException e) {
                return 0;
            }
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(final View vhView) {
            super(vhView);
            view = vhView;
        }
    }
}
