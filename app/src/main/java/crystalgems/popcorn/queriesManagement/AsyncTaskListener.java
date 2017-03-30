package crystalgems.popcorn.queriesManagement;

import org.json.JSONException;

/**
 * Created by Antoine on 30/03/2017.
 */

public interface AsyncTaskListener {
    public void onPostAsyncTask(String jsonString) throws JSONException;
}
