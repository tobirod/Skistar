package newton.android.skistar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewmodel);
    }


    public void allRunsButtonClick(View v) {

        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
