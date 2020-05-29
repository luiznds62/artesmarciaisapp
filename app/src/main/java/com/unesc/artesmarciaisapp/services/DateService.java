package com.unesc.artesmarciaisapp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {
    public static String dateToStringFormated(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static Date stringToDate(String dateString) throws ParseException {
        Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        return dt;
    }
}
