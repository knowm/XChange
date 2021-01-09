package org.knowm.xchange.utils;

public class DigestUtils {

  public static String bytesToHex(byte[] bytes) {

    char[] hexArray = "0123456789abcdef".toCharArray();
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }

  public static byte[] hexToBytes(final String encoded) {
    if ((encoded.length() % 2) != 0)
      throw new IllegalArgumentException("Input string must contain an even number of characters");

    byte[] b = new byte[encoded.length() / 2];
    for (int i = 0; i < encoded.length(); i += 2) {
      b[i / 2] = (byte) ((Character.digit(encoded.charAt(i), 16) << 4)
              + Character.digit(encoded.charAt(i+1), 16));
    }
    return b;
  }
}
