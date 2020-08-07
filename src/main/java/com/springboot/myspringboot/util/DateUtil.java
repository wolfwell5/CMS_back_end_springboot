package com.springboot.myspringboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    static String format = "yyyy-MM-dd hh:mm:ss";
    static SimpleDateFormat sdf = new SimpleDateFormat(format);

    public static Date formatDate(Date date) {
        Date parse = null;

        try {
            String formatDate = sdf.format(date);
            parse = sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parse;
    }

}
