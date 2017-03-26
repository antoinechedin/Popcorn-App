package crystalgems.popcorn;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Alex on 26/03/2017.
 */

class MovieDetailsActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
    }

    @Override
    public void onClick(View v) {

    }
}
