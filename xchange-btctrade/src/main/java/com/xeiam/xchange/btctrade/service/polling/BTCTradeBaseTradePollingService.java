package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.service.BTCTradeDigest;
import com.xeiam.xchange.btctrade.service.BTCTradeSession;
import com.xeiam.xchange.btctrade.service.BTCTradeSessionFactory;

import si.mazi.rescu.ParamsDigest;

public class BTCTradeBaseTradePollingService extends BTCTradeBasePollingService {

  protected final String publicKey;
  protected final BTCTradeSession session;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCTradeBaseTradePollingService(Exchange exchange) {

    super(exchange);

    session = BTCTradeSessionFactory.INSTANCE.getSession(exchange);
    publicKey = session.getKey();
  }

  /**
   * Returns the {@link BTCTradeDigest}.
   *
   * @return the {@link BTCTradeDigest}.
   * @throws IOException indicates I/O exception in refreshing session from server.
   */
  public ParamsDigest getSignatureCreator() throws IOException {

    return session.getSignatureCreator();
  }

}
