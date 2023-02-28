package org.knowm.xchange.krakenfutures;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.krakenfutures.service.KrakenFuturesAccountService;
import org.knowm.xchange.krakenfutures.service.KrakenFuturesMarketDataService;
import org.knowm.xchange.krakenfutures.service.KrakenFuturesMarketDataServiceRaw;
import org.knowm.xchange.krakenfutures.service.KrakenFuturesTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesExchange extends BaseExchange implements Exchange {

  private final String DEMO_URL = "https://demo-futures.kraken.com/derivatives";

  @Override
  protected void initServices() {
    concludeHostParams(exchangeSpecification);

    this.marketDataService = new KrakenFuturesMarketDataService(this);
    this.accountService = new KrakenFuturesAccountService(this);
    this.tradeService = new KrakenFuturesTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://futures.kraken.com/derivatives");
    exchangeSpecification.setHost("https://futures.kraken.com/");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("KrakenFutures");
    exchangeSpecification.setExchangeDescription(
        "KrakenFutures is a bitcoin derivatives exchange operated by Kraken Ltd.");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    exchangeMetaData = KrakenFuturesAdapters.adaptInstrumentsMetaData(((KrakenFuturesMarketDataServiceRaw)marketDataService).getKrakenFuturesInstruments());
  }

  private void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (Boolean.TRUE.equals(
              exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
        exchangeSpecification.setSslUri(DEMO_URL);
      }
    }
  }
}
