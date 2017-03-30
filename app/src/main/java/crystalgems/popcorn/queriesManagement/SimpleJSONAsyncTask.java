package crystalgems.popcorn.queriesManagement;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alex on 29/03/2017.
 */

public class SimpleJSONAsyncTask extends AsyncTask<String, Void, String> {
    private static Response response;
    private OkHttpClient client;
    private AsyncTaskListener listener;
    private String responseString;
    private URL url;

    public SimpleJSONAsyncTask(AsyncTaskListener asyncTaskListener) {
        this.client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS).build();
        listener = asyncTaskListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            url = new URL(params[0]);
            Request requestPopcorn = new Request.Builder().url(url).build();
            response = client.newCall(requestPopcorn).execute();
            responseString = response.body().string();
        }
        catch (IOException e ) {
        e.printStackTrace();
    }
        return responseString;
    }

    @Override
    protected void onPostExecute(String resultString) {
        Log.e("JSONAsyncTask " + url.toString(), "Finished");
        try {
            listener.onPostAsyncTask(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
