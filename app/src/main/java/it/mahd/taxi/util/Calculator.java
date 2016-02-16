package it.mahd.taxi.util;

import java.util.Calendar;

/**
 * Created by salem on 2/16/16.
 */
public class Calculator {
    public int[] getAge(String date) {
        String[] strTemp = date.split("-");
        int birthdayYear = Integer.parseInt(strTemp[0].toString());
        int birthdayMonth = Integer.parseInt(strTemp[1].toString());
        int birthdayDay = Integer.parseInt(strTemp[2].toString());
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int year, month, day;
        year = currentYear - birthdayYear;
        month = currentMonth - birthdayMonth;
        day = currentDay - birthdayDay;
        if (month < 0) {
            year--;
            month = 12 - birthdayMonth + currentMonth;
            if (day < 0) month--;
        } else if (month == 0 && day < 0) {
            year--;
            month = 11;
        }
        if (day < 0) {
            today.add(Calendar.MONTH, -1);
            day = today.getActualMaximum(Calendar.DAY_OF_MONTH) - birthdayDay + currentDay;
        } else {
            day = 0;
            if (month == 12) {
                year++;
                month = 0;
            }
        }

        int[] tab = new int[3];
        tab[0] = year;
        tab[1] = month;
        tab[2] = day;
        return tab;
    }
}
