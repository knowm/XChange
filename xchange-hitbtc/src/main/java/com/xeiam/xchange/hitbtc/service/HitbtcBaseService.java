package com.xeiam.xchange.hitbtc.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

public class HitbtcBaseService extends BaseExchangeService {
  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(
      CurrencyPair.BTC_USD,
      CurrencyPair.BTC_EUR,
      CurrencyPair.LTC_BTC,
      CurrencyPair.LTC_USD,
      CurrencyPair.LTC_EUR,
      CurrencyPair.EUR_USD
      );

  protected HitbtcBaseService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
    // TODO Auto-generated method stub
    return CURRENCY_PAIRS;
  }
}
