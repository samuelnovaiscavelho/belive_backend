package br.com.fiap.belive_backend.utils;

import java.util.Calendar;

public class DateUtils {

    private static boolean isLeap(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    public static boolean isValidDate(int day, int month) {
        int monthAX_VALID_YR = 9999;
        int monthIN_VALID_YR = 1800;

        int year = Calendar.getInstance().get(Calendar.YEAR);

        if (year > monthAX_VALID_YR || year < monthIN_VALID_YR)
            return false;
        if (month < 1 || month > 12)
            return false;
        if (day < 1 || day > 31)
            return false;

        if (month == 2) {
            if (isLeap(year))
                return (day <= 29);
            else
                return (day <= 28);
        }

        if (month == 4 || month == 6 || month == 9 || month == 11)
            return (day <= 30);

        return true;
    }
}
