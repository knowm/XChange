package org.knowm.xchange.itbit.v1.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

public class ItBitHmacPostBodyDigest extends BaseParamsDigest {

  private static final String FIELD_SEPARATOR = "\",\"";

  private final String apiKey;

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private ItBitHmacPostBodyDigest(String apiKey, String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
    this.apiKey = apiKey;
  }

  public static ItBitHmacPostBodyDigest createInstance(String apiKey, String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new ItBitHmacPostBodyDigest(apiKey, secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }

    Map<String, String> httpHeaders = restInvocation.getHttpHeadersFromParams();
    String currentNonce = httpHeaders.get("X-Auth-Nonce");
    String currentTimestamp = httpHeaders.get("X-Auth-Timestamp");

    // only POST requests will have a non-null request body.
    String requestBody = restInvocation.getRequestBody();
    if (requestBody == null) {
      requestBody = "";
    } else {
      requestBody = requestBody.replace("\"", "\\\"");
    }

    String verb = restInvocation.getHttpMethod().trim();
    String invocationUrl = restInvocation.getInvocationUrl().trim();
    String message = new StringBuilder("[\"").append(verb).append(FIELD_SEPARATOR).append(invocationUrl).append(FIELD_SEPARATOR).append(requestBody)
        .append(FIELD_SEPARATOR).append(currentNonce).append(FIELD_SEPARATOR).append(currentTimestamp).append("\"]").toString();

    md.update((currentNonce + message).getBytes());
    BigInteger hash = new BigInteger(md.digest());

    Mac mac512 = getMac();
    mac512.update(invocationUrl.getBytes());
    mac512.update(hash.toByteArray());

    return apiKey + ":" + Base64.encodeBytes((new BigInteger(mac512.doFinal())).toByteArray());
  }
}
