package newton.android.skistar;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import newton.android.skistar.ViewModels.ViewModel;
import newton.android.skistar.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewModel viewmodel = new ViewModel(this);
        super.onCreate(savedInstanceState);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewModel(viewmodel);
    }
}
