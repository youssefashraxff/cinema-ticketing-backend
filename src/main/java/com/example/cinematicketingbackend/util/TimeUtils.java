package com.example.cinematicketingbackend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for time-related operations.
 */
public class TimeUtils {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    /**
     * Parse a string time to LocalDateTime.
     *
     * @param time Time string in format "yyyy-MM-dd HH:mm"
     * @return LocalDateTime object
     * @throws DateTimeParseException if the format is invalid
     */
    public static LocalDateTime parseDateTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            throw new DateTimeParseException("Time string cannot be null or empty", time, 0);
        }
        return LocalDateTime.parse(time, FORMATTER);
    }

    /**
     * Check if time1 is after time2.
     *
     * @param time1 First time string
     * @param time2 Second time string
     * @return true if time1 is after time2
     */
    public static boolean isTimeAfter(String time1, String time2) {
        LocalDateTime dt1 = parseDateTime(time1);
        LocalDateTime dt2 = parseDateTime(time2);
        return dt1.isAfter(dt2);
    }

    /**
     * Calculate duration in minutes between start and finish times.
     *
     * @param start  Start time string
     * @param finish Finish time string
     * @return Duration in minutes
     */
    public static long calculateDurationInMinutes(String start, String finish) {
        LocalDateTime startTime = parseDateTime(start);
        LocalDateTime finishTime = parseDateTime(finish);
        return java.time.Duration.between(startTime, finishTime).toMinutes();
    }

    /**
     * Check if two time ranges overlap.
     *
     * @param start1  Start time of first range
     * @param finish1 Finish time of first range
     * @param start2  Start time of second range
     * @param finish2 Finish time of second range
     * @return true if the ranges overlap
     */
    public static boolean isOverlapping(String start1, String finish1, String start2, String finish2) {
        LocalDateTime s1 = parseDateTime(start1);
        LocalDateTime f1 = parseDateTime(finish1);
        LocalDateTime s2 = parseDateTime(start2);
        LocalDateTime f2 = parseDateTime(finish2);

        // Two ranges overlap if: s1 < f2 && s2 < f1
        return s1.isBefore(f2) && s2.isBefore(f1);
    }

    /**
     * Validate if the time string matches the required format "yyyy-MM-dd HH:mm".
     *
     * @param time Time string to validate
     * @return true if format is valid
     */
    public static boolean validateTimeFormat(String time) {
        if (time == null || time.trim().isEmpty()) {
            return false;
        }
        try {
            parseDateTime(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

