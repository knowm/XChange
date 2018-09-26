package org.knowm.xchange.getbtc.utils;


public class RestCommonUtil {

  private RestCommonUtil() {}

  /**
   *
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    return str == null || "".equals(str) || "".equals(str.trim());
  }

  /**
   * 
   * @param str
   * @return
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
}
