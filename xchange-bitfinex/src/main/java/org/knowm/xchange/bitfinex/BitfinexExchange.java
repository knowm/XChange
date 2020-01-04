package org.knowm.xchange.bitfinex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.service.BitfinexAccountService;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.bitfinex.service.BitfinexMarketDataService;
import org.knowm.xchange.bitfinex.service.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.bitfinex.service.BitfinexTradeService;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexAccountFeesResponse;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexSymbolDetail;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexAccountInfosResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitfinexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BitfinexMarketDataService(this);
    this.accountService = new BitfinexAccountService(this);
    this.tradeService = new BitfinexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitfinex.com/");
    exchangeSpecification.setHost("api.bitfinex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitFinex");
    exchangeSpecification.setExchangeDescription("BitFinex is a bitcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    try {
      BitfinexMarketDataServiceRaw dataService =
          (BitfinexMarketDataServiceRaw) this.marketDataService;
      List<CurrencyPair> currencyPairs = dataService.getExchangeSymbols();
      exchangeMetaData = BitfinexAdapters.adaptMetaData(currencyPairs, exchangeMetaData);

      // Get the last-price of each pair. It is needed to infer XChange's priceScale out of
      // Bitfinex's pricePercision

      Map<CurrencyPair, BigDecimal> lastPrices =
          Arrays.stream(dataService.getBitfinexTickers(null))
              .map(BitfinexAdapters::adaptTicker)
              .collect(Collectors.toMap(t -> t.getCurrencyPair(), t -> t.getLast()));

      final List<BitfinexSymbolDetail> symbolDetails = dataService.getSymbolDetails();
      exchangeMetaData =
          BitfinexAdapters.adaptMetaData(exchangeMetaData, symbolDetails, lastPrices);

      if (exchangeSpecification.getApiKey() != null
          && exchangeSpecification.getSecretKey() != null) {
        // Additional remoteInit configuration for authenticated instances
        BitfinexAccountService accountService = (BitfinexAccountService) this.accountService;
        final BitfinexAccountFeesResponse accountFees = accountService.getAccountFees();
        exchangeMetaData = BitfinexAdapters.adaptMetaData(accountFees, exchangeMetaData);

        BitfinexTradeService tradeService = (BitfinexTradeService) this.tradeService;
        final BitfinexAccountInfosResponse[] bitfinexAccountInfos =
            tradeService.getBitfinexAccountInfos();

        exchangeMetaData = BitfinexAdapters.adaptMetaData(bitfinexAccountInfos, exchangeMetaData);
      }
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }
}
