package model;

public class Activity extends TimeStamp {

    //FIELDS
    private String name;

    //CONSTRUCTOR
    public Activity(String n, String t) {
        super();
        setName(n);
        setType(t);
    }

    //METHODS

    //MODIFIES: this
    //EFFECTS: changes name value to input value
    public void setName(String n) {
        name = n;
    }

    //EFFECTS: returns name value
    public String getName() {
        return name;
    }

    //EFFECTS: returns a string representing the object in CSV line format
    //  name, type, day, duration \n
    @Override
    public String toString() {
        return getName() + "," + getType() + "," + getDay() + "," + getDuration() + "\\n";
    }
}
