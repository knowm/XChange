package org.knowm.xchange.cobinhood;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cobinhood.service.CobinhoodAccountService;
import org.knowm.xchange.cobinhood.service.CobinhoodMarketDataService;
import org.knowm.xchange.cobinhood.service.CobinhoodTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

public class CobinhoodExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {

    this.marketDataService = new CobinhoodMarketDataService(this);
    this.accountService = new CobinhoodAccountService(this);
    this.tradeService = new CobinhoodTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.cobinhood.com/");
    exchangeSpecification.setHost("cobinhood.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cobinhood");
    exchangeSpecification.setExchangeDescription("Cobinhood is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    exchangeMetaData = ((CobinhoodMarketDataService) marketDataService).getMetadata();
  }
}
