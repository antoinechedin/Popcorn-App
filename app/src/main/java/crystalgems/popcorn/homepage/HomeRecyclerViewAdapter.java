package crystalgems.popcorn.homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import crystalgems.popcorn.queriesManagement.JSONAsyncTask;
import crystalgems.popcorn.moviedetails.MovieDetailsActivity;
import crystalgems.popcorn.R;

/**
 * Created by Alex on 26/03/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> implements JSONAsyncTask.StringConsumer{
    private String[] dataset;
    private JSONArray jsonParentArray = new JSONArray();
    private JSONArray jsonChildArray = new JSONArray();
    private JSONObject jsonPopcornObject;
    private JSONObject jsonImdbSearchObject;
    private JSONObject imdbResponse;
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
        try {
            jsonChildArray = (JSONArray) jsonParentArray.get(0);
            jsonPopcornObject = (JSONObject) jsonChildArray.get(position);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (jsonParentArray.length() > 1) {
                jsonChildArray = (JSONArray) jsonParentArray.get(position + 1);
                jsonImdbSearchObject = (JSONObject) jsonChildArray.get(0);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //Movie title
        try {
            if (jsonImdbSearchObject != null) {
                String movieTitle = jsonPopcornObject.getString("titleImdb");
                if (!movieTitle.equals(""))
                    holder.setTitleElements(movieTitle);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //Movie year
        try {
            if (jsonImdbSearchObject != null) {
                String movieYear = jsonPopcornObject.getString("year");
                holder.setYearElements(movieYear);
            }
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
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
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
                System.out.println("JSON ARRAY POPCORN:" + jsonParentArray);

                //Else it is from Imdb json
                for (int i = 1; i < jsonStringArrayList.size(); i++) {
                    JSONObject searchObject = new JSONObject(jsonStringArrayList.get(i));
                    System.out.println("SEARCH OBJECT:" + searchObject);
                    JSONArray arraySearchObject = new JSONArray();

                    try {
                        //if (searchObject.get("Response") == "True") //TODO
                        arraySearchObject = (JSONArray) searchObject.get("Search");
                    } catch (Exception ignored) {
                    }

                    jsonParentArray.put(arraySearchObject);
                    System.out.println("JSON ARRAY IMDB:" + jsonParentArray);
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
            new DownloadImageTask(moviePosterImageView, view.getContext()).execute(posterUrl);
        }

        /**
         * set Poster element to default picture
         */
        public void setPosterElements() {
            moviePosterImageView.setImageResource(R.color.cardview_dark_background);
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;
            private Context context;

            public DownloadImageTask(ImageView bmImage, Context context) {
                this.bmImage = bmImage;
                this.context = context;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    mIcon11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.la_la_land);
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }
    }
}
