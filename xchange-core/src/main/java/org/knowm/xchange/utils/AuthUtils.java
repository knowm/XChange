package org.knowm.xchange.utils;

public class AuthUtils {

  /**
   * Generates a BASE64 Basic Authentication String
   *
   * @param user
   * @param pass
   * @return BASE64 Basic Authentication String
   */
  public static String getBasicAuth(String user, final String pass) {

    return "Basic " + net.iharder.Base64.encodeBytes((user + ":" + pass).getBytes());
  }
}
