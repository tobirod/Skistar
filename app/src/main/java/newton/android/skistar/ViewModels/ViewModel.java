package newton.android.skistar.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class ViewModel extends BaseObservable {

    private Context context;

    private String swipeTime;
    private String liftName;
    private String id;
    private String name;
    private String height;
    private String swipeDate;

    private String skierId;
    private String seasonId;

    public ViewModel(Context context){
        this.context = context;
    }

    public void setSkierId (String skierId) {
        this.skierId = skierId;
        notifyPropertyChanged(newton.android.skistar.BR.skierId);
    }

    public void setSeasonId (String seasonId) {
        this.seasonId = seasonId;
        notifyPropertyChanged(newton.android.skistar.BR.seasonId);
    }

    public void setSwipeTime(String swipeTime){
        this.swipeTime = swipeTime;
        notifyPropertyChanged(newton.android.skistar.BR.swipeTime);
    }

    public void setLiftName(String liftName){
        this.liftName = liftName;
        notifyPropertyChanged(newton.android.skistar.BR.liftName);
    }

    public void setId(String id){
        this.id = id;
        notifyPropertyChanged(newton.android.skistar.BR.id);
    }

    public void setName(String name){
        this.name = name;
        notifyPropertyChanged(newton.android.skistar.BR.name);
    }

    public void setHeight(String height){
        this.height = height;
        notifyPropertyChanged(newton.android.skistar.BR.height);
    }

    public void setSwipeDate(String swipeDate){
        this.swipeDate = swipeDate;
        notifyPropertyChanged(newton.android.skistar.BR.swipeDate);
    }

    @Bindable
    public String getSkierId() {
        return skierId;
    }
    @Bindable
    public String getSeasonId() {
        return seasonId;
    }
    @Bindable
    public String getSwipeTime() {
        return swipeTime;
    }
    @Bindable
    public String getLiftName() {
        return liftName;
    }
    @Bindable
    public String getId() {
        return id;
    }
    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public String getHeight() {
        return height;
    }
    @Bindable
    public String getSwipeDate() {
        return swipeDate;
    }

}
