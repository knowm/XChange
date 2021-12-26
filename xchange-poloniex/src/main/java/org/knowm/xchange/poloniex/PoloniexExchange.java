package org.knowm.xchange.poloniex;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.service.PoloniexAccountService;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataService;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.poloniex.service.PoloniexTradeService;
import org.knowm.xchange.utils.nonce.TimestampIncrementingNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Zach Holmes */
public class PoloniexExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new TimestampIncrementingNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new PoloniexMarketDataService(this);
    this.accountService = new PoloniexAccountService(this);
    this.tradeService =
        new PoloniexTradeService(this, (PoloniexMarketDataService) marketDataService);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://poloniex.com/");
    exchangeSpecification.setHost("poloniex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Poloniex");
    exchangeSpecification.setExchangeDescription("Poloniex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    PoloniexMarketDataServiceRaw poloniexMarketDataServiceRaw =
        (PoloniexMarketDataServiceRaw) marketDataService;

    Map<String, PoloniexCurrencyInfo> poloniexCurrencyInfoMap =
        poloniexMarketDataServiceRaw.getPoloniexCurrencyInfo();
    Map<String, PoloniexMarketData> poloniexMarketDataMap =
        poloniexMarketDataServiceRaw.getAllPoloniexTickers();

    exchangeMetaData =
        PoloniexAdapters.adaptToExchangeMetaData(
            poloniexCurrencyInfoMap, poloniexMarketDataMap, exchangeMetaData);
  }
}
