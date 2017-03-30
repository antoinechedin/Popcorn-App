package crystalgems.popcorn.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import crystalgems.popcorn.R;
import crystalgems.popcorn.homepage.HomeActivity;

/**
 * Created by Tracy on 29/03/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private Button modifier;
    private Button retour;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        modifier = (Button) findViewById(R.id.modifierButton);
        retour = (Button) findViewById(R.id.returnButton);
        context = this;

        //TODO bouton modifier infos utilisateur

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


}
