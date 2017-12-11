package org.knowm.xchange.binance;

import java.util.Map;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.marketdata.BinanceSymbolPrice;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongCurrentTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BinanceMarketDataService(this);
    this.tradeService = new BinanceTradeService(this);
    this.accountService = new BinanceAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://www.binance.com");
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance");
    spec.setExchangeDescription("Binance Exchange.");
    return spec;
  }

  @Override
  public void remoteInit() {
    try {
      // populate currency pair keys only, exchange does not provide any other metadata for download
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
      BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
      for (BinanceSymbolPrice price : marketDataService.tickerAllPrices()) {
        final CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(price.symbol);
        currencyPairs.put(pair, null);
      }
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
    }
  }
}
