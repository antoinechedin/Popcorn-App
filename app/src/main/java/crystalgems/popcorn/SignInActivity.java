package crystalgems.popcorn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import crystalgems.popcorn.homepage.HomeActivity;
import crystalgems.popcorn.user.SignUpActivity;

/**
 * Created by Tracy on 30/03/2017.
 */

public class SignInActivity extends Activity {

    private Button homeButton;
    private Button signUpButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_sign_up_activity);

        homeButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        context = this;

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


}
