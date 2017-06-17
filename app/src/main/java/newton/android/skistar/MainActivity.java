package newton.android.skistar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    TextView statistics_day_run;
    TextView statistics_day_height;
    TextView statistics_week_run;
    TextView statistics_week_height;
    TextView statistics_season_run;
    TextView statistics_season_height;

    GetJson getJson = new GetJson(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewmodel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Day");
        spec.setContent(R.id.statistics_day);
        spec.setIndicator("Today");
        tabHost.addTab(spec);

        statistics_day_run = (TextView) findViewById(R.id.statistics_today_runs);
        statistics_day_height = (TextView) findViewById(R.id.statistics_today_height);

        //Tab 2
        spec = tabHost.newTabSpec("Week");
        spec.setContent(R.id.statistics_week);
        spec.setIndicator("This week");
        tabHost.addTab(spec);

        statistics_week_run = (TextView) findViewById(R.id.statistics_week_runs);
        statistics_week_height = (TextView) findViewById(R.id.statistics_week_height);

        //Tab 3
        spec = tabHost.newTabSpec("Season");
        spec.setContent(R.id.statistics_season);
        spec.setIndicator("This season");
        tabHost.addTab(spec);

        statistics_season_run = (TextView) findViewById(R.id.statistics_season_runs);
        statistics_season_height = (TextView) findViewById(R.id.statistics_season_height);

        if (GetJson.runs == null) {
            GetJson.runs = new ArrayList<>();
            GetJson.runsToday = new ArrayList<>();
            GetJson.runsWeek = new ArrayList<>();
            GetJson.runsSeason = new ArrayList<>();
            getJson.loadJson();
        }

        setStatistics();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                setStatistics();
                return true;

            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void allRunsButtonClick(View v) {

        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void setStatistics() {

        statistics_day_run.setText(getJson.numberRunsToday);
        statistics_day_height.setText(getJson.heightDroppedToday);

        statistics_week_run.setText(getJson.numberRunsWeek);
        statistics_week_height.setText(getJson.heightDroppedWeek);

        statistics_season_run.setText(getJson.numberRunsSeason);
        statistics_season_height.setText(getJson.heightDroppedSeason);

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
