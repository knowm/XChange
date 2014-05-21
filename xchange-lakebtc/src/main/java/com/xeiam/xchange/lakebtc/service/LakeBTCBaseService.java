package com.xeiam.xchange.lakebtc.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author kpysniak
 */
public class LakeBTCBaseService extends BaseExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

      CurrencyPair.BTC_USD,
      CurrencyPair.BTC_CNY

  );

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected LakeBTCBaseService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }


  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
    return CURRENCY_PAIRS;
  }
}
