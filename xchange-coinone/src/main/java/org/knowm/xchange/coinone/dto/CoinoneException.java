package org.knowm.xchange.coinone.dto;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class CoinoneException extends RuntimeException {

  private String message;

  private String error;

  public static Map<String, String> resMsgMap = new HashMap<String, String>();

  static {
    resMsgMap.put("4", "Blocked user access");
    resMsgMap.put("11", "Access token is missing");
    resMsgMap.put("12", "Invalid access token");
    resMsgMap.put("40", "Invalid API permission");
    resMsgMap.put("50", "Authenticate error");
    resMsgMap.put("51", "Invalid API");
    resMsgMap.put("52", "Deprecated API");
    resMsgMap.put("53", "Two Factor Auth Fail");
    resMsgMap.put("100", "Session expired");
    resMsgMap.put("101", "Invalid format");
    resMsgMap.put("102", "ID is not exist");
    resMsgMap.put("103", "Lack of Balance");
    resMsgMap.put("104", "Order id is not exist");
    resMsgMap.put("105", "Price is not correct");
    resMsgMap.put("106", "Locking error");
    resMsgMap.put("107", "Parameter error");
    resMsgMap.put("111", "Order id is not exist");
    resMsgMap.put("112", "Cancel failed");
    resMsgMap.put("113", "Quantity is too low(ETH, ETC > 0.01)");
    resMsgMap.put("120", "V2 API payload is missing");
    resMsgMap.put("121", "V2 API signature is missing");
    resMsgMap.put("122", "V2 API nonce is missing");
    resMsgMap.put("123", "V2 API signature is not correct");
    resMsgMap.put("130", "V2 API Nonce value must be a positive integer");
    resMsgMap.put("131", "V2 API Nonce is must be bigger then last nonce");
    resMsgMap.put("132", "V2 API body is corrupted");
    resMsgMap.put("141", "Too many limit orders");
    resMsgMap.put("150", "It's V1 API. V2 Access token is not acceptable");
    resMsgMap.put("151", "It's V2 API. V1 Access token is not acceptable");
    resMsgMap.put("200", "Wallet Error");
    resMsgMap.put("202", "Limitation error");
    resMsgMap.put("210", "Limitation error");
    resMsgMap.put("220", "Limitation error");
    resMsgMap.put("221", "Limitation error");
    resMsgMap.put("310", "Mobile auth error");
    resMsgMap.put("311", "Need mobile auth");
    resMsgMap.put("312", "Name is not correct");
    resMsgMap.put("330", "Phone number error");
    resMsgMap.put("404", "Page not found error");
    resMsgMap.put("405", "Server error");
    resMsgMap.put("444", "Locking error");
    resMsgMap.put("500", "Email error");
    resMsgMap.put("501", "Email error");
    resMsgMap.put("777", "Mobile auth error");
    resMsgMap.put("778", "Phone number error");
    resMsgMap.put("1202", "App not found");
    resMsgMap.put("1203", "Already registered");
    resMsgMap.put("1204", "Invalid access");
    resMsgMap.put("1205", "API Key error");
    resMsgMap.put("1206", "User not found");
    resMsgMap.put("1207", "User not found");
    resMsgMap.put("1208", "User not found");
    resMsgMap.put("1209", "User not found");
  }

  public CoinoneException(String message) {

    super();
    this.message = message;
  }

  public String getMessage() {
    return message == null ? error : message;
  }
}
