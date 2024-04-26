package tasks.lab04.unit;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private Task task;
    private Date time;
    private Date startTime;
    private Date endTime;
    private static final int INTERVAL = 60 * 60;

    @BeforeEach
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

    @Test
    public void shouldThrowExceptionForNegativeTime() {
        assertThrows(IllegalArgumentException.class, () -> new Task("Task", new Date(-1)));
    }
}
