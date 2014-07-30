package com.xeiam.xchange.virtex.v2.service.polling;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class VirtexBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_CAD,

  CurrencyPair.BTC_LTC,

  new CurrencyPair("LTC", "CAD")

  );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VirtexBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
