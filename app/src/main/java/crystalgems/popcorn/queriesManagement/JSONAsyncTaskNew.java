package crystalgems.popcorn.queriesManagement;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alex on 29/03/2017.
 */

public class JSONAsyncTaskNew extends AsyncTask<String, Void, String>{

    public interface StringConsumer {
        void setJSONString(String jsonString) throws JSONException;
    }

    private OkHttpClient client;
    private static Response response;
    private StringConsumer jsonBody;
    private String urlResponse;

    public JSONAsyncTaskNew(StringConsumer stringConsumer) {
        this.client = new OkHttpClient();
        jsonBody = stringConsumer;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL urlPopcorn = new URL(params[0]);
            Request requestPopcorn = new Request.Builder().url(urlPopcorn).build();
            response = client.newCall(requestPopcorn).execute();
            urlResponse = response.body().string();
        }
        catch (IOException e ) {
        e.printStackTrace();
    }
        return urlResponse;
    }

    private ArrayList<String> getMovieTitlesFromJsonResponse(String urlPopcornResponse, ArrayList<String> movieTitlesArrayList) throws JSONException {
        JSONArray jsonArray = new JSONArray(urlPopcornResponse);
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);
            movieTitlesArrayList.add(jsonObject.getString("titleImdb").replace(' ', '+')); //We also replace the spaces with + for the url
        }

        return movieTitlesArrayList;
    }

    @Override
    protected void onPostExecute(String resultString) {
        Log.e("JSONAsyncTask", "Finished");
        try {
            jsonBody.setJSONString(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
