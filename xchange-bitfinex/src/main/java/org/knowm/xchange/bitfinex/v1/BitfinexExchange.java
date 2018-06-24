package org.knowm.xchange.bitfinex.v1;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexAccountFeesResponse;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexSymbolDetail;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexAccountInfosResponse;
import org.knowm.xchange.bitfinex.v1.service.BitfinexAccountService;
import org.knowm.xchange.bitfinex.v1.service.BitfinexMarketDataService;
import org.knowm.xchange.bitfinex.v1.service.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.bitfinex.v1.service.BitfinexTradeService;
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

    BitfinexMarketDataServiceRaw dataService =
        (BitfinexMarketDataServiceRaw) this.marketDataService;
    List<CurrencyPair> currencyPairs = dataService.getExchangeSymbols();
    exchangeMetaData = BitfinexAdapters.adaptMetaData(currencyPairs, exchangeMetaData);

    final List<BitfinexSymbolDetail> symbolDetails = dataService.getSymbolDetails();
    exchangeMetaData = BitfinexAdapters.adaptMetaData(exchangeMetaData, symbolDetails);

    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getSecretKey() != null) {
      // Additional remoteInit configuration for authenticated instances
      BitfinexAccountService accountService = (BitfinexAccountService) this.accountService;
      final BitfinexAccountFeesResponse accountFees = accountService.getAccountFees();
      exchangeMetaData = BitfinexAdapters.adaptMetaData(accountFees, exchangeMetaData);

      BitfinexTradeService tradeService = (BitfinexTradeService) this.tradeService;
      final BitfinexAccountInfosResponse[] bitfinexAccountInfos =
          tradeService.getBitfinexAccountInfos();

      exchangeMetaData = BitfinexAdapters.adaptMetaData(bitfinexAccountInfos, exchangeMetaData);
    }
  }
}
