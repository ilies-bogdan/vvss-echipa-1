package tasks.lab04.unit;


import org.junit.Before;
import org.junit.Test;
import tasks.model.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private Task task;
    private Date time;
    private Date startTime;
    private Date endTime;
    private static final int INTERVAL = 60 * 60;

    @Before
    public void setUp() {
        time = new Date();
        startTime = new Date(time.getTime());
        endTime = new Date(startTime.getTime() + INTERVAL * 1000);
        task = new Task("Task", startTime, endTime, INTERVAL);
    }

    @Test
    public void shouldSetActive() {
        task.setActive(true);
        assertTrue(task.isActive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNegativeTime() {
        new Task("Task", new Date(-1));
    }
}
