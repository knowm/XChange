package org.knowm.xchange.livecoin;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.service.LivecoinMarketDataService;
import org.knowm.xchange.livecoin.service.LivecoinMarketDataServiceRaw;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class LivecoinExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.livecoin.net");
    exchangeSpecification.setHost("api.livecoin.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Livecoin");
    exchangeSpecification.setExchangeDescription("Livecoin - A convenient way to buy and sell Bitcoin");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new LivecoinMarketDataService(this);
    this.accountService = null; // new LIVECOINAccountService(this);
    this.tradeService = null; // new LIVECOINTradeService(this);
  }

  @Override
  public void remoteInit() throws IOException {
    List<LivecoinRestriction> products = ((LivecoinMarketDataServiceRaw) marketDataService).getConbaseExProducts();
    exchangeMetaData = LivecoinAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
    // System.out.println("JSON: " +
    // ObjectMapperHelper.toJSON(exchangeMetaData));
  }
}
