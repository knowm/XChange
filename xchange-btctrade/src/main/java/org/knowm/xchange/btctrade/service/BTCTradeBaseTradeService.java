package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import si.mazi.rescu.ParamsDigest;

public class BTCTradeBaseTradeService extends BTCTradeBaseService {

  protected final String publicKey;
  protected final BTCTradeSession session;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCTradeBaseTradeService(Exchange exchange) {

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
