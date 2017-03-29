package crystalgems.popcorn.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import crystalgems.popcorn.QueriesManagement.JSONAsyncTask;
import crystalgems.popcorn.moviedetails.MovieDetailsActivity;
import crystalgems.popcorn.R;

/**
 * Created by Alex on 26/03/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements JSONAsyncTask.StringConsumer{
    /*private String[] dataset;*/
    private JSONObject jsonObject;
    private Context context;

    /*public HomeRecyclerViewAdapter(String[] dataset) {
        this.dataset = dataset;
    }*/

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

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            String movieTitle = jsonObject.getString("titleImdb");
            String movieYear = jsonObject.getString("year");

            System.out.println("Title : " + movieTitle);
            System.out.println("Year : " + movieYear);

            holder.setElements(movieTitle, movieYear);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Movie picture
        ImageView moviePicture = (ImageView) holder.view.findViewById(R.id.movie_picture);
        moviePicture.setImageResource(R.drawable.la_la_land);

        // Movie title
        /*TextView movieTitle = (TextView) holder.view.findViewById(R.id.movieTitle);
        movieTitle.setText(dataset[position]);*/
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (jsonObject != null)
            return jsonObject.length();
        else
            return 0;
    }

    @Override
    public void setJSONString(String jsonString) throws JSONException {
        jsonObject = new JSONObject(jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1)); //Remove the first and last character to fit json format.
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView movieTitleTextView;
        private TextView movieYearTextView;

        public ViewHolder(final View vhView) {
            super(vhView);
            view = vhView;
            movieTitleTextView = (TextView) vhView.findViewById(R.id.movieTitle);
            //movieYearTextView = (TextView) vhView.findViewById(R.id.);
        }


        public void setElements(String movieTitle, String moveYear) {
            movieTitleTextView.setText(movieTitle);
            //movieYearTextView.setText(moveYear);

            //TODO  setImageIfExists(image)
        }
    }
}
