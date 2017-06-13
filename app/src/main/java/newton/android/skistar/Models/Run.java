package newton.android.skistar.Models;

import com.google.gson.annotations.SerializedName;

public class Run {

    @SerializedName("swipeTime")
    private String date;

    @SerializedName("liftName")
    private String lift;

    @SerializedName("name")
    private String location;

    private String height;

    public Run(String date, String lift, String location, String height) {
        this.date = date;
        this.lift = lift;
        this.location = location;
        this.height = height;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLift() {
        return lift;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}
