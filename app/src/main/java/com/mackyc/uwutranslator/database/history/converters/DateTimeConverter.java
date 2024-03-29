package com.mackyc.uwutranslator.database.history.converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTimeConverter {

    @TypeConverter
    public Date fromTimestamp (Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp (Date date) {
        return date == null ? null : date.getTime();
    }

}
