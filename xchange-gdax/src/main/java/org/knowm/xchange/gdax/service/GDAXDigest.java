package org.knowm.xchange.gdax.service;

import java.io.IOException;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

public class GDAXDigest extends BaseParamsDigest {

  private GDAXDigest(byte[] secretKey) {

    super(secretKey, HMAC_SHA_256);
  }

  public static GDAXDigest createInstance(String secretKey) {

    try {
      return secretKey == null ? null : new GDAXDigest(Base64.decode(secretKey));
    } catch (IOException e) {
      throw new ExchangeException("Cannot decode secret key", e);
    }
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String pathWithQueryString = restInvocation.getInvocationUrl().replace(restInvocation.getBaseUrl(), "");
    String message = restInvocation.getParamValue(HeaderParam.class, "CB-ACCESS-TIMESTAMP").toString() + restInvocation.getHttpMethod()
        + pathWithQueryString + (restInvocation.getRequestBody() != null ? restInvocation.getRequestBody() : "");

    Mac mac256 = getMac();

    try {
      mac256.update(message.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new ExchangeException("Digest encoding exception", e);
    }

    return Base64.encodeBytes(mac256.doFinal());
  }
}