package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btctrade.BTCTradeAdapters;
import org.knowm.xchange.btctrade.dto.BTCTradeSecretData;
import si.mazi.rescu.ParamsDigest;

/**
 * Represents an API key status.
 *
 * <p>For one API key, we can only have one single session, and all requests on one session should
 * be synchronized, because:
 *
 * <ol>
 *   <li>the {@code BTCTradeSecretData} of one API key is single in server side.
 *   <li>the nonce of one API key should be incrementing.
 * </ol>
 */
public class BTCTradeSession {

  private final Exchange exchange;
  private final BTCTradeSecretDataService secretDataService;

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
