package crystalgems.popcorn.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import crystalgems.popcorn.R;

/**
 * Created by Tracy on 29/03/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

}
