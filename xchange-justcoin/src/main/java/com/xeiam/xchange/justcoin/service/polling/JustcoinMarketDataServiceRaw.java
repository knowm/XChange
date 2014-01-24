package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.justcoin.Justcoin;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author jamespedwards42
 */
public class JustcoinMarketDataServiceRaw extends BasePollingExchangeService {

  protected final Justcoin justcoin;

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public JustcoinMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.justcoin = RestProxyFactory.createProxy(Justcoin.class, exchangeSpecification.getSslUri());
  }

  public JustcoinTicker[] getTickers(final String tradableIdentifier, final String currency) throws IOException {

    verify(tradableIdentifier, currency);

    return justcoin.getTickers();

  }

  public JustcoinDepth getMarketDepth(final String tradableIdentifier, final String currency) throws IOException {

    verify(tradableIdentifier, currency);
    return justcoin.getDepth(tradableIdentifier.toUpperCase(), currency.toUpperCase());
  }
  
  /**
   * Verify that both currencies can make valid pair
   * 
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency
   */
  private void verify(final String tradableIdentifier, final String currency) throws IOException {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(JustcoinUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }

  public List<CurrencyPair> getExchangeSymbols() {

    return new ArrayList<CurrencyPair>(JustcoinUtils.CURRENCY_PAIRS);
  }
}
