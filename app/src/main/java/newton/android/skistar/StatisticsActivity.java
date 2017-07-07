package newton.android.skistar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityStatisticsBinding;

public class StatisticsActivity extends AppCompatActivity {

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

        ActivityStatisticsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics);
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
                Intent intent = new Intent(StatisticsActivity.this, SettingsActivity.class);
                StatisticsActivity.this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void allRunsButtonClick(View v) {

        Intent intent = new Intent(StatisticsActivity.this, ListActivity.class);
        StatisticsActivity.this.startActivity(intent);
    }

    public void setStatistics() {

        getJson.loadJson();

        statistics_day_run.setText(GetJson.numberRunsToday);
        statistics_day_height.setText(GetJson.heightDroppedToday);

        statistics_week_run.setText(GetJson.numberRunsWeek);
        statistics_week_height.setText(GetJson.heightDroppedWeek);

        statistics_season_run.setText(GetJson.numberRunsSeason);
        statistics_season_height.setText(GetJson.heightDroppedSeason);

    }
}
