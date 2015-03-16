package com.xeiam.xchange.service;

import com.xeiam.xchange.Exchange;

/**
 * Top of the hierarchy abstract class for an "exchange service"
 */
public abstract class BaseExchangeService {

  /**
   * The base Exchange. Every service has access to the containing exchange class, which hold meta data and the exchange specification
   */
  protected final Exchange exchange;

  /**
   * Constructor
   */
  protected BaseExchangeService(Exchange exchange) {

    this.exchange = exchange;
  }

}
