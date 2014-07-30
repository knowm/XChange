package com.xeiam.xchange.btctrade.service.polling;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.service.BTCTradeDigest;
import com.xeiam.xchange.btctrade.service.BTCTradeSession;
import com.xeiam.xchange.btctrade.service.BTCTradeSessionFactory;

public class BTCTradeBaseTradePollingService extends BTCTradeBasePollingService {

  protected final String publicKey;
  protected final BTCTradeSession session;

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeBaseTradePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    session = BTCTradeSessionFactory.INSTANCE.getSession(exchangeSpecification);
    publicKey = session.getKey();
  }

  /**
   * Returns the next nonce.
   *
   * @return the next nonce.
   */
  public long nextNonce() {

    return session.nextNonce();
  }

  /**
   * Returns the {@link BTCTradeDigest}.
   *
   * @return the {@link BTCTradeDigest}.
   */
  public ParamsDigest getSignatureCreator() {

    return session.getSignatureCreator();
  }

}
