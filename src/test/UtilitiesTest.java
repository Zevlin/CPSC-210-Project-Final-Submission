import model.Activity;
import model.Rest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import utilities.*;

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
}
