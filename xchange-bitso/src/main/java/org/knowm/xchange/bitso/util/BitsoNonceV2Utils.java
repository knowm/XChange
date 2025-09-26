package org.knowm.xchange.bitso.util;

import java.security.SecureRandom;
import java.time.Instant;

/**
 * Utility class for generating Bitso API Nonce v2 compatible values.
 *
 * <p>Nonce v2 format: A number between 14 and 19 digits, formed by concatenating: - A 13-digit
 * Epoch timestamp in milliseconds - A random salt (1 to 6 digits)
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/nonce-v2-rollout">Nonce v2 Rollout</a>
 */
public class BitsoNonceV2Utils {

  /**
   * Generates a Nonce v2 compatible value using the current timestamp and a 6-digit random salt.
   *
   * @return A Nonce v2 value (19 digits: 13-digit timestamp + 6-digit salt)
   */
  public static long generateNonceV2() {
    return generateNonceV2(6);
  }

  /**
   * Generates a Nonce v2 compatible value using the current timestamp and a random salt of
   * specified digits.
   *
   * @param saltDigits Number of digits for the random salt (1-6)
   * @return A Nonce v2 value
   * @throws IllegalArgumentException if saltDigits is not between 1 and 6
   */
  public static long generateNonceV2(int saltDigits) {
    if (saltDigits < 1 || saltDigits > 6) {
      throw new IllegalArgumentException("Salt digits must be between 1 and 6");
    }

    // Get current timestamp in milliseconds (13 digits)
    long timestamp = Instant.now().toEpochMilli();

    // Generate random salt with specified number of digits
    int minSalt = (int) Math.pow(10, saltDigits - 1);
    int maxSalt = (int) Math.pow(10, saltDigits) - 1;
    int salt = new SecureRandom().nextInt(maxSalt - minSalt + 1) + minSalt;

    // Concatenate timestamp and salt
    return Long.parseLong(timestamp + String.valueOf(salt));
  }

  /**
   * Generates a Nonce v2 compatible value using a specific timestamp and a 6-digit random salt.
   * Useful for testing or when you need to control the timestamp.
   *
   * @param timestamp The timestamp in milliseconds
   * @return A Nonce v2 value
   */
  public static long generateNonceV2(long timestamp) {
    return generateNonceV2(timestamp, 6);
  }

  /**
   * Generates a Nonce v2 compatible value using a specific timestamp and salt digits.
   *
   * @param timestamp The timestamp in milliseconds
   * @param saltDigits Number of digits for the random salt (1-6)
   * @return A Nonce v2 value
   * @throws IllegalArgumentException if saltDigits is not between 1 and 6
   */
  public static long generateNonceV2(long timestamp, int saltDigits) {
    if (saltDigits < 1 || saltDigits > 6) {
      throw new IllegalArgumentException("Salt digits must be between 1 and 6");
    }

    // Generate random salt with specified number of digits
    int minSalt = (int) Math.pow(10, saltDigits - 1);
    int maxSalt = (int) Math.pow(10, saltDigits) - 1;
    int salt = new SecureRandom().nextInt(maxSalt - minSalt + 1) + minSalt;

    // Concatenate timestamp and salt
    return Long.parseLong(timestamp + String.valueOf(salt));
  }

  /**
   * Validates if a nonce value conforms to Nonce v2 format.
   *
   * @param nonce The nonce value to validate
   * @return true if the nonce is valid Nonce v2 format (14-19 digits)
   */
  public static boolean isValidNonceV2(long nonce) {
    String nonceStr = String.valueOf(nonce);
    int length = nonceStr.length();

    // Nonce v2 should be between 14 and 19 digits
    return length >= 14 && length <= 19;
  }

  /**
   * Extracts the timestamp portion from a Nonce v2 value.
   *
   * @param nonceV2 The Nonce v2 value
   * @return The timestamp in milliseconds
   * @throws IllegalArgumentException if the nonce is not valid Nonce v2 format
   */
  public static long extractTimestamp(long nonceV2) {
    if (!isValidNonceV2(nonceV2)) {
      throw new IllegalArgumentException("Invalid Nonce v2 format");
    }

    String nonceStr = String.valueOf(nonceV2);
    // First 13 digits are the timestamp
    return Long.parseLong(nonceStr.substring(0, 13));
  }

  /**
   * Extracts the salt portion from a Nonce v2 value.
   *
   * @param nonceV2 The Nonce v2 value
   * @return The salt value
   * @throws IllegalArgumentException if the nonce is not valid Nonce v2 format
   */
  public static int extractSalt(long nonceV2) {
    if (!isValidNonceV2(nonceV2)) {
      throw new IllegalArgumentException("Invalid Nonce v2 format");
    }

    String nonceStr = String.valueOf(nonceV2);
    // Remaining digits after the 13-digit timestamp are the salt
    if (nonceStr.length() > 13) {
      return Integer.parseInt(nonceStr.substring(13));
    }
    return 0;
  }
}
