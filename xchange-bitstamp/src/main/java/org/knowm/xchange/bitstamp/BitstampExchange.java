package org.knowm.xchange.bitstamp;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampPairInfo;
import org.knowm.xchange.bitstamp.service.BitstampAccountService;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataService;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataServiceRaw;
import org.knowm.xchange.bitstamp.service.BitstampTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Matija Mazi */
public class BitstampExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new CurrentNanosecondTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new BitstampMarketDataService(this);
    this.tradeService = new BitstampTradeService(this);
    this.accountService = new BitstampAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitstamp.net");
    exchangeSpecification.setHost("www.bitstamp.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitstamp");
    exchangeSpecification.setExchangeDescription(
        "Bitstamp is a Bitcoin exchange registered in Slovenia.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    BitstampMarketDataServiceRaw dataService =
        (BitstampMarketDataServiceRaw) this.marketDataService;
    BitstampPairInfo[] bitstampPairInfos = dataService.getTradingPairsInfo();
    exchangeMetaData =
        BitstampAdapters.adaptMetaData(Arrays.asList(bitstampPairInfos), exchangeMetaData);
  }
}
