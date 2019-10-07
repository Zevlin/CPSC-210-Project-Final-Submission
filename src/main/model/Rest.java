package model;

public class Rest extends Timestamp {

    //FIELDS
    private String type;

    //CONSTRUCTOR
    public Rest(String t) {
        super();
        setType(t);
    }

    //METHODS

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
    //  --/--,type, day, duration \n
    @Override
    public String toString() {
        return "--/--," + getType() + "," + getDay() + "," + getDuration() + "\\n";
    }
}
