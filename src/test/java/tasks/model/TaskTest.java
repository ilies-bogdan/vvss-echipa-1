package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @BeforeEach
    public void setUp() {
        try {
            task = new Task("new task", Task.getDateFormat().parse("2023-02-12 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTaskCreation() throws ParseException {
        assert task.getTitle() == "new task";
        System.out.println(task.getFormattedDateStart());
        System.out.println(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
        assert task.getFormattedDateStart().equals(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testNextTimeAfterF02_TC01() throws ParseException {
        task = new Task("Task F02_TC01", SIMPLE_DATE_FORMAT.parse("11.04.2024"), SIMPLE_DATE_FORMAT.parse("15.04.2024"), 1);
        task.setActive(true);
        Date current = SIMPLE_DATE_FORMAT.parse("18.04.2024");
        assertNull(task.nextTimeAfter(current));
    }

    @Test
    public void testNextTimeAfterF01_TC02() throws ParseException {
        task = new Task("Task F01_TC02", SIMPLE_DATE_FORMAT.parse("11.04.2024"), SIMPLE_DATE_FORMAT.parse("15.04.2024"), 1);
        task.setTime(SIMPLE_DATE_FORMAT.parse("16.04.2024"));
        task.setActive(true);
        Date current = SIMPLE_DATE_FORMAT.parse("12.04.2024");
        Date expected = SIMPLE_DATE_FORMAT.parse("16.04.2024");
        assertEquals(expected, task.nextTimeAfter(current));
    }

    @Test
    public void testNextTimeAfterF01_TC03() throws ParseException {
        task = new Task("Task F01_TC03", SIMPLE_DATE_FORMAT.parse("11.04.2024"), SIMPLE_DATE_FORMAT.parse("15.04.2024"), 1);
        task.setActive(true);
        task.setTime(SIMPLE_DATE_FORMAT.parse("16.04.2024"));
        Date current = SIMPLE_DATE_FORMAT.parse("16.04.2024");
        assertNull(task.nextTimeAfter(current));
    }

    @Test
    public void testNextTimeAfterF01_TC04() throws ParseException {
        task = new Task("Task F01_TC04", SIMPLE_DATE_FORMAT.parse("11.04.2024"), SIMPLE_DATE_FORMAT.parse("15.04.2024"), 1);
        task.setActive(true);
        Date current = SIMPLE_DATE_FORMAT.parse("10.04.2024");
        Date expected = SIMPLE_DATE_FORMAT.parse("11.04.2024");
        assertEquals(expected, task.nextTimeAfter(current));
    }

    @Test
    public void testNextTimeAfterF01_TC05() throws ParseException {
        task = new Task("Task F01_TC05", SIMPLE_DATE_FORMAT.parse("11.04.2024"), SIMPLE_DATE_FORMAT.parse("15.04.2024"), 1);
        task.setActive(true);
        Date current = SIMPLE_DATE_FORMAT.parse("12.04.2024");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.SECOND, 1);
        Date expected = calendar.getTime();
        assertEquals(expected, task.nextTimeAfter(current));
    }

    @Test
    public void testNextTimeAfterF02_TC06() throws ParseException {
        Date start = SIMPLE_DATE_FORMAT.parse("10.04.2024");
        Date end = SIMPLE_DATE_FORMAT.parse("18.04.2024");
        Date current = SIMPLE_DATE_FORMAT.parse("15.04.2024");
        task = new Task("Test Task", start, end, 86400);
        current.setTime(current.getTime() + (task.getRepeatInterval() / 2) * 1000);

        Date nextTime = task.nextTimeAfter(current);

        assertNull(nextTime);
    }


    @Test
    public void testNextTimeAfterF02TC07() throws ParseException {
        Date start = SIMPLE_DATE_FORMAT.parse("10.04.2024");
        Date end = SIMPLE_DATE_FORMAT.parse("18.04.2024");

        Date current = SIMPLE_DATE_FORMAT.parse("15.04.2024");
        task = new Task("Test Task", start, end, 86400);
        task.setActive(true);


        Date nextTime = task.nextTimeAfter(current);

        Date expectedNextTime = SIMPLE_DATE_FORMAT.parse("16.04.2024");

        assertEquals(expectedNextTime, nextTime);
    }


}
