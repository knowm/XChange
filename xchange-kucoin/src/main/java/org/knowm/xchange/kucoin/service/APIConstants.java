/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.service;

/** Based on code by zicong.lu on 2018/12/14. */
public class APIConstants {
  public static final String API_BASE_HOST = "api.kucoin.com";
  public static final String API_SANDBOX_HOST = "openapi-sandbox.kucoin.com";

  public static final String USER_API_KEY = "KC-API-KEY";
  public static final String USER_API_SECRET = "KC-API-SECRET";
  public static final String USER_API_PASSPHRASE = "KC-API-PASSPHRASE";

  public static final String API_HEADER_KEY = "KC-API-KEY";
  public static final String API_HEADER_SIGN = "KC-API-SIGN";
  public static final String API_HEADER_PASSPHRASE = "KC-API-PASSPHRASE";
  public static final String API_HEADER_TIMESTAMP = "KC-API-TIMESTAMP";

  public static final String API_TICKER_TOPIC_PREFIX = "/market/ticker:";
  public static final String API_LEVEL2_TOPIC_PREFIX = "/market/level2:";
  public static final String API_MATCH_TOPIC_PREFIX = "/market/match:";
  public static final String API_LEVEL3_TOPIC_PREFIX = "/market/level3:";
  public static final String API_ACTIVATE_TOPIC_PREFIX = "/market/level3:";
  public static final String API_BALANCE_TOPIC_PREFIX = "/account/balance";
}
