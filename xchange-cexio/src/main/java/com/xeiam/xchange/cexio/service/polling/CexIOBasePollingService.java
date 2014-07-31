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

  new CurrencyPair("GHS", "BTC"),

  new CurrencyPair("LTC", "BTC"),

  new CurrencyPair("NMC", "BTC"),

  new CurrencyPair("GHS", "NMC"),

  new CurrencyPair("IXC", "BTC"));

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
