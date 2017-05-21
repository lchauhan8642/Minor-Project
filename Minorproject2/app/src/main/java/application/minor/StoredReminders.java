package application.minor;


public class StoredReminders {
    String address,message,task,date;
    double latitude,longitude,radius;
    int id;
    public StoredReminders()
    {}

    public StoredReminders(int id,String address, String message, String task, String date, double latitude, double longitude,double radius) {
        this.id=id;
        this.address = address;
        this.message = message;
        this.task = task;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius=radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
