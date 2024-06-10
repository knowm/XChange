package org.knowm.xchange.coinbase.v4;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbase.v2.service.CoinbaseMarketDataService;
import org.knowm.xchange.coinbase.v4.service.CoinbaseAccountServiceCDP;
import org.knowm.xchange.coinbase.v4.service.CoinbaseTradeServiceCDP;

public class CoinbaseExchangeCDP extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CoinbaseMarketDataService(this);
    this.accountService = new CoinbaseAccountServiceCDP(this);
    this.tradeService = new CoinbaseTradeServiceCDP(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.coinbase.com");
    exchangeSpecification.setHost("api.coinbase.com");
    exchangeSpecification.setExchangeName("Coinbase");
    exchangeSpecification.setExchangeDescription(
        "Founded in June of 2012, Coinbase is a bitcoin wallet and platform where merchants and consumers can transact with the new digital currency bitcoin.");
    return exchangeSpecification;
  }
}
