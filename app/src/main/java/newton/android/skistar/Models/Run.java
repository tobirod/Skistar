package newton.android.skistar.Models;

public class Run {

    private String swipeTime;
    private String liftName;
    private String id;
    private String name;
    private String height;
    private String swipeDate;

    public Run(String swipeTime, String liftName, String id, String name, String height, String swipeDate) {
        this.swipeTime = swipeTime;
        this.liftName = liftName;
        this.id = id;
        this.name = name;
        this.height = height;
        this.swipeDate = swipeDate;
    }

    public String getSwipeTime() {
        return swipeTime;
    }

    public void setSwipeTime(String swipeTime) {
        this.swipeTime = swipeTime;
    }

    public String getLiftName() {
        return liftName;
    }

    public void setLiftName(String liftName) {
        this.liftName = liftName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSwipeDate() {
        return swipeDate;
    }

    public void setSwipeDate(String swipeDate) {
        this.swipeDate = swipeDate;
    }

}
