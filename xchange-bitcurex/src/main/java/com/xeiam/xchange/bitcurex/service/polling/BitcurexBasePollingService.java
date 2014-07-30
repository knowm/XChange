package com.xeiam.xchange.bitcurex.service.polling;

import static com.xeiam.xchange.currency.Currencies.EUR;
import static com.xeiam.xchange.currency.Currencies.PLN;

import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.Bitcurex;
import com.xeiam.xchange.bitcurex.BitcurexExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitcurexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected Bitcurex bitcurex;

  public final List<CurrencyPair> CURRENCY_PAIRS;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcurexBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    bitcurex = RestProxyFactory.createProxy(Bitcurex.class, exchangeSpecification.getSslUri());

    String currency = (String) exchangeSpecification.getExchangeSpecificParametersItem(BitcurexExchange.KEY_CURRENCY);
    CurrencyPair pair = currency.equals(EUR) ? CurrencyPair.BTC_EUR : CurrencyPair.BTC_PLN;
    CURRENCY_PAIRS = Arrays.asList(pair);
  }

  public void verify(String currency) throws ExchangeException {

    if (!currency.equalsIgnoreCase(EUR) && !currency.equalsIgnoreCase(PLN))
      throw new ExchangeException("Invalid currency: " + currency);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }

}
