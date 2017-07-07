package newton.android.skistar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.Services.AutoUpdate;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    public static boolean autoUpdate;
    public static long updateTime;

    public EditText autoUpdateTime;

    EditText skierId;
    EditText seasonId;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    GetJson getJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        binding.setViewModel(viewmodel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        skierId = (EditText) findViewById(R.id.updateSkierId);
        seasonId = (EditText) findViewById(R.id.updateSeasonId);

        Switch autoUpdateSwitch = (Switch) findViewById(R.id.autoUpdateSwitch);

        autoUpdateTime = (EditText) findViewById(R.id.autoUpdateTimeEditText);

        autoUpdateSwitch.setChecked(false);
        autoUpdateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    autoUpdate = isChecked;
                    Intent intent = new Intent(SettingsActivity.this, AutoUpdate.class);
                    startService(intent);
                } else {
                    autoUpdate = isChecked;
                    Intent intent = new Intent(SettingsActivity.this, AutoUpdate.class);
                    stopService(intent);
                }
            }
        });
    }

    public void setUpdateTime(View view) {

        if (autoUpdateTime.getText().length() >= 1) {

            long minutes = Integer.parseInt(autoUpdateTime.getText().toString());
            long seconds = minutes*60;
            long milliseconds = seconds*1000;

            updateTime = milliseconds;

            toastMessage("Interval refreshed.");

        } else {
            updateTime = 300000;
        }

    }

    public void updateAndRefreshButton(View view) throws InterruptedException {

        if (skierId.getText().length() != 0 && seasonId.getText().length() != 0) {

            editor = sharedpreferences.edit();

            editor.putString("skierId", skierId.getText().toString());
            editor.putString("seasonId", seasonId.getText().toString());
            editor.commit();

            getJson = new GetJson(this);

            getJson.loadJson();

            if (GetJson.viableId) {
                Intent intent = new Intent(SettingsActivity.this, StatisticsActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        } else {
            toastMessage("Fields cannot be null. Please enter both skierID and seasonID.");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
