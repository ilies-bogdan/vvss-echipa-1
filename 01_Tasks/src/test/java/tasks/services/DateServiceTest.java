package tasks.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest {

    private DateService dateService;

    Calendar calendar;

    Date currentDate;


    @BeforeEach
    void setUp() {
        dateService = new DateService();
        calendar = Calendar.getInstance();
        currentDate = new Date();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDateMergedWithTime_BVA01() {
        String time = "23:14";
        calendar.add(Calendar.DATE, 7);
        Date date = calendar.getTime();

        Date actual = dateService.getDateMergedWithTime(time, date);

        assert actual.after(currentDate);
        assert Math.abs(date.getTime() - actual.getTime()) < 24 * 60 * 60 * 1000;
    }

    @Test
    void getDateMergedWithTime_BVA02() {
        String time = "12:60";
        calendar.add(Calendar.DATE, 7);
        Date date = calendar.getTime();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime(time, date));

        assert "time unit exceeds bounds".equals(ex.getMessage());
    }

    @Test
    void getDateMergedWithTime_BVA03() {
        String time = "21:53";
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        Date actual = dateService.getDateMergedWithTime(time, date);

        assert actual.after(currentDate);
        assert Math.abs(date.getTime() - actual.getTime()) < 24 * 60 * 60 * 1000;
    }

    @Test
    void getDateMergedWithTime_BVA04() {
        String time = "21:53";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime(time, currentDate));

        assert "invalid date".equals(ex.getMessage());
    }

}