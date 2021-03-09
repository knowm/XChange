package org.knowm.xchange.mercadobitcoin;

import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinAccountService;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinMarketDataService;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.mercadobitcoin.net");
    exchangeSpecification.setHost("www.mercadobitcoin.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Mercado Bitcoin");
    exchangeSpecification.setExchangeDescription(
        "Mercado Bitcoin is a Bitcoin and Litecoin exchange registered in Brazil.");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new MercadoBitcoinMarketDataService(this);
    this.accountService = new MercadoBitcoinAccountService(this);
    this.tradeService = new MercadoBitcoinTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
