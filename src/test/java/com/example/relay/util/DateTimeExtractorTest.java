package com.example.relay.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class DateTimeExtractorTest {

    @Test
    void testGetDateTimeList() {
        List<String> dateTimeEntries = Arrays.asList(
                "2022-01-01T12:00:00Z",
                "2022-01-01T14:30:00Z",
                "2022-01-01T18:45:00Z"
        );

        List<LocalDateTime> dateTimeList = DateTimeExtractor.getDateTimeList(dateTimeEntries);

        assertEquals(3, dateTimeList.size());
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0, 0), dateTimeList.get(0));
        assertEquals(LocalDateTime.of(2022, 1, 1, 14, 30, 0), dateTimeList.get(1));
        assertEquals(LocalDateTime.of(2022, 1, 1, 18, 45, 0), dateTimeList.get(2));
    }

    @Test
    void testCalculateTotalHoursWorked() {
        List<LocalDateTime> dateTimeList = Arrays.asList(
                LocalDateTime.of(2022, 1, 1, 8, 0, 0),
                LocalDateTime.of(2022, 1, 1, 12, 30, 0),
                LocalDateTime.of(2022, 1, 1, 17, 15, 0)
        );

        double totalHoursWorked = DateTimeExtractor.calculateTotalHoursWorked(dateTimeList);

        assertEquals(38.5, totalHoursWorked);
    }
}