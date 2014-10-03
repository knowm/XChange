package com.xeiam.xchange.okcoin;

public class OkCoinUtils {

  public static String getErrorMessage(int errorCode) {

    switch (errorCode) {

    case (10000):
      return "Required field can not be null";
    case (10001):
      return "User request too frequent";
    case (10002):
      return "System error";
    case (10003):
      return "Try again later";
    case (10004):
      return "Not allowed to get resource due to IP constraint";
    case (10005):
      return "secretKey does not exist";
    case (10006):
      return "Partner (API key) does not exist";
    case (10007):
      return "Signature does not match";
    case (10008):
      return "Illegal parameter";
    case (10009):
      return "Order does not exist";
    case (10010):
      return "Insufficient funds";
    case (10011):
      return "Order quantity is less than minimum quantity allowed";
    case (10012):
      return "Invalid currency pair";
    case (10013):
      return "Only support https request";
    case (10014):
      return "Order price can not be ≤ 0 or ≥ 1,000,000";
    case (10015):
      return "Order price differs from current market price too much";
    case (10216):
      return "Non-public API";
    default:
      return "Unknown error";
    }
  }
}
