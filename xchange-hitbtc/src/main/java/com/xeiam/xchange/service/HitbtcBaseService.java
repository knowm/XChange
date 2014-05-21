package com.xeiam.xchange.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.Collection;

/**
 * @author kpysniak
 */
public abstract class HitbtcBaseService extends BaseExchangeService {

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HitbtcBaseService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

}
