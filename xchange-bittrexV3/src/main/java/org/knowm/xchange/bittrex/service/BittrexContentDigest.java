package org.knowm.xchange.bittrex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class BittrexContentDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BittrexContentDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BittrexContentDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BittrexContentDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-512");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }

    String content = restInvocation.getRequestBody();
    return DigestUtils.bytesToHex(md.digest(content.getBytes()));
  }
}
