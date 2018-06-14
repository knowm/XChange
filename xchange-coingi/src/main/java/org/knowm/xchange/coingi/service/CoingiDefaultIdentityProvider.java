package org.knowm.xchange.coingi.service;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CoingiDefaultIdentityProvider {
  private static final String ALGO = "HmacSHA256";

  private static final Charset CHARSET = Charset.forName("US-ASCII");

  private final byte[] publicKey;

  private final Mac sha256HMAC;

  /**
   * Initializes the provider using the given public and private API key.
   *
   * @param publicKey Public API key part
   * @param privateKey Private API key part
   */
  public CoingiDefaultIdentityProvider(byte[] publicKey, byte[] privateKey) {
    Objects.requireNonNull(publicKey, "No public key given.");
    if (publicKey.length == 0) {
      throw new IllegalArgumentException("Empty public key given.");
    }

    this.publicKey = publicKey;

    try {
      Objects.requireNonNull(privateKey, "No private key given.");
      if (privateKey.length == 0) {
        throw new IllegalArgumentException("Empty private key given.");
      }

      this.sha256HMAC = Mac.getInstance(ALGO);
      this.sha256HMAC.init(new SecretKeySpec(privateKey, ALGO));
    } catch (NoSuchAlgorithmException ex) {
      throw new RuntimeException(ex);
    } catch (InvalidKeyException ex) {
      throw new IllegalArgumentException("Invalid private key.", ex);
    }
  }

  /**
   * Returns the public key part.
   *
   * @return Public key
   */
  public byte[] getPublicKey() {
    return publicKey;
  }

  /**
   * Returns a valid signature for the given nonce.
   *
   * @param nonce Request nonce
   * @return Request signature
   */
  public synchronized String getSignature(long nonce) {
    return HexEncoder.encode(sha256HMAC.doFinal(buildSignatureData(nonce)))
        .toLowerCase(Locale.ROOT);
  }

  byte[] buildSignatureData(long nonce) {
    final byte[] nonceData = String.valueOf(nonce).getBytes(CHARSET);
    final byte[] data = new byte[nonceData.length + 1 + publicKey.length];

    System.arraycopy(nonceData, 0, data, 0, nonceData.length);
    data[nonceData.length] = '$';
    System.arraycopy(publicKey, 0, data, nonceData.length + 1, publicKey.length);

    return data;
  }

  /** Binary to hexa convertor. */
  public static class HexEncoder {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Converts given binary data to a hexadecimal string representation.
     *
     * @param data Binary data
     * @return Hexadecimal representation
     */
    public static String encode(byte[] data) {
      final char[] hexChars = new char[data.length * 2];

      int position = 0;
      for (byte bt : data) {
        hexChars[position++] = hexArray[(bt & 0xF0) >>> 4];
        hexChars[position++] = hexArray[bt & 0x0F];
      }

      return String.valueOf(hexChars);
    }
  }
}
