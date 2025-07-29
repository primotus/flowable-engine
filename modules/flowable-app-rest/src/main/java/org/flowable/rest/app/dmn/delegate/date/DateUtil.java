package org.flowable.rest.app.dmn.delegate.date;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.ISOPeriodFormat;
import java.util.Date;


public class DateUtil {

    static public Period toDuration(String value) {
        return ISOPeriodFormat.standard().parsePeriod(value);
    }

    static public Object toDateNow(String format) {
        if ( format.equals("date")){
            return LocalDate.now();
        } else if (format.equals("date-time")){
            return LocalDateTime.now();
        }

        return null;
    }

    static public LocalDate toDate(String value){
        try {
            LocalDate d = LocalDate.parse(value, ISODateTimeFormat.date());
            return d;
        } catch (Exception e) {
            System.out.println("Error al parsear la fecha y hora: " + e.getMessage());
            return null;
        }
    }

    static public LocalDateTime toDateTime(String value){
        try {
            return LocalDateTime.parse(value, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (Exception e) {
            System.out.println("Error al parsear la fecha y hora: " + e.getMessage());
            return null;
        }
    }

    static public boolean isAfter(Date date1, Date date2) {
        return date1.after(date2);
    }
    static public boolean isBefore(Date date1, Date date2) {
        return date1.before(date2);
    }
}
