package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

public class BitmexDigest extends BaseParamsDigest {

  private String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */

  private BitmexDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BitmexDigest createInstance(String secretKeyBase64) {

    try {
      if (secretKeyBase64 != null)
        return new BitmexDigest(Base64.decode(secretKeyBase64.getBytes()));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not decode Base 64 string", e);
    }
    return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest sha256;
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
    sha256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    sha256.update(restInvocation.getRequestBody().getBytes());

    Mac mac512 = getMac();
    mac512.update(("/" + restInvocation.getPath()).getBytes());
    mac512.update(sha256.digest());

    return Base64.encodeBytes(mac512.doFinal()).trim();

  }

  private BitmexDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitmexDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitmexDigest(secretKeyBase64, apiKey);
  }

}
