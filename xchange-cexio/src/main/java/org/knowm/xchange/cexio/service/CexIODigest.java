package org.knowm.xchange.cexio.service;

import org.knowm.xchange.cexio.dto.CexIORequest;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.crypto.Mac;
import java.math.BigInteger;

public class CexIODigest extends BaseParamsDigest {

  private final String clientId;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  private CexIODigest(String secretKeyBase64, String clientId, String apiKey, SynchronizedValueFactory<Long> nonceFactory) {
    super(secretKeyBase64, HMAC_SHA_256);

    this.clientId = clientId;
    this.apiKey = apiKey;
    this.nonceFactory = nonceFactory;
  }

  public static CexIODigest createInstance(String secretKeyBase64, String clientId, String apiKey, SynchronizedValueFactory<Long> nonceFactory) {
    return secretKeyBase64 == null ? null : new CexIODigest(secretKeyBase64, clientId, apiKey, nonceFactory);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String nonce = nonceFactory.createValue().toString();

    Mac mac256 = getMac();
    mac256.update(nonce.getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    String signature = String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();

    CexIORequest request = (CexIORequest) restInvocation.getUnannanotatedParams().get(0);
    request.signature = signature;
    request.nonce = nonce;
    request.key = apiKey;

    return signature;
  }

}
