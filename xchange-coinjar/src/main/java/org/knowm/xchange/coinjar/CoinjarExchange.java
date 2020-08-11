package org.knowm.xchange.coinjar;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinjar.service.CoinjarAccountService;
import org.knowm.xchange.coinjar.service.CoinjarBaseService;
import org.knowm.xchange.coinjar.service.CoinjarMarketDataService;
import org.knowm.xchange.coinjar.service.CoinjarTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinjarExchange extends BaseExchange implements Exchange {

  private static SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.accountService = new CoinjarAccountService(this);
    this.marketDataService = new CoinjarMarketDataService(this);
    this.tradeService = new CoinjarTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri(CoinjarBaseService.LIVE_URL);
    exchangeSpecification.setExchangeName("Coinjar");
    exchangeSpecification.setExchangeDescription("Coinjar");

    return exchangeSpecification;
  }
}
