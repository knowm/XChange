package com.xeiam.xchange.bitcoinium.service.polling;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitcoiniumBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair(Currencies.BTC, "BITSTAMP_USD"),

  new CurrencyPair(Currencies.BTC, "BTCCHINA_CNY"),

  new CurrencyPair(Currencies.BTC, "BTCE_EUR"),

  new CurrencyPair(Currencies.BTC, "BTCE_RUR"),

  new CurrencyPair(Currencies.BTC, "BTCE_USD"),

  new CurrencyPair(Currencies.BTC, "KRAKEN_EUR"),

  new CurrencyPair(Currencies.BTC, "KRAKEN_USD")

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcoiniumBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;

  }
}
