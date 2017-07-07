package newton.android.skistar.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.ListActivity;
import newton.android.skistar.R;
import newton.android.skistar.SettingsActivity;

public class AutoUpdate extends IntentService {

    static SettingsActivity settingsActivity = new SettingsActivity();
    GetJson getJson = new GetJson(this);

    public static long time = settingsActivity.updateTime;

    public static boolean autoUpdateEnabled = settingsActivity.autoUpdate;

    public AutoUpdate() {
        super("AutoUpdate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        toastMessage("Service started.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toastMessage("Service stopped.");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        autoUpdateEnabled = settingsActivity.autoUpdate;

        synchronized (this) {

            while (autoUpdateEnabled) {

                Log.i("AutoUpdate", "Interval");

                if (time == 0) {
                    time = 300000;
                }

                try {
                    Thread.sleep(time);
                    getJson.loadJson();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

                autoUpdateEnabled = settingsActivity.autoUpdate;
                time = settingsActivity.updateTime;

                if (!autoUpdateEnabled) {
                    stopService(intent);
                    break;
                }

            }

        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
