package org.knowm.xchange.anx.v2.service;

import java.io.IOException;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

/**
 * @author Matija Mazi
 */
public class ANXV2Digest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private ANXV2Digest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static ANXV2Digest createInstance(String secretKeyBase64) {

    try {
      if (secretKeyBase64 != null)
        return new ANXV2Digest(Base64.decode(secretKeyBase64.getBytes()));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not decode Base 64 string", e);
    }
    return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac = getMac();
    mac.update(restInvocation.getMethodPath().getBytes());
    mac.update(new byte[]{0});
    mac.update(restInvocation.getRequestBody().getBytes());

    return Base64.encodeBytes(mac.doFinal()).trim();
  }
}