package crystalgems.popcorn.QueriesManagement;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alex on 29/03/2017.
 */

public class JSONAsyncTask extends AsyncTask<String, Void, String[]> {

    public interface StringConsumer {
        void setJSONString(String jsonString) throws JSONException;
    }

    private OkHttpClient clientPopcorn;
    private OkHttpClient clientImdb;
    private static Response responsePopcorn;
    private static Response responseImdb;
    private StringConsumer jsonBodyText;
    private StringConsumer jsonBodyPoster;
    private String urlPopcornResponse;
    private String urlImdbResponse;

    public JSONAsyncTask(StringConsumer stringConsumer) {
        this.clientPopcorn = new OkHttpClient();
        this.clientImdb = new OkHttpClient();
        jsonBodyText = stringConsumer;
        jsonBodyPoster = stringConsumer;
    }

    @Override
    protected String[] doInBackground(String... params) {
        try {
            URL urlPopcorn = new URL(params[0]);
            URL urlImdb = new URL(params[1]);
            Request requestPopcorn = new Request.Builder().url(urlPopcorn).build();
            responsePopcorn = clientPopcorn.newCall(requestPopcorn).execute();
            urlPopcornResponse = responsePopcorn.body().string();

            Request requestImdb = new Request.Builder().url(urlImdb).build();
            responseImdb = clientImdb.newCall(requestImdb).execute();
            urlImdbResponse = responseImdb.body().string();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{urlPopcornResponse, urlImdbResponse};
    }

    @Override
    protected void onPostExecute(String[] result) {
        Log.e("JSONAsyncTask", "Finished");
        try {
            jsonBodyText.setJSONString(result[0]);
            jsonBodyPoster.setJSONString(result[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
