package tasks.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateServiceTest {

    private DateService dateService;

    private Calendar calendar;

    private Date currentDate;

    @BeforeEach
    public void setUp() {
        dateService = new DateService();
        calendar = Calendar.getInstance();
        currentDate = new Date();
    }

    @AfterEach
    public void tearDown() {
    }

    @RepeatedTest(3)
    @DisplayName("BVA01")
    public void getDateMergedWithTime_BVA01() {
        String time = "23:14";
        calendar.add(Calendar.DATE, 7);
        Date date = calendar.getTime();

        Date actual = dateService.getDateMergedWithTime(time, date);

        assert actual.after(currentDate);
        assert Math.abs(date.getTime() - actual.getTime()) < 24 * 60 * 60 * 1000;
    }

    @Test
    @DisplayName("BVA02")
    @Timeout(2)
    public void getDateMergedWithTime_BVA02() {
        String time = "12:60";
        calendar.add(Calendar.DATE, 7);
        Date date = calendar.getTime();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime(time, date));

        assert "time unit exceeds bounds".equals(ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"21:53"})
    @DisplayName("BVA03")
    public void getDateMergedWithTime_BVA03(String time) {
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        Date actual = dateService.getDateMergedWithTime(time, date);

        assert actual.after(currentDate);
        assert Math.abs(date.getTime() - actual.getTime()) < 24 * 60 * 60 * 1000;
    }

    @Test
    @DisplayName("BVA04")
    public void getDateMergedWithTime_BVA04() {
        String time = "21:53";
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> dateService.getDateMergedWithTime(time, date));

        assert "invalid date".equals(ex.getMessage());
    }

    @Test
    @DisplayName("ECP01")
    public void testValidTimeFutureDate() {
        String time = "10:30";
        Date futureDate = new Date(2025, Calendar.FEBRUARY, 28);

        Date mergedDate = dateService.getDateMergedWithTime(time, futureDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(futureDate);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);

        assertEquals(calendar.getTime(), mergedDate);
    }

    @Test
    @DisplayName("ECP02")
    public void testInvalidTimeFormat() {
        String invalidTime = "invalid";
        Date anyDate = new Date();

        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(invalidTime, anyDate));
    }

    @Test
    @DisplayName("ECP03")
    public void testTimeUnitExceedingBounds() {
        String timeExceedingHour = "25:00";
        String timeExceedingMinute = "10:65";
        Date anyDate = new Date();

        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(timeExceedingHour, anyDate));
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(timeExceedingMinute, anyDate));
    }

    @Test
    @DisplayName("ECP04")
    public void testPastDate() {
        String time = "10:30";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date pastDate = calendar.getTime();

        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, pastDate));
    }
}
