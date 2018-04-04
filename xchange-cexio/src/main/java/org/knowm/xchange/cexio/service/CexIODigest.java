package org.knowm.xchange.cexio.service;

import java.math.BigInteger;
import java.util.List;
import javax.crypto.Mac;
import org.knowm.xchange.cexio.dto.CexIORequest;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.SynchronizedValueFactory;

public class CexIODigest extends BaseParamsDigest {

  private final String clientId;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param clientId Account user name
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or
   *     the decoded key is invalid).
   * @param nonceFactory
   */
  private CexIODigest(
      String secretKeyBase64,
      String clientId,
      String apiKey,
      SynchronizedValueFactory<Long> nonceFactory) {
    super(secretKeyBase64, HMAC_SHA_256);

    this.clientId = clientId;
    this.apiKey = apiKey;
    this.nonceFactory = nonceFactory;
  }

  public static CexIODigest createInstance(
      String secretKeyBase64,
      String clientId,
      String apiKey,
      SynchronizedValueFactory<Long> nonceFactory) {
    return secretKeyBase64 == null
        ? null
        : new CexIODigest(secretKeyBase64, clientId, apiKey, nonceFactory);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac256 = getMac();
    String nonce = nonceFactory.createValue().toString();
    mac256.update(nonce.getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    String signature = String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();

    List<Object> unannanotatedParams = restInvocation.getUnannanotatedParams();

    for (Object unannanotatedParam : unannanotatedParams) {
      // there *should* be only one
      if (unannanotatedParam instanceof CexIORequest) {
        CexIORequest request = (CexIORequest) unannanotatedParam;
        request.signature = signature;
        request.nonce = nonce;
        request.key = apiKey;
      }
    }

    return signature;
  }
}
