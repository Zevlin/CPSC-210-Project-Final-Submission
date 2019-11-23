import model.Activity;
import model.IntervalTooSmallException;
import model.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import utilities.*;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class UtilitiesTest {

    TimeLogger logger;
    Date day;

    @BeforeEach
    public void beforeEach() throws Exception{
        logger = new TimeLogger();
        day = new Date();
    }

    @Test
    public void newTimestampTest() {
        logger.newActivity(new Activity("name", "type"));
        assertEquals("name", logger.getStampName());
        assertEquals(0, logger.getStampDuration());
        assertEquals(day.toString(), logger.getStampStart());
        logger.newRest(new Rest("type"));
        assertEquals("Rest", logger.getStampName());
        assertEquals(0, logger.getStampDuration());
        assertEquals(day.toString(), logger.getStampStart());
    }

    @Test
    public void expectedTimeLoggerIntervalTooSmallTest() {
        try {
            logger.getUpdateInterval();
            fail("No update interval exception detected.");
        }
        catch (IntervalTooSmallException its) {
            System.out.println("IntervalTooSmallException caught");
        }
    }

    @Test
    public void unexpectedTimeLoggerIntervalTooSmallTest() {
        try {
            logger.setUpdateInterval(1000);
            logger.getUpdateInterval();
            logger.setUpdateInterval(1001);
            logger.getUpdateInterval();

        } catch (IntervalTooSmallException its) {
            fail("IntervalTooSmallException was not expected");
        }
    }

    @Test
    public void saveDataTest() throws Exception {
        File f = new File(".\\Data\\timeLog.csv");
        if (f.exists()) {
            f.delete();
        }
        assertFalse(f.exists());
        logger.saveData(new ArrayList(){});
        assertTrue(f.exists());
    }

    @Test
    public void loadDataTest() throws Exception {
        File f = new File(".\\Data\\timeLog.csv");
        if (f.exists()) {
            f.delete();
        }
        assertFalse(f.exists());
        PrintWriter out = new PrintWriter(new FileOutputStream((f)));
        out.println("test");
        out.close();
        assertEquals("test", logger.loadData().get(0));
    }

}
