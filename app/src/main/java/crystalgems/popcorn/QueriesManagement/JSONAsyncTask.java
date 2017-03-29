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

public class JSONAsyncTask extends AsyncTask<String, Void, String> {

    public interface StringConsumer {
        void setJSONString(String jsonString) throws JSONException;
    }

    private OkHttpClient client;
    private static Response response;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private StringConsumer jsonBody;

    public JSONAsyncTask(StringConsumer stringConsumer) {
        this.client = new OkHttpClient();
        jsonBody = stringConsumer;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            Request request = new Request.Builder().url(url).build();
            response = client.newCall(request).execute();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("JSONAsyncTask", "Finished");
        try {
            jsonBody.setJSONString(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
