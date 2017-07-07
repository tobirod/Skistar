package newton.android.skistar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    EditText skierId;
    EditText seasonId;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    GetJson getJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewmodel);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        skierId = (EditText) findViewById(R.id.form_skierId);
        seasonId = (EditText) findViewById(R.id.form_seasonId);

        if (GetJson.runs == null) {
            GetJson.runs = new ArrayList<>();
            GetJson.runsToday = new ArrayList<>();
            GetJson.runsWeek = new ArrayList<>();
            GetJson.runsSeason = new ArrayList<>();
        }
    }

    public void loginButton(View view) throws InterruptedException {

        if (skierId.getText().length() != 0 && seasonId.getText().length() != 0) {

            editor = sharedpreferences.edit();

            editor.putString("skierId", skierId.getText().toString());
            editor.putString("seasonId", seasonId.getText().toString());
            editor.commit();

            getJson = new GetJson(this);

            getJson.loadJson();

            if (GetJson.viableId) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                MainActivity.this.startActivity(intent);
            }

        } else {
            toastMessage("Fields cannot be null. Please enter both skierID and seasonID.");
        }
    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
