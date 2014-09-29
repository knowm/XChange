package com.xeiam.xchange.okcoin.service.polling;

import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class OkCoinBasePollingService extends BasePollingExchangeService implements BasePollingService {

  private final Collection<CurrencyPair> symbols;

  /**
   * @param exchangeSpecification the exchange specification.
   */
  @SuppressWarnings("unchecked")
  protected OkCoinBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    symbols = (Collection<CurrencyPair>) exchangeSpecification.getExchangeSpecificParametersItem(OkCoinExchange.SYMBOLS_PARAMETER);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CurrencyPair> getExchangeSymbols() {
    return symbols;
  }

}
