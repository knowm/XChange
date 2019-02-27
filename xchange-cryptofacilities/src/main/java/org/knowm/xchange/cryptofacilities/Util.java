package org.knowm.xchange.cryptofacilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  public static Date parseDate(String str) throws ParseException {
    return str == null ? null : DATE_FORMAT.parse(str);
  }
}
