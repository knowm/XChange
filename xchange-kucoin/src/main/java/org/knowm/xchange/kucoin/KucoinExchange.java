package org.knowm.xchange.kucoin;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kucoin.service.KucoinAccountService;
import org.knowm.xchange.kucoin.service.KucoinMarketDataService;
import org.knowm.xchange.kucoin.service.KucoinTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Jan Akerman
 */
public class KucoinExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    marketDataService = new KucoinMarketDataService(this);
    accountService = new KucoinAccountService();
    tradeService = new KucoinTradeService();
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new NotYetImplementedForExchangeException("Authenticated endpoints not yet implemented");
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.kucoin.com/");
    exchangeSpecification.setHost("api.kucoin.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kucoin");
    exchangeSpecification.setExchangeDescription("Kucoinis a Bitcoin exchange operated by Kucoin Co., Limited.");
    return exchangeSpecification;
  }

}
