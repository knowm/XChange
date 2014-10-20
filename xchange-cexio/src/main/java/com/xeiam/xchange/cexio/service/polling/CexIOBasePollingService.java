package com.xeiam.xchange.cexio.service.polling;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class CexIOBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair("BTC", "USD"), new CurrencyPair("GHS", "USD"), new CurrencyPair("LTC", "USD"), new CurrencyPair("DOGE", "USD"), new CurrencyPair("DRK", "USD"),

  new CurrencyPair("GHS", "BTC"), new CurrencyPair("LTC", "BTC"), new CurrencyPair("DOGE", "BTC"), new CurrencyPair("DRK", "BTC"), new CurrencyPair("NMC", "BTC"), new CurrencyPair("IXC", "BTC"),
      new CurrencyPair("POT", "BTC"), new CurrencyPair("ANC", "BTC"), new CurrencyPair("MEC", "BTC"), new CurrencyPair("WDC", "BTC"), new CurrencyPair("FTC", "BTC"), new CurrencyPair("DGB", "BTC"),
      new CurrencyPair("USDE", "BTC"), new CurrencyPair("MYR", "BTC"), new CurrencyPair("AUR", "BTC"),

      new CurrencyPair("GHS", "LTC"), new CurrencyPair("DOGE", "LTC"), new CurrencyPair("DRK", "LTC"), new CurrencyPair("MEC", "LTC"), new CurrencyPair("WDC", "LTC"), new CurrencyPair("ANC", "LTC"),
      new CurrencyPair("FTC", "LTC"),

      new CurrencyPair("BTC", "EUR"), new CurrencyPair("LTC", "EUR"), new CurrencyPair("DOGE", "EUR"));

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CexIOBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
