package com.xeiam.xchange.okcoin.service.polling;

import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class OkCoinBasePollingService extends BasePollingExchangeService implements BasePollingService {

  private final List<CurrencyPair> symbols;

  /** Set to true if international site should be used */
  protected final boolean useIntl;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinBasePollingService(Exchange exchange) {

    super(exchange);

    // TODO look at this
    useIntl = (Boolean) exchange.getExchangeSpecification().getExchangeSpecificParameters().get("Use_Intl");

    if (useIntl) {
      symbols = (List<CurrencyPair>) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(OkCoinExchange.INTL_SYMBOLS_PARAMETER);
    } else {
      symbols = (List<CurrencyPair>) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(OkCoinExchange.SYMBOLS_PARAMETER);
    }
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return symbols;
  }

}
