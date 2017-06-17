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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import newton.android.skistar.ListActivity;
import newton.android.skistar.Models.Run;

public class GetJson {

    private static final String ENDPOINT = "https://www.skistar.com/myskistar/api/v2/views/statisticspage.json?entityId=3206&seasonId=11";
    public static ArrayList<Run> runs;
    public static ArrayList<Run> runsToday;
    public static ArrayList<Run> runsWeek;
    public static ArrayList<Run> runsSeason;
    private Context context;

    public String numberRunsToday;
    public String heightDroppedToday;

    public String numberRunsWeek;
    public String heightDroppedWeek;

    public String numberRunsSeason;
    public String heightDroppedSeason;

    public GetJson(Context context){
        this.context = context;
    }

    public void loadJson() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

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

            try {
                parseJson();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (ListActivity.adapter != null) {
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

    public void parseJson() throws ParseException {

        Calendar currentCalendar = Calendar.getInstance();

        String seasonStart = "2016-10-30T00:00:00";
        String seasonEnd = "2017-05-28T23:59:59";

        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Date dateSeasonStart = timeFormat.parse(seasonStart);
        Date dateSeasonEnd = timeFormat.parse(seasonEnd);

        Run testrun1 = new Run("2017-06-16T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Fre, 16 Jun");
        Run testrun2 = new Run("2017-06-16T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Fre, 16 Jun");
        Run testrun3 = new Run("2017-06-16T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Fre, 16 Jun");

        Run testrun4 = new Run("2017-06-15T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Tor, 15 Jun");
        Run testrun5 = new Run("2017-06-15T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Tor, 15 Jun");
        Run testrun6 = new Run("2017-06-15T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Tor, 15 Jun");

        Run testrun7 = new Run("2017-06-17T09:52:01", "F7 Stormyra", "5", "Trysil", "125", "Lör, 17 Jun");
        Run testrun8 = new Run("2017-06-17T09:52:01", "F7 Stormyra", "5", "Trysil", "125", "Lör, 17 Jun");
        Run testrun9 = new Run("2017-06-17T09:52:01", "F7 Stormyra", "5", "Trysil", "125", "Lör, 17 Jun");

        runs.add(testrun1);
        runs.add(testrun2);
        runs.add(testrun3);

        runs.add(testrun4);
        runs.add(testrun5);
        runs.add(testrun6);

        runs.add(testrun7);
        runs.add(testrun8);
        runs.add(testrun9);

        int currentWeek = currentCalendar.get(Calendar.WEEK_OF_YEAR);

        if (runs != null) {

            for (int i = 0; i < runs.size(); i++) {

                Calendar runCalendar = Calendar.getInstance();

                String jsonBuffer = runs.get(i).getSwipeTime();

                Date runTime = timeFormat.parse(jsonBuffer);

                runCalendar.setTime(runTime);

                int runWeek = runCalendar.get(Calendar.WEEK_OF_YEAR);

                boolean sameDay = currentCalendar.get(Calendar.YEAR) == runCalendar.get(Calendar.YEAR) && currentCalendar.get(Calendar.DAY_OF_YEAR) == runCalendar.get(Calendar.DAY_OF_YEAR);

                if (sameDay) {
                    Run runDayBuffer = new Run(
                            runs.get(i).getSwipeTime(),
                            runs.get(i).getLiftName(),
                            runs.get(i).getId(),
                            runs.get(i).getName(),
                            runs.get(i).getHeight(),
                            runs.get(i).getSwipeDate());

                    runsToday.add(runDayBuffer);

                }

                if (runWeek == currentWeek) {
                    Run runWeekBuffer = new Run(
                            runs.get(i).getSwipeTime(),
                            runs.get(i).getLiftName(),
                            runs.get(i).getId(),
                            runs.get(i).getName(),
                            runs.get(i).getHeight(),
                            runs.get(i).getSwipeDate());

                    runsWeek.add(runWeekBuffer);

                }

                if (runTime.after(dateSeasonStart) && runTime.before(dateSeasonEnd)) {
                    Run runSeasonBuffer = new Run(
                            runs.get(i).getSwipeTime(),
                            runs.get(i).getLiftName(),
                            runs.get(i).getId(),
                            runs.get(i).getName(),
                            runs.get(i).getHeight(),
                            runs.get(i).getSwipeDate());

                    runsSeason.add(runSeasonBuffer);

                }
            }
        }

        setStatistics();

        Log.i("runsToday", "have " + runsToday.size() + "posts.");
        Log.i("runsWeek", "have " + runsWeek.size() + "posts.");
        Log.i("runsSeason", "have " + runsSeason.size() + "posts.");

    }

    public void setStatistics() {

        int runsTodayBuffer = 0;
        int heightTodayBuffer = 0;

        int runsWeekBuffer = 0;
        int heightWeekBuffer = 0;

        int runsSeasonBuffer = 0;
        int heightSeasonBuffer = 0;

        for (int i = 0; i < GetJson.runsToday.size(); i++) {

            runsTodayBuffer++;

            String heightBuffer = GetJson.runsToday.get(i).getHeight();

            int runHeight = Integer.parseInt(heightBuffer);

            heightTodayBuffer = heightTodayBuffer + runHeight;

        }

        for (int i = 0; i < GetJson.runsWeek.size(); i++) {

            runsWeekBuffer++;

            String heightBuffer = GetJson.runsWeek.get(i).getHeight();

            int runHeight = Integer.parseInt(heightBuffer);

            heightWeekBuffer = heightWeekBuffer + runHeight;

        }

        for (int i = 0; i < GetJson.runsSeason.size(); i++) {

            runsSeasonBuffer++;

            String heightBuffer = GetJson.runsSeason.get(i).getHeight();

            int runHeight = Integer.parseInt(heightBuffer);

            heightSeasonBuffer = heightSeasonBuffer + runHeight;

        }

        numberRunsToday = String.valueOf(runsTodayBuffer);
        heightDroppedToday = String.valueOf(heightTodayBuffer) + "m";

        numberRunsWeek = String.valueOf(runsWeekBuffer);
        heightDroppedWeek = String.valueOf(heightWeekBuffer) + "m";

        numberRunsSeason = String.valueOf(runsSeasonBuffer);
        heightDroppedSeason = String.valueOf(heightSeasonBuffer) + "m";
    }
}
