package org.knowm.xchange.binance;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.binance.service.BinanceUsAccountService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
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
    this.binance =
        ExchangeRestProxyBuilder.forInterface(
                BinanceAuthenticated.class, getExchangeSpecification())
            .build();
    this.timestampFactory =
        new BinanceTimestampFactory(
            binance, getExchangeSpecification().getResilience(), getResilienceRegistries());
    this.marketDataService = new BinanceMarketDataService(this, binance, getResilienceRegistries());
    this.tradeService = new BinanceTradeService(this, binance, getResilienceRegistries());
    this.accountService = new BinanceUsAccountService(this, binance, getResilienceRegistries());
  }

  @Override
  public void remoteInit() {
    BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
    try {
      exchangeInfo = marketDataService.getExchangeInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Map<String, AssetDetail> assetDetailMap = null;

    postInit(assetDetailMap);
  }
}
