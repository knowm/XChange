package org.knowm.xchange.abucoins.service;

import java.math.BigInteger;
import java.util.List;

import javax.crypto.Mac;

import org.knowm.xchange.abucoins.dto.AbucoinsRequest;
import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.SynchronizedValueFactory;

public class AbucoinsDigest extends BaseParamsDigest {

  private final String clientId;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param secretBase64
   * @param passphrase Account user name
   * @param key @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   * @param nonceFactory
   */
  private AbucoinsDigest(String secretBase64, String passphrase, String key, SynchronizedValueFactory<Long> nonceFactory) {
    super(secretBase64, HMAC_SHA_256);

    this.clientId = passphrase;
    this.apiKey = key;
    this.nonceFactory = nonceFactory;
  }

  public static AbucoinsDigest createInstance(String secretBase64, String passphrase, String key, SynchronizedValueFactory<Long> nonceFactory) {
    return secretBase64 == null ? null : new AbucoinsDigest(secretBase64, passphrase, key, nonceFactory);
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
      //there *should* be only one
      if (unannanotatedParam instanceof AbucoinsRequest) {
    	  	AbucoinsRequest request = (AbucoinsRequest) unannanotatedParam;
        request.signature = signature;
        request.nonce = nonce;
        request.key = apiKey;
      }
    }

    return signature;
  }

}
