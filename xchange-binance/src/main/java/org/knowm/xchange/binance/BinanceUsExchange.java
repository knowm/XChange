package org.knowm.xchange.binance;

import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import java.io.IOException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.binance.service.BinanceUsAccountService;
import org.knowm.xchange.utils.AuthUtils;

public class BinanceUsExchange extends BinanceExchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://api.binance.us");
    spec.setHost("www.binance.us");
    spec.setPort(80);
    spec.setExchangeName("Binance US");
    spec.setExchangeDescription("Binance US Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binanceus");
    return spec;
  }

  @Override
  protected void initServices() {
    this.timestampFactory =
        new BinanceTimestampFactory(
            getExchangeSpecification().getResilience(), getResilienceRegistries());
    this.marketDataService = new BinanceMarketDataService(this, getResilienceRegistries());
    this.tradeService = new BinanceTradeService(this, getResilienceRegistries());
    this.accountService = new BinanceUsAccountService(this, getResilienceRegistries());
  }

  @Override
  public void remoteInit() {
    BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
    try {
      exchangeMetaData =
          BinanceAdapters.adaptExchangeMetaData(marketDataService.getExchangeInfo(), null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
