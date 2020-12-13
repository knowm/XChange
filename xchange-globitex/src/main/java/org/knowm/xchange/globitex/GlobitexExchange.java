package org.knowm.xchange.globitex;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexSymbols;
import org.knowm.xchange.globitex.service.GlobitexAccountService;
import org.knowm.xchange.globitex.service.GlobitexMarketDataService;
import org.knowm.xchange.globitex.service.GlobitexMarketDataServiceRaw;
import org.knowm.xchange.globitex.service.GlobitexTradeService;

/*@author makarid*/

/*username is needed in order to get UserTrades.
 * username is globitex account number*/
public class GlobitexExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new GlobitexMarketDataService(this);
    this.accountService = new GlobitexAccountService(this);
    this.tradeService = new GlobitexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.globitex.com");
    exchangeSpecification.setHost("api.globitex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Globitex");
    exchangeSpecification.setExchangeDescription("Globitex is a Bitcoin exchange based in UK.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    super.remoteInit();
    GlobitexSymbols globitexSymbols =
        ((GlobitexMarketDataServiceRaw) marketDataService).getGlobitexSymbols();
    exchangeMetaData = GlobitexAdapters.adaptToExchangeMetaData(globitexSymbols);
  }
}
