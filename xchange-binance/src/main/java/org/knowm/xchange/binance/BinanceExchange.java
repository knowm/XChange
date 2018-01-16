package org.knowm.xchange.binance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.marketdata.BinancePrice;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongCurrentTimeIncrementalNonceFactory();
  private Long deltaServerTime;
  
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
    spec.setSslUri("https://api.binance.com");
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance");
    spec.setExchangeDescription("Binance Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binance");
    return spec;
  }

  @Override
  public void remoteInit() {
    try {
      // populate currency pair keys only, exchange does not provide any other metadata for download
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
      Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

      BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
      for (BinancePrice price : marketDataService.tickerAllPrices()) {
        CurrencyPair pair = price.getCurrencyPair();
        currencyPairs.put(price.getCurrencyPair(), new CurrencyPairMetaData(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 8));

        currencies.put(pair.base, new CurrencyMetaData(8, BigDecimal.ZERO));
        currencies.put(pair.counter, new CurrencyMetaData(8, BigDecimal.ZERO));
      }
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
    }
  }

  public long deltaServerTime() throws IOException {
    if (deltaServerTime == null) {
      Binance binance = RestProxyFactory.createProxy(Binance.class, getExchangeSpecification().getSslUri());
      deltaServerTime = binance.time().getServerTime().getTime() - System.currentTimeMillis();
    }
    return deltaServerTime;
  }
}
