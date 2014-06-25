package com.xeiam.xchange.btctrade.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretData;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeSecretDataService;

import si.mazi.rescu.ParamsDigest;

/**
 * Represents an API key status.
 * 
 * <p>
 * For one API key, we can only have one single session,
 * and all requests on one session should be synchronized,
 * because:
 * <ol>
 * <li>the {@code BTCTradeSecretData} of one API key is single in server side.</li>
 * <li>the nonce of one API key should be incrementing.</li>
 * </ol>
 * </p>
 */
public class BTCTradeSession {

  private final ExchangeSpecification exchangeSpecification;
  private final BTCTradeSecretDataService secretDataService;

  private long lastNonce = 0L;

  private BTCTradeSecretData secretData;
  private long secretExpiresTime;

  private BTCTradeDigest signatureCreator;

  /**
   * Constructor in package access level for {@link BTCTradeSessionFactory}.
   * 
   * @param exchangeSpecification the {@link ExchangeSpecification}.
   */
  BTCTradeSession(ExchangeSpecification exchangeSpecification) {
    this.exchangeSpecification = exchangeSpecification;
    secretDataService = new BTCTradeSecretDataService(exchangeSpecification);
  }

  public ExchangeSpecification getExchangeSpecification() {
    return exchangeSpecification;
  }

  /**
   * Returns the public key of the API key.
   *
   * @return the public key of the API key.
   */
  public String getKey() {
    return exchangeSpecification.getApiKey();
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

  public synchronized ParamsDigest getSignatureCreator() {
    if (secretData == null
        || secretExpiresTime - System.currentTimeMillis() < 60 * 1000) {
      refresh();
    }

    return signatureCreator;
  }

  private void refresh() {
    secretData = secretDataService.getSecretData();
    secretExpiresTime = BTCTradeAdapters.adaptDatetime(
        secretData.getExpires()).getTime();

    signatureCreator = BTCTradeDigest.createInstance(secretData.getSecret());
  }

}
