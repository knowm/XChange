package org.knowm.xchange.bitmex;

import com.google.common.collect.BiMap;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.service.BitmexAccountService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataServiceRaw;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.utils.nonce.ExpirationTimeFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitmexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new ExpirationTimeFactory(30);

  protected RateLimitUpdateListener rateLimitUpdateListener;

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox").equals(true)) {
        exchangeSpecification.setSslUri("https://testnet.bitmex.com/");
        exchangeSpecification.setHost("testnet.bitmex.com");
      }
    }
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);

    this.marketDataService = new BitmexMarketDataService(this);
    this.accountService = new BitmexAccountService(this);
    this.tradeService = new BitmexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitmex.com/");
    exchangeSpecification.setHost("bitmex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitmex");
    exchangeSpecification.setExchangeDescription("Bitmex is a bitcoin exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", false);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    List<BitmexTicker> tickers =
        ((BitmexMarketDataServiceRaw) marketDataService).getActiveTickers();
    BiMap<BitmexPrompt, String> contracts =
        ((BitmexMarketDataServiceRaw) marketDataService).getActivePrompts(tickers);
    exchangeMetaData = BitmexAdapters.adaptToExchangeMetaData(exchangeMetaData, tickers, contracts);
  }

  public RateLimitUpdateListener getRateLimitUpdateListener() {
    return rateLimitUpdateListener;
  }

  public void setRateLimitUpdateListener(RateLimitUpdateListener rateLimitUpdateListener) {
    this.rateLimitUpdateListener = rateLimitUpdateListener;
  }
}
