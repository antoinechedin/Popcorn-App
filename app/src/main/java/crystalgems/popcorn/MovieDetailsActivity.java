package crystalgems.popcorn;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Alex on 26/03/2017.
 */

public class MovieDetailsActivity extends Activity implements View.OnClickListener {
    private RecyclerView movieDetailsRecyclerView;
    private RecyclerView.LayoutManager movieDetailsLayoutManager;
    private RecyclerView.Adapter rvAdapter;

    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        movieDetailsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_recycler_view);

        // Improve performance if we know components will have fixed size, which is the case
        movieDetailsRecyclerView.setHasFixedSize(true);

        movieDetailsLayoutManager = new LinearLayoutManager(this, 0, false);
        movieDetailsRecyclerView.setLayoutManager(movieDetailsLayoutManager);

        // specify an adapter to create views for items in the recycler view
        rvAdapter = new MovieDetailsRecyclerViewAdapter(customDataset);
        movieDetailsRecyclerView.setAdapter(rvAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
