package ru.chemarev.andrey.convertor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateConverter implements CustomConverter<LocalDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");

    @Override
    public Object convert(LocalDateTime obj) {
        return formatter.format(obj);
    }
}
