package com.xeiam.xchange.okcoin.service.polling;

import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class OkCoinBasePollingService extends BasePollingExchangeService implements BasePollingService {

  private final Collection<CurrencyPair> symbols;

  /** Set to true if international site should be used */
  protected final boolean useIntl;

  /**
   * @param exchangeSpecification the exchange specification.
   */
  @SuppressWarnings("unchecked")
  protected OkCoinBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    useIntl = (Boolean) exchangeSpecification.getExchangeSpecificParameters().get("Use_Intl");

    if (useIntl) {
      symbols = (Collection<CurrencyPair>) exchangeSpecification.getExchangeSpecificParametersItem(OkCoinExchange.INTL_SYMBOLS_PARAMETER);
    }
    else {
      symbols = (Collection<CurrencyPair>) exchangeSpecification.getExchangeSpecificParametersItem(OkCoinExchange.SYMBOLS_PARAMETER);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CurrencyPair> getExchangeSymbols() {

    return symbols;
  }

}
