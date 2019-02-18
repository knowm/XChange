package org.knowm.xchange.cryptofacilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

  public static Date parseDate(String str) throws ParseException {
    try {
      // need to create a new instance of SFD, since its not thread safe
      return str == null ? null : new SimpleDateFormat(DATE_FORMAT).parse(str);
    } catch (Exception e) {
      throw new RuntimeException("Could not parse date using '" + str + "'.", e);
    }
  }
}
