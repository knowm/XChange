package org.knowm.xchange.loyalbit.service;

import java.io.IOException;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

public class LoyalbitDigest extends BaseParamsDigest {

  private final String clientId;
  private final byte[] apiKey;

  private LoyalbitDigest(String secretKeyHex, String clientId, String apiKeyHex) throws IOException {
    super(secretKeyHex.getBytes(), HMAC_SHA_256);
    this.clientId = clientId;
    this.apiKey = apiKeyHex.getBytes();
  }

  public static LoyalbitDigest createInstance(String secretKeyBase64, String clientId, String apiKey) {
    try {
      return secretKeyBase64 == null ? null : new LoyalbitDigest(secretKeyBase64, clientId, apiKey);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error parsing API key or secret", e);
    }
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Mac mac256 = getMac();
    mac256.update(restInvocation.getInvocationUrl().getBytes());
    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey);

    return Base64.encodeBytes(mac256.doFinal());
  }
}