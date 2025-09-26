package org.knowm.xchange.bitso.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

/**
 * Test for BitsoNonceV2Utils
 *
 * @author Piotr Ładyżyński
 */
public class BitsoNonceV2UtilsTest {

  @Test
  public void testGenerateNonceV2DefaultSalt() {
    long nonce = BitsoNonceV2Utils.generateNonceV2();

    // Should be 19 digits (13-digit timestamp + 6-digit salt)
    String nonceStr = String.valueOf(nonce);
    assertThat(nonceStr).hasSize(19);

    // Should be valid Nonce v2 format
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce)).isTrue();
  }

  @Test
  public void testGenerateNonceV2WithSaltDigits() {
    // Test with 1-digit salt
    long nonce1 = BitsoNonceV2Utils.generateNonceV2(1);
    String nonce1Str = String.valueOf(nonce1);
    assertThat(nonce1Str).hasSize(14); // 13 + 1
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce1)).isTrue();

    // Test with 6-digit salt
    long nonce6 = BitsoNonceV2Utils.generateNonceV2(6);
    String nonce6Str = String.valueOf(nonce6);
    assertThat(nonce6Str).hasSize(19); // 13 + 6
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce6)).isTrue();
  }

  @Test
  public void testGenerateNonceV2InvalidSaltDigits() {
    assertThatThrownBy(() -> BitsoNonceV2Utils.generateNonceV2(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Salt digits must be between 1 and 6");

    assertThatThrownBy(() -> BitsoNonceV2Utils.generateNonceV2(7))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Salt digits must be between 1 and 6");
  }

  @Test
  public void testGenerateNonceV2WithTimestamp() {
    long timestamp = 1731349200123L; // Fixed timestamp for testing

    long nonce = BitsoNonceV2Utils.generateNonceV2(timestamp);
    String nonceStr = String.valueOf(nonce);

    // Should be 19 digits (13-digit timestamp + 6-digit salt)
    assertThat(nonceStr).hasSize(19);

    // Should start with the provided timestamp
    assertThat(nonceStr).startsWith("1731349200123");

    // Should be valid Nonce v2 format
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce)).isTrue();
  }

  @Test
  public void testGenerateNonceV2WithTimestampAndSaltDigits() {
    long timestamp = 1731349200123L;

    // Test with 3-digit salt
    long nonce = BitsoNonceV2Utils.generateNonceV2(timestamp, 3);
    String nonceStr = String.valueOf(nonce);

    assertThat(nonceStr).hasSize(16); // 13 + 3
    assertThat(nonceStr).startsWith("1731349200123");
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce)).isTrue();
  }

  @Test
  public void testIsValidNonceV2() {
    // Valid Nonce v2 formats (14-19 digits)
    assertThat(BitsoNonceV2Utils.isValidNonceV2(17313492001239L)).isTrue(); // 14 digits
    assertThat(BitsoNonceV2Utils.isValidNonceV2(173134920012399L)).isTrue(); // 15 digits
    assertThat(BitsoNonceV2Utils.isValidNonceV2(1731349200123456L)).isTrue(); // 16 digits
    assertThat(BitsoNonceV2Utils.isValidNonceV2(17313492001234567L)).isTrue(); // 17 digits
    assertThat(BitsoNonceV2Utils.isValidNonceV2(173134920012345678L)).isTrue(); // 18 digits
    assertThat(BitsoNonceV2Utils.isValidNonceV2(1731349200123456789L)).isTrue(); // 19 digits

    // Invalid Nonce v2 formats (too short)
    assertThat(BitsoNonceV2Utils.isValidNonceV2(1731349200123L)).isFalse(); // 13 digits (too short)
    assertThat(BitsoNonceV2Utils.isValidNonceV2(123456789012L)).isFalse(); // 12 digits (too short)

    // Note: Testing 20+ digit numbers is not possible with long literals due to Java limitations,
    // but since the method validates length, it would return false for numbers > 19 digits
  }

  @Test
  public void testExtractTimestamp() {
    long timestamp = 1731349200123L;
    long nonce = BitsoNonceV2Utils.generateNonceV2(timestamp);

    long extractedTimestamp = BitsoNonceV2Utils.extractTimestamp(nonce);
    assertThat(extractedTimestamp).isEqualTo(timestamp);
  }

  @Test
  public void testExtractTimestampInvalidNonce() {
    assertThatThrownBy(
            () -> BitsoNonceV2Utils.extractTimestamp(1234567890123L)) // 13 digits (too short)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid Nonce v2 format");
  }

  @Test
  public void testExtractSalt() {
    long timestamp = 1731349200123L;

    // Test with known salt digits
    long nonce = BitsoNonceV2Utils.generateNonceV2(timestamp, 3);
    int salt = BitsoNonceV2Utils.extractSalt(nonce);

    // Salt should be a 3-digit number (100-999)
    assertThat(salt).isBetween(100, 999);

    // Verify reconstruction
    String expectedNonce = timestamp + String.valueOf(salt);
    assertThat(String.valueOf(nonce)).isEqualTo(expectedNonce);
  }

  @Test
  public void testExtractSaltMinimalNonce() {
    // Test with 14-digit nonce (13-digit timestamp + 1-digit salt)
    long nonce = 17313492001239L; // timestamp: 1731349200123, salt: 9

    long extractedTimestamp = BitsoNonceV2Utils.extractTimestamp(nonce);
    int extractedSalt = BitsoNonceV2Utils.extractSalt(nonce);

    assertThat(extractedTimestamp).isEqualTo(1731349200123L);
    assertThat(extractedSalt).isEqualTo(9);
  }

  @Test
  public void testExtractSaltInvalidNonce() {
    assertThatThrownBy(() -> BitsoNonceV2Utils.extractSalt(1234567890123L)) // 13 digits (too short)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid Nonce v2 format");
  }

  @Test
  public void testNonceV2Examples() {
    // Test examples from Bitso documentation
    // Example: 1731349200123123456 (timestamp 1731349200123 + salt 123456)
    long nonce19 = 1731349200123123456L;
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce19)).isTrue();
    assertThat(BitsoNonceV2Utils.extractTimestamp(nonce19)).isEqualTo(1731349200123L);
    assertThat(BitsoNonceV2Utils.extractSalt(nonce19)).isEqualTo(123456);

    // Example: 173134920012378901 (timestamp 1731349200123 + salt 78901)
    long nonce18 = 173134920012378901L;
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce18)).isTrue();
    assertThat(BitsoNonceV2Utils.extractTimestamp(nonce18)).isEqualTo(1731349200123L);
    assertThat(BitsoNonceV2Utils.extractSalt(nonce18)).isEqualTo(78901);

    // Example: 1731349200123999 (timestamp 1731349200123 + salt 999)
    long nonce16 = 1731349200123999L;
    assertThat(BitsoNonceV2Utils.isValidNonceV2(nonce16)).isTrue();
    assertThat(BitsoNonceV2Utils.extractTimestamp(nonce16)).isEqualTo(1731349200123L);
    assertThat(BitsoNonceV2Utils.extractSalt(nonce16)).isEqualTo(999);
  }

  @Test
  public void testGeneratedNoncesAreUnique() {
    // Generate multiple nonces quickly and ensure they're different
    long nonce1 = BitsoNonceV2Utils.generateNonceV2();
    long nonce2 = BitsoNonceV2Utils.generateNonceV2();
    long nonce3 = BitsoNonceV2Utils.generateNonceV2();

    // Since they include random salt, they should be different
    assertThat(nonce1).isNotEqualTo(nonce2);
    assertThat(nonce2).isNotEqualTo(nonce3);
    assertThat(nonce1).isNotEqualTo(nonce3);
  }
}
