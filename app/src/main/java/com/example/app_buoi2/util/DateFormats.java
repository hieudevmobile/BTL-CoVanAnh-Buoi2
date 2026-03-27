package com.example.app_buoi2.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateFormats {

    private static final SimpleDateFormat SHOW =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));

    private DateFormats() {}

    public static String formatShow(long millis) {
        return SHOW.format(new Date(millis));
    }
}
