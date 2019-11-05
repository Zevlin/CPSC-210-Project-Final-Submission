import model.TimeStamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Activity;
import model.Rest;

import java.util.Date;

public class TimeStampTest {
    TimeStamp activity;
    TimeStamp rest;
    long start;
    Date day;

    @BeforeEach
    public void beforeEach() {
        activity = new Activity("name", "type");
        rest = new Rest("type");
        start = System.currentTimeMillis();
        day = new Date();
    }

    @Test
    public void getDayTest() {
        assertEquals(day, activity.getDay());
        assertEquals(day, rest.getDay());
    }

    @Test
    public void getDurationTest() {
        assertEquals(0, activity.getDuration());
        assertEquals(0, rest.getDuration());
    }

    @Test
    public void getNameTest() {
        assertEquals("name", activity.getName());
        assertEquals("Rest", rest.getName());
    }

    @Test
    public void getTypeTest() {
        assertEquals("type", activity.getType());
        assertEquals("type", rest.getType());
    }

    @Test
    public void toStringTest() {
        assertEquals("name,type," + day.toString() + ",0" + "\\n", activity.toString());
        assertEquals("--/--,type," + day.toString() + ",0" + "\\n", rest.toString());
    }

}
