package com.xeiam.xchange.okcoin.service.polling;

import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class OkCoinBasePollingService extends BasePollingExchangeService implements BasePollingService {

  /** Set to true if international site should be used */
  protected final boolean useIntl;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinBasePollingService(Exchange exchange) {

    super(exchange);

    useIntl = (Boolean) exchange.getExchangeSpecification().getExchangeSpecificParameters().get("Use_Intl");

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
