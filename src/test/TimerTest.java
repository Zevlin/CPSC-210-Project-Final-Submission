import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import utilities.Timer;


public class TimerTest {
    Timer timer;
    long startSysTime;
    long endSysTime;

    @BeforeEach
    public void runBefore() {
        timer = new Timer();
        startSysTime = System.currentTimeMillis();
        endSysTime = System.currentTimeMillis();
    }

    @Test
    public void testConstructor() {
        assertEquals("other", timer.getActivityName());
        assertEquals("other", timer.getActivityType());
        assertEquals(startSysTime, timer.getStartSysTime());
        assertEquals(endSysTime, timer.getEndSysTime());
        assertEquals(endSysTime-startSysTime, timer.getElapsedTime());
    }

    @Test
    public void setIsRecordingTest() {
        assertFalse(timer.getIsRecording());
        timer.setIsRecording(true);
        assertTrue(timer.getIsRecording());
    }

    @Test
    public void setActivityTypeTest() {
        assertEquals("other", timer.getActivityType());
        timer.setActivityType("work");
        assertEquals("work", timer.getActivityType());
    }

    @Test
    public void setActivityNameTest() {
        assertEquals("other", timer.getActivityName());
        timer.setActivityName("work");
        assertEquals("work", timer.getActivityName());
    }



}
