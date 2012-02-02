package com.xeiam.xchange.provider.mtgox;

import com.xeiam.xchange.ExchangeProvider;
import com.xeiam.xchange.SessionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  <p>Provider to provide the following to XChange:</p>
 *  <ul>
 *  <li>Entry point to the Mt Gox Exchange</li>
 *  </ul>
 *
 * @since 0.0.1
 *         
 */
public class MtGoxExchangeProvider implements ExchangeProvider {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxExchangeProvider.class);

  @Override
  public void start(SessionOptions sessionOptions) {
    log.debug("Connecting to Mt Gox");
  }

  @Override
  public void stop(SessionOptions sessionOptions) {
    log.debug("Disconnecting from Mt Gox");
  }
}
