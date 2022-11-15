package ru.alrosa.transport.gastransportstatistics.serializationdeserialization;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UtilClass {

    public static LocalDate toLocalDateTime(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static DateTimeFormatter getFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
}
