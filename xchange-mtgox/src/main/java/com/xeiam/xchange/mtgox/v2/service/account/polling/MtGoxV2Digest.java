package com.xeiam.xchange.mtgox.v2.service.account.polling;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocationParams;

/**
 * @author Matija Mazi <br/>
 * @created 5/12/13 9:33 PM
 *
 * TODO! this doesn't work yet.
 */
public class MtGoxV2Digest implements ParamsDigest {

  private static final Logger log = LoggerFactory.getLogger(MtGoxV2Digest.class);

  private static final String HMAC_SHA_512 = "HmacSHA512";
  private final Mac mac;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private MtGoxV2Digest(String secretKeyBase64) throws IllegalArgumentException {

    try {
      SecretKey secretKey = new SecretKeySpec(secretKeyBase64.getBytes("UTF-8"), HMAC_SHA_512);
      mac = Mac.getInstance(HMAC_SHA_512);
      mac.init(secretKey);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
  }

  public static MtGoxV2Digest createInstance(String secretKeyBase64) throws IllegalArgumentException {

    return secretKeyBase64 == null ? null : new MtGoxV2Digest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocationParams restInvocationParams) {

    try {
      String postBody = restInvocationParams.getRequestBody();
      String methodPath = restInvocationParams.getMethodPath();
      log.debug("methodPath = {}", methodPath);
      mac.update(methodPath.getBytes("UTF-8"));
      mac.update((byte) 0);
      mac.update(postBody.getBytes("UTF-8"));
      return String.format("%0128x", new BigInteger(1, mac.doFinal()));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
    // return Base64.encodeBytes(mac.doFinal()).trim();
  }
}
