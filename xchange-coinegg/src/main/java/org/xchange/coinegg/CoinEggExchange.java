package org.xchange.coinegg;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.xchange.coinegg.service.CoinEggAccountService;
import org.xchange.coinegg.service.CoinEggMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinEggExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CoinEggMarketDataService(this);
    this.accountService = new CoinEggAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.coinegg.com");
    exchangeSpecification.setHost("http://api.coinegg.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CoinEgg");
    exchangeSpecification.setExchangeDescription(
        "CoinEgg is a Bitcoin exchange based in the United Kingdom.");
    exchangeSpecification.setApiKey("");
    exchangeSpecification.setSecretKey("");
    exchangeSpecification.setPassword("");

    return exchangeSpecification;
  }

  @Override
  public TradeService getTradeService() {
    throw new NotAvailableFromExchangeException();
  }
}
