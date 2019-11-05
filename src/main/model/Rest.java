package model;

public class Rest extends TimeStamp {

    //FIELDS
    private String type;

    //CONSTRUCTOR
    public Rest(String t) {
        super();
        setType(t);
    }

    //METHODS



    //EFFECTS: returns a string representing the object in CSV line format
    //  --/--,type, day, duration \n
    @Override
    public String toString() {
        return "--/--," + getType() + "," + getDay() + "," + getDuration() + "\\n";
    }

    public String getName() {
        return "Rest";
    }
}
