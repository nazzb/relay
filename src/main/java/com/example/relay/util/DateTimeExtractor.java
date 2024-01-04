package com.example.relay.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class DateTimeExtractor {
    public static List<LocalDateTime> getDateTimeList(List<String> dateTimeEntries){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        List<LocalDateTime> dateTimeList = new ArrayList<>();

        for(String entry: dateTimeEntries){
            // Parse the string to a LocalDateTime object
            LocalDateTime localDateTime = LocalDateTime.parse(entry, formatter);
            dateTimeList.add(localDateTime);
        }
        return dateTimeList;
    }
    public static double calculateTotalHoursWorked(List<LocalDateTime> dateTimeList){
        //Calculate the total duration of work, in seconds
        double totalSecondsWorked = 0;
        for(LocalDateTime entry: dateTimeList){
            totalSecondsWorked += entry.get(ChronoField.SECOND_OF_DAY);
        }

        //Convert to hours and return the value
        double hoursWorked = totalSecondsWorked / 3600.0;
        int remainingMinutes = (int) ((totalSecondsWorked % 3600) / 60);
        return hoursWorked + (remainingMinutes / 60.0);
    }
}
