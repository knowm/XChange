package org.knowm.xchange.btctrade.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;

/**
 * {@link BTCTradeSession} factory to ensure the polling service instances, which using the same API key, share the same secret data and nonce.
 */
public enum BTCTradeSessionFactory {

  /**
   * Enum Singleton Pattern.
   */
  INSTANCE;

  private final Logger log = LoggerFactory.getLogger(BTCTradeSessionFactory.class);

  private Map<String, BTCTradeSession> sessions;

  private BTCTradeSessionFactory() {

    log.debug("Intializing session factory.");
    sessions = new HashMap<String, BTCTradeSession>();
  }

  /**
   * @param exchange
   * @return
   */
  public synchronized BTCTradeSession getSession(Exchange exchange) {

    String publicKey = exchange.getExchangeSpecification().getApiKey();
    log.debug("Getting session: {}", publicKey);
    BTCTradeSession session = sessions.get(publicKey);
    if (session == null) {
      log.debug("Creating session: {}", publicKey);
      session = new BTCTradeSession(exchange);
      sessions.put(publicKey, session);
    }
    return session;
  }

}
