package tasks.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateService {
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_A_DAY = 24;

    public DateService(){
    }

    public static LocalDate getLocalDateValueFromDate(Date date){//for setting to DatePicker - requires LocalDate
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }
    public Date getDateValueFromLocalDate(LocalDate localDate){//for getting from DatePicker
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }
    public Date getDateMergedWithTime(String time, Date noTimeDate) {//to retrieve Date object from both DatePicker and time field
        String[] units = time.split(":");
        int hour = Integer.parseInt(units[0]);
        int minute = Integer.parseInt(units[1]);
        if (hour > HOURS_IN_A_DAY || minute > MINUTES_IN_HOUR) throw new IllegalArgumentException("time unit exceeds bounds");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(noTimeDate);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
        public String getTimeOfTheDayFromDate(Date date){//to set in detached time field
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);
    }

    public int parseFromStringToSeconds(String stringTime){//hh:MM
        String[] units = stringTime.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int result = (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
        return result;
    }

    public String getIntervalInHours(int seconds){
        int minutes = seconds / DateService.SECONDS_IN_MINUTE;
        int hours = minutes / DateService.MINUTES_IN_HOUR;
        minutes = minutes % DateService.MINUTES_IN_HOUR;
        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);//hh:MM
    }

    private String formTimeUnit(int timeUnit) {
        StringBuilder sb = new StringBuilder();
        if (timeUnit < 10) sb.append("0");
        if (timeUnit == 0) sb.append("0");
        else {
            sb.append(timeUnit);
        }
        return sb.toString();
    }

}
