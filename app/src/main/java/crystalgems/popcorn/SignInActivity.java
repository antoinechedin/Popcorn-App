package crystalgems.popcorn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import crystalgems.popcorn.connexion.Connexion;
import crystalgems.popcorn.homepage.HomeActivity;
import crystalgems.popcorn.queriesManagement.AsyncTaskListener;
import crystalgems.popcorn.queriesManagement.SimpleJSONAsyncTask;
import crystalgems.popcorn.user.SignUpActivity;

/**
 * Created by Tracy on 30/03/2017.
 */

public class SignInActivity extends Activity implements AsyncTaskListener {

    private AsyncTaskListener asyncTaskListener;
    private EditText loginEditText;
    private EditText passwordEditText;
    private Button homeButton;
    private Button signUpButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_sign_up_activity);
        asyncTaskListener = this;

        homeButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        context = this;

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleJSONAsyncTask asyncTask = new SimpleJSONAsyncTask(asyncTaskListener);
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://89.88.35.148:8080/popcorn/webapi/get/user-login?login=" + loginEditText.getText());
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });

        if (Connexion.getInstance().getUserId() != 0) {
            // TODO : Close this activity when start HomeActivity
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPostAsyncTask(String jsonString) throws JSONException {
        if (jsonString != null) {
            JSONObject jsonObject = new JSONObject(jsonString);
            int id = jsonObject.getInt("id");
            Connexion.getInstance().setUserId(id);

            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        }
    }
}
