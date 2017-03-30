package crystalgems.popcorn.queriesManagement;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alex on 29/03/2017.
 */

public class JSONAsyncTask extends AsyncTask<String, Void, ArrayList<String>>{

    private static Response responsePopcorn;
    private static Response responseImdb;
    private OkHttpClient clientPopcorn;
    private OkHttpClient clientImdb;
    private StringConsumer jsonBody;
    private String urlPopcornResponse;
    private String urlImdbResponse;
    private ArrayList<String> movieTitlesArrayList;
    private ArrayList<String> urlResponsesArrayList;
    public JSONAsyncTask(StringConsumer stringConsumer) {
        this.clientPopcorn = new OkHttpClient().newBuilder().readTimeout(180, TimeUnit.SECONDS).build();
        this.clientImdb = new OkHttpClient().newBuilder().readTimeout(180, TimeUnit.SECONDS).build();
        ;
        jsonBody = stringConsumer;
        movieTitlesArrayList = new ArrayList<>();
        urlResponsesArrayList = new ArrayList<>();
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        try {
            URL urlPopcorn = new URL(params[0]);
            String urilImdbString = "http://www.omdbapi.com/?s=";
            Request requestPopcorn = new Request.Builder()

                    .url(urlPopcorn).build();
            responsePopcorn = clientPopcorn.newCall(requestPopcorn).execute();
            urlPopcornResponse = responsePopcorn.body().string();
            urlResponsesArrayList.add(urlPopcornResponse);

            movieTitlesArrayList = getMovieTitlesFromJsonResponse(urlPopcornResponse, movieTitlesArrayList);

            for (int i = 0; i < movieTitlesArrayList.size(); i++) {
                URL posterUrl = new URL(urilImdbString + movieTitlesArrayList.get(i));
                Request requestImdb = new Request.Builder().url(posterUrl).build();
                responseImdb = clientImdb.newCall(requestImdb).execute();
                urlImdbResponse = responseImdb.body().string();
                urlResponsesArrayList.add(urlImdbResponse);
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return urlResponsesArrayList;
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
    protected void onPostExecute(ArrayList<String> resultsArrayList) {
        Log.e("JSONAsyncTask", "Finished");
        try {
            jsonBody.setJSONString(resultsArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface StringConsumer {
        void setJSONString(ArrayList<String> jsonString) throws JSONException;
    }
}

