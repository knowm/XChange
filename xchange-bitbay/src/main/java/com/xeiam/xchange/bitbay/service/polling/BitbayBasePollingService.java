package com.xeiam.xchange.bitbay.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.Bitbay;
import com.xeiam.xchange.bitbay.service.BitbayDigest;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak
 */
public class BitbayBasePollingService<T extends Bitbay> extends BaseExchangeService implements BasePollingService {

  protected final T bitbay;
  protected final ParamsDigest signatureCreator;
  protected final String apiKey;

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

    CurrencyPair.BTC_USD,
    CurrencyPair.BTC_PLN,
    CurrencyPair.LTC_USD,
    CurrencyPair.LTC_EUR,
    CurrencyPair.LTC_BTC,
    new CurrencyPair(Currencies.LTC, Currencies.PLN)

  );

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitbayBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitbay = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BitbayDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    return CURRENCY_PAIRS;
  }
}
