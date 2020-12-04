package com.criollofood.bootapp.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class Utils {

    public static String formatDateToFull(LocalDateTime fecha) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(fecha);
    }
}
