package org.xchange.coinegg;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.knowm.xchange.currency.CurrencyPair;

public class CoinEggUtils {
  
  private static MessageDigest md5Digest;
  
  static {
    try {
      md5Digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  public static String toBaseCoin(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase();
  }
  
  public static String toHexString(byte[] data) {
    return DatatypeConverter.printHexBinary(data).toLowerCase();
  }
  
  public static String toMD5Hash(String data) {
    return toHexString(md5Digest.digest(data.getBytes())).toLowerCase();
  }

}
