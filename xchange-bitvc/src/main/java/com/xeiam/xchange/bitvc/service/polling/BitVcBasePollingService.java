package com.xeiam.xchange.bitvc.service.polling;

import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitVcBasePollingService extends BaseExchangeService implements BasePollingService {

  private Collection<CurrencyPair> symbols;

  @SuppressWarnings("unchecked")
  protected BitVcBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    symbols = (Collection<CurrencyPair>) exchangeSpecification.getExchangeSpecificParametersItem("symbols");
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() {

    return symbols;
  }

}
