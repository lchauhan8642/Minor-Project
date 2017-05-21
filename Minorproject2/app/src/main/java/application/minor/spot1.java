package application.minor;


public class spot1 {
    String place,message,date;
    int radius;
    int id;
    public spot1()
    {}


    public spot1( int id,String message,  String place,String date,  int radius) {
        this.id=id;
        this.message = message;
        this.place=place;
        this.date = date;

        this.radius=radius;

    }
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

