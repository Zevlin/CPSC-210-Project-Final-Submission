package model;

public class Activity extends Timestamp {

    //FIELDS
    private String name;
    private String type;

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

    //MODIFIES: this
    //EFFECTS: changes type value to input value
    public void setType(String t) {
        type = t;
    }

    //EFFECTS: returns type value
    public String getType() {
        return type;
    }

    //EFFECTS: returns a string representing the object in CSV line format
    //  name, type, day, duration \n
    @Override
    public String toString() {
        return getName() + "," + getType() + "," + getDay() + "," + getDuration() + "\\n";
    }
}
