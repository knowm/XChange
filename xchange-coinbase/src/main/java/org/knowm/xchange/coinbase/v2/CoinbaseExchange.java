package org.knowm.xchange.coinbase.v2;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbase.v2.service.CoinbaseAccountService;
import org.knowm.xchange.coinbase.v2.service.CoinbaseMarketDataService;
import org.knowm.xchange.coinbase.v2.service.CoinbaseTradeService;
import org.knowm.xchange.coinbase.cdp.service.CoinbaseAccountServiceCDP;
import org.knowm.xchange.coinbase.cdp.service.CoinbaseTradeServiceCDP;

public class CoinbaseExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CoinbaseMarketDataService(this);
    if (exchangeSpecification.getApiKey().startsWith("organizations")){
      this.accountService = new CoinbaseAccountServiceCDP(this);
      this.tradeService = new CoinbaseTradeServiceCDP(this);
    } else {
      this.accountService = new CoinbaseAccountService(this);
      this.tradeService = new CoinbaseTradeService(this);
    }
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
