package utilities;

import model.TimeStamp;

import java.util.ArrayList;

public interface Saveable {

    public void saveData(ArrayList<TimeStamp> l) throws Exception;

}
