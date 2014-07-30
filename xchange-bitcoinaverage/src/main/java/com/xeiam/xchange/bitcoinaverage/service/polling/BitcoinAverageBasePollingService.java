package com.xeiam.xchange.bitcoinaverage.service.polling;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitcoinAverageBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_AUD,

  CurrencyPair.BTC_BRL,

  CurrencyPair.BTC_CAD,

  CurrencyPair.BTC_CNY,

  CurrencyPair.BTC_CZK,

  CurrencyPair.BTC_EUR,

  CurrencyPair.BTC_GBP,

  CurrencyPair.BTC_ILS,

  CurrencyPair.BTC_JPY,

  CurrencyPair.BTC_NOK,

  CurrencyPair.BTC_NZD,

  CurrencyPair.BTC_PLN,

  CurrencyPair.BTC_RUB,

  CurrencyPair.BTC_SEK,

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_ZAR

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcoinAverageBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
