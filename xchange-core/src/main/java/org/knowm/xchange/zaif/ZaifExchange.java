package org.knowm.xchange.zaif;

import java.io.IOException;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.knowm.xchange.zaif.service.ZaifMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class ZaifExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new ZaifMarketDataService(this);
    //this.accountService = new BiboxAccountService(this);
    //this.tradeService = new BiboxTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return this.nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.zaif.jp/");
    exchangeSpecification.setHost("api.zaif.jp");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Zaif");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    exchangeMetaData = ((ZaifMarketDataService) marketDataService).getMetadata();
  }
}
