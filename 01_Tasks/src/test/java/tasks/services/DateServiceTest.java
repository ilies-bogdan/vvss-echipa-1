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

class DateServiceTest {

    private DateService dateService;

    private Calendar calendar;

    private Date currentDate;

    @BeforeEach
    void setUp() {
        dateService = new DateService();
        calendar = Calendar.getInstance();
        currentDate = new Date();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class DateServiceTestBVA {

        @RepeatedTest(3)
        @DisplayName("BVA01")
        void getDateMergedWithTime_BVA01() {
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
        void getDateMergedWithTime_BVA02() {
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
        void getDateMergedWithTime_BVA03(String time) {
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();

            Date actual = dateService.getDateMergedWithTime(time, date);

            assert actual.after(currentDate);
            assert Math.abs(date.getTime() - actual.getTime()) < 24 * 60 * 60 * 1000;
        }

        @Test
        @DisplayName("BVA04")
        void getDateMergedWithTime_BVA04() {
            String time = "21:53";
            calendar.add(Calendar.DATE, -1);
            Date date = calendar.getTime();

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> dateService.getDateMergedWithTime(time, date));

            assert "invalid date".equals(ex.getMessage());
        }

    }

}
