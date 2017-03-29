package crystalgems.popcorn.homepage;

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

import crystalgems.popcorn.QueriesManagement.JSONAsyncTask;
import crystalgems.popcorn.moviedetails.MovieDetailsActivity;
import crystalgems.popcorn.R;

/**
 * Created by Alex on 26/03/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements JSONAsyncTask.StringConsumer{
    private String[] dataset;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private Context context;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_movie_card_view, parent, false);
        // We always can set the view's size, margins, paddings and layout parameters here

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //TODO add content/flags to intent
                context.startActivity(intent);
            }
        });

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Movie title and year
        try {
            jsonObject = (JSONObject) jsonArray.get(position);
            String movieTitle = jsonObject.getString("titleImdb");
            String movieYear = jsonObject.getString("year");

            holder.setTitleYearElements(movieTitle, movieYear);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (jsonArray != null)
            return jsonArray.length();
        else
            return 0;
    }

    @Override
    public void setJSONString(String jsonString) throws JSONException {
        jsonArray = new JSONArray(jsonString);
        notifyDataSetChanged();
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


        public void setTitleYearElements(String movieTitle, String moveYear) {
            movieTitleTextView.setText(movieTitle);
            movieYearTextView.setText(moveYear);

            //TODO salut
            moviePosterImageView.setImageResource(R.drawable.la_la_land);
        }
    }
}
