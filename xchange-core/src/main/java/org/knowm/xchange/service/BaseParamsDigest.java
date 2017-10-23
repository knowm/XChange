package org.knowm.xchange.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.iharder.Base64;
import si.mazi.rescu.ParamsDigest;

public abstract class BaseParamsDigest implements ParamsDigest {

  public static final String HMAC_SHA_512 = "HmacSHA512";
  public static final String HMAC_SHA_384 = "HmacSHA384";
  public static final String HMAC_SHA_256 = "HmacSHA256";
  public static final String HMAC_SHA_1 = "HmacSHA1";

  private final ThreadLocal<Mac> threadLocalMac;

  /**
   * Constructor
   *
   * @param secretKeyBase64 Base64 secret key
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  protected BaseParamsDigest(String secretKeyBase64, final String hmacString) throws IllegalArgumentException {

    try {
      final SecretKey secretKey = new SecretKeySpec(secretKeyBase64.getBytes("UTF-8"), hmacString);
      threadLocalMac = new ThreadLocal<Mac>() {

        @Override
        protected Mac initialValue() {

          try {
            Mac mac = Mac.getInstance(hmacString);
            mac.init(secretKey);
            return mac;
          } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
          } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
          }
        }
      };
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
  }

  /**
   * Constructor
   *
   * @param secretKeyBase64 Base64 secret key
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  protected BaseParamsDigest(byte[] secretKeyBase64, final String hmacString) throws IllegalArgumentException {

    final SecretKey secretKey = new SecretKeySpec(secretKeyBase64, hmacString);
    threadLocalMac = new ThreadLocal<Mac>() {

      @Override
      protected Mac initialValue() {

        try {
          Mac mac = Mac.getInstance(hmacString);
          mac.init(secretKey);
          return mac;
        } catch (InvalidKeyException e) {
          throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
        }
      }
    };
  }

  protected Mac getMac() {

    return threadLocalMac.get();
  }

  protected static byte[] decodeBase64(String secretKey) {
    try {
      return Base64.decode(secretKey);
    } catch (IOException e) {
      throw new RuntimeException("Can't decode secret key as Base 64", e);
    }
  }
}
