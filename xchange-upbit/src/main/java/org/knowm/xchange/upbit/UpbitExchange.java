package org.knowm.xchange.upbit;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.upbit.service.UpbitAccountService;
import org.knowm.xchange.upbit.service.UpbitMarketDataService;
import org.knowm.xchange.upbit.service.UpbitTradeService;

public class UpbitExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new UpbitMarketDataService(this);
    this.accountService = new UpbitAccountService(this);
    this.tradeService = new UpbitTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.upbit.com");
    exchangeSpecification.setHost("www.upbit.co.kr");
    exchangeSpecification.setExchangeName("Upbit");
    exchangeSpecification.setExchangeDescription("Upbit is a block chain exchange.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    exchangeMetaData = ((UpbitMarketDataService) marketDataService).getMetaData();
  }
}
