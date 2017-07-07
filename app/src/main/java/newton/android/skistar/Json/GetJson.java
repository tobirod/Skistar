package newton.android.skistar.Json;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

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
import newton.android.skistar.R;
import newton.android.skistar.SettingsActivity;

public class GetJson {

    public static ArrayList<Run> runs;
    public static ArrayList<Run> runsToday;
    public static ArrayList<Run> runsWeek;
    public static ArrayList<Run> runsSeason;
    private Context context;
    private String json = "";

    private SharedPreferences sharedPreferences;

    public static boolean viableId;

    public static String numberRunsToday;
    public static String heightDroppedToday;
    public int checkNewData = 0;

    public static String numberRunsWeek;
    public static String heightDroppedWeek;

    public static String numberRunsSeason;
    public static String heightDroppedSeason;

    String userSkierId;
    String userSeasonId;

    private String ENDPOINT = "";

    public GetJson(Context context){
        this.context = context;
    }

    public void setENDPOINT() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (sharedPreferences.contains("skierId") && sharedPreferences.contains("seasonId")) {
            userSkierId = sharedPreferences.getString("skierId", "");
            userSeasonId = sharedPreferences.getString("seasonId", "");

            ENDPOINT = "https://www.skistar.com/myskistar/api/v2/views/statisticspage.json?entityId=" + userSkierId + "&seasonId=" + userSeasonId;

            Log.i("Endpoint", ENDPOINT);
        }
    }

    public void loadJson() {

        setENDPOINT();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            viableId = true;

            JSONObject jsonObject;

            if (!response.contentEquals(json)) {

                json = response;

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

            } else {
                Log.i("ParseJSON", "No changes to JSON.");
                clearSharedPrefs();
            }

        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            viableId = false;
            Log.e("ParseJSON", error.toString());
            toastMessage("SkierID and/or SeasonID invalid. Please try again.");

            clearSharedPrefs();
        }
    };

    private void parseJson() throws ParseException {

        runsToday.clear();
        runsWeek.clear();
        runsSeason.clear();

        Calendar currentCalendar = Calendar.getInstance();

        String seasonStart = "2016-10-30T00:00:00";
        String seasonEnd = "2017-05-28T23:59:59";

        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Date dateSeasonStart = timeFormat.parse(seasonStart);
        Date dateSeasonEnd = timeFormat.parse(seasonEnd);

        Run testrun1 = new Run("2017-07-04T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Mån, 4 Jul");
        Run testrun2 = new Run("2017-07-04T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Mån, 4 Jul");
        Run testrun3 = new Run("2017-07-04T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Mån, 4 Jul");

        Run testrun4 = new Run("2017-07-04T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Mån, 4 Jul");
        Run testrun5 = new Run("2017-07-04T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Mån, 4 Jul");
        Run testrun6 = new Run("2017-07-04T12:52:01", "F7 Stormyra", "5", "Trysil", "125", "Mån, 4 Jul");

        Run testrun7 = new Run("2017-07-07T08:52:01", "F7 Stormyra", "5", "Trysil", "125", "Fre, 7 Jul");
        Run testrun8 = new Run("2017-07-07T08:52:01", "F7 Stormyra", "5", "Trysil", "125", "Fre, 7 Jul");
        Run testrun9 = new Run("2017-07-07T08:52:01", "F7 Stormyra", "5", "Trysil", "125", "Fre, 7 Jul");

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

            setStatistics();

            if (checkNewData == 0) {
                checkNewData = runsToday.size();
            }

        }

        if (SettingsActivity.autoUpdate && runsToday.size() > 0) {

            if (runsToday.size() > checkNewData) {

                checkNewData = runsToday.size();

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_notification_refresh)
                                .setContentTitle("New run:")
                                .setContentText(runsToday.get(0).getLiftName() + ": " + runsToday.get(0).getHeight() + "m")
                                .setAutoCancel(true);

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(100, mBuilder.build());

            }
        }

        Log.i("runsToday", "have " + runsToday.size() + "posts.");
        Log.i("runsWeek", "have " + runsWeek.size() + "posts.");
        Log.i("runsSeason", "have " + runsSeason.size() + "posts.");
    }

    private void setStatistics() {

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

    private void toastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void clearSharedPrefs() {

        if (sharedPreferences.getString("skierID", "").length() > 0 && sharedPreferences.getString("seasonId", "").length() > 0) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.remove("skierId");
            editor.remove("seasonId");
            editor.commit();

        }


    }
}
