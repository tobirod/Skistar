package newton.android.skistar.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class ViewModel extends BaseObservable {

    private Context context;

    private String date;
    private String lift;
    private String location;
    private String height;

    public ViewModel(Context context){
        this.context = context;
    }

    public void setDate(String date){
        this.date = date;
        notifyPropertyChanged(newton.android.skistar.BR.date);
    }

    public void setLift(String lift){
        this.lift = lift;
        notifyPropertyChanged(newton.android.skistar.BR.lift);
    }

    public void setLocation(String location){
        this.location = location;
        notifyPropertyChanged(newton.android.skistar.BR.location);
    }

    public void setHeight(String height){
        this.height = height;
        notifyPropertyChanged(newton.android.skistar.BR.height);
    }

    @Bindable
    public String getDate() {
        return date;
    }
    @Bindable
    public String getLift() {
        return lift;
    }
    @Bindable
    public String getLocation() {
        return location;
    }
    @Bindable
    public String getHeight() {
        return height;
    }

}
