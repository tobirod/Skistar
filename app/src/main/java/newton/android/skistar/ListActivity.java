package newton.android.skistar;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import newton.android.skistar.Json.GetJson;
import newton.android.skistar.Models.ListAdapter;
import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    public static ListAdapter adapter;

    GetJson getJson = new GetJson(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewModel(viewmodel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_header, listView, false);

        listView.addHeaderView(header, null, false);
        listView.setAdapter(adapter);

        populateListView();
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
                refreshActionButton();
                return true;

            case R.id.action_settings:
                toastMessage("Settings");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateListView() {

        adapter = new ListAdapter(GetJson.runs, getApplicationContext());
        listView.setAdapter(adapter);

        Log.i("Test", "Populate");

        adapter.notifyDataSetChanged();
    }

    public void refreshActionButton() {
        getJson.loadJson();

        adapter.notifyDataSetChanged();

        Log.i("Test", "Refresh");
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
