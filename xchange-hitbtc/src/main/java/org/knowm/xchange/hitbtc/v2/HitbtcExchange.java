package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.dto.meta.HitbtcMetaData;
import org.knowm.xchange.hitbtc.service.HitbtcAccountService;
import org.knowm.xchange.hitbtc.service.HitbtcTradeService;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.internal.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.internal.api.HitbtcRestClient;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class HitbtcExchange extends BaseExchange implements org.knowm.xchange.Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  private HitbtcMetaData hitbtcMetaData;

  @Override
  protected void initServices() {

    marketDataService = new HitbtcMarketDataService();
    tradeService = new HitbtcTradeService(this);
    accountService = new HitbtcAccountService(this);
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {

    hitbtcMetaData = loadMetaData(is, HitbtcMetaData.class);
    exchangeMetaData = HitbtcAdapters.adaptToExchangeMetaData(null, hitbtcMetaData.getCurrencies());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.hitbtc.com");
    exchangeSpecification.setHost("hitbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Hitbtc");
    exchangeSpecification.setExchangeDescription("Hitbtc is a Bitcoin exchange.");
    exchangeSpecification.setExchangeSpecificParametersItem("demo-api", "http://demo-api.hitbtc.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    HitbtcRestClient.INSTANCE.init(this);
    List<HitbtcSymbol> hitbtcSymbols = ((HitbtcMarketDataServiceRaw) marketDataService).getHitbtcSymbols();
    exchangeMetaData = HitbtcAdapters.adaptToExchangeMetaData(hitbtcSymbols, hitbtcMetaData.getCurrencies());
  }

}
