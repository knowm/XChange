package org.knowm.xchange.cryptofacilities;

import java.util.Date;
import org.knowm.xchange.utils.DateUtils;

public class Util {

  public static Date parseDate(String str) {
    try {
      return str == null ? null : DateUtils.fromISODateString(str);
    } catch (Exception e) {
      throw new RuntimeException("Could not parse date using '" + str + "'.", e);
    }
  }
}
