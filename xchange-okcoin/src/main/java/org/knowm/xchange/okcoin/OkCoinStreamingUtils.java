package org.knowm.xchange.okcoin;

public class OkCoinStreamingUtils {

  public static String getErrorMessage(int errorCode) {

    switch (errorCode) {
    case (10001):
      return "Illegal parameters";
    case (10002):
      return "Authentication failure";
    case (10003):
      return "This connection has requested other user data";
    case (10004):
      return "This connection did not request this user data";
    case (10005):
      return "System error";
    case (10009):
      return "Order does not exist";
    case (10010):
      return "Insufficient funds";
    case (10011):
      return "Order quantity too low";
    case (10012):
      return "Only support btc_usd ltc_usd";
    case (10014):
      return "Order price must be between 0 - 1 000 000";
    case (10015):
      return "Channel subscription temporally not available";
    case (10016):
      return "Insufficient coins";
    case (10017):
      return "WebSocket authorization error";
    case (10100):
      return "user frozen";
    case (10216):
      return "non-public API";
    case (20001):
      return "user does not exist";
    case (20002):
      return "user frozen";
    case (20003):
      return "frozen due to force liquidation";
    case (20004):
      return "future account frozen";
    case (20005):
      return "user future account does not exist";
    case (20006):
      return "required field can not be null";
    case (20007):
      return "illegal parameter";
    case (20008):
      return "future account fund balance is zero";
    case (20009):
      return "future contract status error";
    case (20010):
      return "risk rate information does not exist";
    case (20011):
      return "risk rate bigger than 90% before opening position";
    case (20012):
      return "risk rate bigger than 90% after opening position";
    case (20013):
      return "temporally no counter party price";
    case (20014):
      return "system error";
    case (20015):
      return "order does not exist";
    case (20016):
      return "liquidation quantity bigger than holding";
    case (20017):
      return "not authorized/illegal order ID";
    case (20018):
      return "order price higher than 105% or lower than 95% of the price of last minute";
    case (20019):
      return "IP restrained to access the resource";
    case (20020):
      return "secret key does not exist";
    case (20021):
      return "index information does not exist";
    case (20022):
      return "wrong API interface";
    case (20023):
      return "fixed margin user";
    case (20024):
      return "signature does not match";
    case (20025):
      return "leverage rate error";

    default:
      return "Unknown error";
    }
  }
}
