package com.xeiam.xchange.mercadobitcoin.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import java.util.Arrays;
import java.util.List;

/**
 * @author timmolter
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_BRL, new CurrencyPair(Currencies.LTC, Currencies.BRL)

  );

  /**
   * Constructor
   *
   * @param exchangeSpecification
   */
  public MercadoBitcoinBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
