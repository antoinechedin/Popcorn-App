package crystalgems.popcorn;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 26/03/2017.
 */

public class MovieDetailsActivity extends Activity {
    private RecyclerView movieDetailsRecyclerView;
    private RecyclerView.LayoutManager movieDetailsLayoutManager;
    private RecyclerView.Adapter rvAdapter;
    private TextView releaseDateValue;
    private TextView directorValue;
    private TextView actorsValue;
    private TextView categoriesValue;
    private TextView nationalityValue;


    private String[] customDataset = {"Premier", "Deuxième la la la la la la la la la la la la la la la la la la la la la la la la", "Troisième", "Quatrième la la la la la la la la la", "Cinquième", "Sixième", "Septième", "Huitième"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        releaseDateValue = (TextView) findViewById(R.id.releaseDateValue);
        directorValue = (TextView) findViewById(R.id.directorValue);
        actorsValue = (TextView) findViewById(R.id.actorsValue);
        categoriesValue = (TextView) findViewById(R.id.categoriesValue);
        nationalityValue = (TextView) findViewById(R.id.nationalityValue);

        movieDetailsRecyclerView = (RecyclerView) findViewById(R.id.movie_details_recycler_view);

        // Improve performance if we know RecyclerView will have fixed size, which is the case
        movieDetailsRecyclerView.setHasFixedSize(true);

        movieDetailsLayoutManager = new LinearLayoutManager(this, 0, false);
        movieDetailsRecyclerView.setLayoutManager(movieDetailsLayoutManager);

        // specify an adapter to create views for items in the recycler view
        rvAdapter = new MovieDetailsRecyclerViewAdapter(customDataset);
        movieDetailsRecyclerView.setAdapter(rvAdapter);



    }
}
