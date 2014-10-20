package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * Polling service.
 */
public abstract class CoinsetterBasePollingService extends BaseExchangeService implements BasePollingService {

  private final Collection<CurrencyPair> symbols;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    symbols = Arrays.asList(CurrencyPair.BTC_USD);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    return symbols;
  }

}
