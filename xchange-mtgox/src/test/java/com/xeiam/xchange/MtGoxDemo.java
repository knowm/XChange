package com.xeiam.xchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates typical use cases against the Mt Gox exchange
 */
public class MtGoxDemo {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxDemo.class);

  /**
   * @param args Not required
   */
  public static void main(String[] args) {

    String mtgoxProviderClassName = "com.xeiam.xchange.provider.mtgox.MtGoxExchangeProvider";

    SessionOptions sessionOptions = new SessionOptions(mtgoxProviderClassName);

    Session session = new Session(sessionOptions);
    session.start();
    session.stop();

  }

}
