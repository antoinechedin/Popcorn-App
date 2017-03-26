package crystalgems.popcorn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Alex on 26/03/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    private String[] dataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(final View vhView) {
            super(vhView);
            view = vhView;
        }


    }

    public HomeRecyclerViewAdapter(String[] dataset) {
        this.dataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        this.context = parent.getContext();

        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_view, parent, false);
        // We always can set the view's size, margins, paddings and layout parameters here

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Movie picture
        ImageView moviePicture = (ImageView) holder.view.findViewById(R.id.movie_picture);
        moviePicture.setImageResource(R.drawable.la_la_land);

        // Movie title
        TextView movieTitle = (TextView) holder.view.findViewById(R.id.movie_title);
        movieTitle.setText(dataset[position]);
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
