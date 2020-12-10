package org.knowm.xchange.bibox;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bibox.service.BiboxAccountService;
import org.knowm.xchange.bibox.service.BiboxMarketDataService;
import org.knowm.xchange.bibox.service.BiboxTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

public class BiboxExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new BiboxMarketDataService(this);
    this.accountService = new BiboxAccountService(this);
    this.tradeService = new BiboxTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bibox.com/");
    exchangeSpecification.setHost("bibox.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bibox");
    exchangeSpecification.setExchangeDescription("AI ENHANCED ENCRYPTED DIGITAL ASSET EXCHANGE.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    exchangeMetaData = ((BiboxMarketDataService) marketDataService).getMetadata();
  }
}
