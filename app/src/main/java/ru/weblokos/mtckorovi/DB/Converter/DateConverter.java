package ru.weblokos.mtckorovi.DB.Converter;

import android.arch.persistence.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gravitas on 14.07.2018.
 */

public class DateConverter {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }
}