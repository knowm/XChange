package org.knowm.xchange.independentreserve.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {

    private static final String TIMEZONE = "UTC";
    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final SimpleDateFormat DATE_FORMAT;
    
    static {
        DATE_FORMAT = new SimpleDateFormat(PATTERN);
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
    }
    
    private Util() { }
    
    /**
     * Format a date String for IR
     *
     * @param date
     * @return formatted date for Independent Reserve
     */
    public static String formatDate(Date d) {
        synchronized (DATE_FORMAT) {       // SimpleDateFormat is not thread safe, therefore synchronize it
          return d == null ? null : DATE_FORMAT.format(d);
        }
    }
    
}
