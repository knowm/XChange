/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.btctrade.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;

/**
 * {@link BTCTradeSession} factory to ensure the polling service instances,
 *  which using the same API key, share the same secret data and nonce.
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
   * Returns the session of the specified API key
   * in the {@code ExchangeSpecification}.
   *
   * @param exchangeSpecification the {@link ExchangeSpecification}
   * to create the session.
   * @return the session.
   */
  public synchronized BTCTradeSession getSession(
      ExchangeSpecification exchangeSpecification) {
    String publicKey = exchangeSpecification.getApiKey();
    log.debug("Getting session: {}", publicKey);
    BTCTradeSession session = sessions.get(publicKey);
    if (session == null) {
      log.debug("Creating session: {}", publicKey);
      session = new BTCTradeSession(exchangeSpecification);
      sessions.put(publicKey, session);
    }
    return session;
  }

}
