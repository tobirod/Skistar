package newton.android.skistar;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newton.android.skistar.Models.ListAdapter;
import newton.android.skistar.Models.Run;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {

    private static final String ENDPOINT = "https://www.skistar.com/myskistar/api/v2/views/statisticspage.json?entityId=3206&seasonId=11";

    private RequestQueue requestQueue;
    private Gson gson;
    private ArrayList<Run> runs;
    private String jsonBuffer;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewModel(viewmodel);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        runs = new ArrayList<>();

        parseJson();

        populateListView();
    }

    public void parseJson() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(response);
                JSONArray rideStatistics = jsonObject.getJSONArray("rideStatistics");
                if (runs.size() != 0) {
                    runs.clear();
                }
                for (int i = 0; i < rideStatistics.length(); i++) {
                    Run runBuffer = new Run(rideStatistics.getJSONObject(i).get("swipeTime").toString(),
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

            populateListView();
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ParseJSON", error.toString());
        }
    };

    public void populateListView() {

        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new ListAdapter(runs, getApplicationContext());

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_header, listView, false);

        listView.addHeaderView(header, null, false);
        listView.setAdapter(adapter);
    }
}
