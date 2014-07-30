package com.xeiam.xchange.btctrade.service;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Map;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;

public class BTCTradeDigest extends BaseParamsDigest {

  private static final String ENCODING = "UTF-8";
  private static final Charset CHARSET = Charset.forName(ENCODING);

  public static BTCTradeDigest createInstance(String secret) {

    return new BTCTradeDigest(secret.getBytes(CHARSET));
  }

  private BTCTradeDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_256);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String digestParams(RestInvocation restInvocation) {

    Params params = restInvocation.getParamsMap().get(FormParam.class);
    Map<String, String> nameValues = params.asHttpHeaders();
    nameValues.remove("signature");

    Params newParams = Params.of();
    for (Map.Entry<String, String> nameValue : nameValues.entrySet()) {
      newParams.add(nameValue.getKey(), nameValue.getValue());
    }

    String message = newParams.asQueryString();

    Mac mac = getMac();
    mac.update(message.getBytes());

    return String.format("%064x", new BigInteger(1, mac.doFinal()));
  }

}
