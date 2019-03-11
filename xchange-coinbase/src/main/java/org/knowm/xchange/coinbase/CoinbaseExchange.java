package org.knowm.xchange.coinbase;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbase.service.CoinbaseAccountService;
import org.knowm.xchange.coinbase.service.CoinbaseMarketDataService;
import org.knowm.xchange.coinbase.service.CoinbaseTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author jamespedwards42 */
public class CoinbaseExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CoinbaseMarketDataService(this);
    this.accountService = new CoinbaseAccountService(this);
    this.tradeService = new CoinbaseTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://coinbase.com");
    exchangeSpecification.setHost("coinbase.com");
    exchangeSpecification.setExchangeName("Coinbase");
    exchangeSpecification.setExchangeDescription(
        "Founded in June of 2012, Coinbase is a bitcoin wallet and platform where merchants and consumers can transact with the new digital currency bitcoin.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
