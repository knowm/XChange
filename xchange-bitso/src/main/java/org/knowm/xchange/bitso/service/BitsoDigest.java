package org.knowm.xchange.bitso.service;

import jakarta.ws.rs.HeaderParam;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/**
 * Bitso API v3 authentication digest Creates HMAC-SHA256 signature following the pattern: nonce +
 * HTTP method + request path + JSON payload Returns Authorization header in format: "Bitso
 * <key>:<nonce>:<signature>"
 */
public class BitsoDigest extends BaseParamsDigest {

  private final String apiKey;

  private BitsoDigest(String secretKey, String apiKey) {
    super(secretKey, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitsoDigest createInstance(String secretKey, String apiKey) {
    return secretKey == null ? null : new BitsoDigest(secretKey, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    // Get nonce from header
    String currentNonce = restInvocation.getParamValue(HeaderParam.class, "Bitso-Nonce").toString();

    // Get HTTP method
    String httpMethod = restInvocation.getHttpMethod();

    // Get request path (includes query parameters)
    String requestPath = restInvocation.getPath();

    if (!StringUtils.isEmpty(restInvocation.getQueryString())) {
      requestPath += "?" + restInvocation.getQueryString();
    }

    // Get JSON payload (empty for GET requests)
    String jsonPayload = "";
    if (restInvocation.getRequestBody() != null) {
      jsonPayload = restInvocation.getRequestBody();
    }

    // Create message: nonce + HTTP method + request path + JSON payload
    String message = currentNonce + httpMethod + requestPath + jsonPayload;

    // Calculate HMAC-SHA256
    Mac mac = getMac();
    mac.update(message.getBytes(StandardCharsets.UTF_8));

    // Calculate hex-encoded signature
    String signature = String.format("%064x", new BigInteger(1, mac.doFinal()));

    // Return Authorization header in Bitso format: "Bitso <key>:<nonce>:<signature>"
    return String.format("Bitso %s:%s:%s", apiKey, currentNonce, signature);
  }
}
