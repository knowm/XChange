package com.xeiam.xchange.btctrade.service;

import java.io.IOException;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretData;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeSecretDataService;

/**
 * Represents an API key status.
 * <p>
 * For one API key, we can only have one single session, and all requests on one
 * session should be synchronized, because:
 * <ol>
 * <li>the {@code BTCTradeSecretData} of one API key is single in server side.</li>
 * <li>the nonce of one API key should be incrementing.</li>
 * </ol>
 * </p>
 */
public class BTCTradeSession {

  private final Exchange exchange;
  private final BTCTradeSecretDataService secretDataService;

  private long lastNonce = 0L;

  private BTCTradeSecretData secretData;
  private long secretExpiresTime;

  private BTCTradeDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  BTCTradeSession(Exchange exchange) {

    this.exchange = exchange;
    secretDataService = new BTCTradeSecretDataService(exchange);
  }

  public Exchange getExchange() {

    return exchange;
  }

  /**
   * Returns the public key of the API key.
   *
   * @return the public key of the API key.
   */
  public String getKey() {

    return exchange.getExchangeSpecification().getApiKey();
  }

  /**
   * Returns the next nonce of the session.
   *
   * @return the next nonce of the session.
   */
  public synchronized long nextNonce() {

    long newNonce = System.currentTimeMillis() * 1000;
    while (newNonce <= lastNonce) {
      newNonce++;
    }
    lastNonce = newNonce;
    return newNonce;
  }

  public synchronized ParamsDigest getSignatureCreator() throws IOException {

    if (secretData == null || secretExpiresTime - System.currentTimeMillis() < 60 * 1000) {
      refresh();
    }

    return signatureCreator;
  }

  private void refresh() throws IOException {

    secretData = secretDataService.getSecretData();
    secretExpiresTime = BTCTradeAdapters.adaptDatetime(secretData.getExpires()).getTime();

    signatureCreator = BTCTradeDigest.createInstance(secretData.getSecret());
  }

}
