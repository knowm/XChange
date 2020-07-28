package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.service.BittrexAccountService;
import org.knowm.xchange.bittrex.service.BittrexMarketDataService;
import org.knowm.xchange.bittrex.service.BittrexMarketDataServiceRaw;
import org.knowm.xchange.bittrex.service.BittrexTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BittrexExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

  private static List<BittrexSymbol> bittrexSymbols = new ArrayList<>();
  private static final Object INIT_LOCK = new Object();

  @Override
  protected void initServices() {
    this.marketDataService = new BittrexMarketDataService(this);
    this.accountService = new BittrexAccountService(this);
    this.tradeService = new BittrexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bittrex.com/");
    exchangeSpecification.setHost("bittrex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bittrex");
    exchangeSpecification.setExchangeDescription("Bittrex is a cryptocurrencies exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
    if (bittrexSymbols.isEmpty()) {
      synchronized (INIT_LOCK) {
        if (bittrexSymbols.isEmpty()) {
          bittrexSymbols = ((BittrexMarketDataServiceRaw) marketDataService).getBittrexSymbols();
          BittrexAdapters.adaptMetaData(bittrexSymbols, exchangeMetaData);
        }
      }
    }
  }
}
