package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.service.BittrexAccountService;
import org.knowm.xchange.bittrex.service.BittrexMarketDataService;
import org.knowm.xchange.bittrex.service.BittrexMarketDataServiceRaw;
import org.knowm.xchange.bittrex.service.BittrexTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BittrexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

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
    exchangeSpecification.setSslUri("https://bittrex.com/api/");
    exchangeSpecification.setExchangeSpecificParametersItem(
        "rest.v3.url", "https://api.bittrex.com/");
    exchangeSpecification.setHost("bittrex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bittrex");
    exchangeSpecification.setExchangeDescription("Bittrex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    try {
      BittrexMarketDataServiceRaw dataService =
          (BittrexMarketDataServiceRaw) this.marketDataService;
      List<BittrexSymbol> bittrexSymbols = dataService.getBittrexSymbols();
      exchangeMetaData = BittrexAdapters.adaptMetaData(bittrexSymbols, exchangeMetaData);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }
}
