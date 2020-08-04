package nl.wolfpackit.backend_assessment.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtilities {

    public static String formatISO8601Date(Date date) {
        String sDate = "";
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            LocalDate dateOnly = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();

            sDate = formatter.format(dateOnly);
        }
        return sDate;
    }

    public static Date parseISO8601Date(String date) {
        if (date == null) {
            return null;
        } else {
            LocalDate parse = LocalDate.parse(date);
            return Date.from(parse.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }

    public static Date createDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
