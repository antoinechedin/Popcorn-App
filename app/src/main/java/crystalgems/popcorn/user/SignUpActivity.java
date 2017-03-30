package crystalgems.popcorn.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import crystalgems.popcorn.R;
import crystalgems.popcorn.homepage.HomeActivity;

/**
 * Created by Tracy on 30/03/2017.
 */

public class SignUpActivity extends Activity {

    private Button inscription;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        inscription = (Button) findViewById(R.id.signUpButton);
        context = this;

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

}
