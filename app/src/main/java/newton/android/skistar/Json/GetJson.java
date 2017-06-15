package newton.android.skistar.Json;

import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import newton.android.skistar.ListActivity;
import newton.android.skistar.Models.Run;

public class GetJson {

    private static final String ENDPOINT = "https://www.skistar.com/myskistar/api/v2/views/statisticspage.json?entityId=3206&seasonId=11";
    public static ArrayList<Run> runs = new ArrayList<>();
    private Context context;

    public GetJson(Context context){
        this.context = context;
    }

    public void loadJson() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);

        Log.i("Test", "LoadJson");
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            JSONObject jsonObject;

            Log.i("Test", "Response");

            try {
                jsonObject = new JSONObject(response);
                JSONArray rideStatistics = jsonObject.getJSONArray("rideStatistics");
                if (runs.size() != 0) {
                    runs.clear();
                }
                for (int i = 0; i < rideStatistics.length(); i++) {
                    Run runBuffer = new Run(
                            rideStatistics.getJSONObject(i).get("swipeTime").toString(),
                            rideStatistics.getJSONObject(i).get("liftName").toString(),
                            rideStatistics.getJSONObject(i).getJSONObject("destination").get("id").toString(),
                            rideStatistics.getJSONObject(i).getJSONObject("destination").get("name").toString(),
                            rideStatistics.getJSONObject(i).get("height").toString(),
                            rideStatistics.getJSONObject(i).get("swipeDate").toString());

                    runs.add(runBuffer);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (ListActivity.adapter != null) {
                Run testrun = new Run("2018-05-13T12:51:41", "123", "123", "123", "123", "123");

                runs.add(testrun);

                ListActivity.adapter.notifyDataSetChanged();
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ParseJSON", error.toString());
        }
    };
}
