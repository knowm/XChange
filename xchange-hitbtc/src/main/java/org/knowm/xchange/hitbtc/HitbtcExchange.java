package org.knowm.xchange.hitbtc;

import java.io.IOException;
import java.io.InputStream;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import org.knowm.xchange.hitbtc.dto.meta.HitbtcMetaData;
import org.knowm.xchange.hitbtc.service.polling.HitbtcAccountService;
import org.knowm.xchange.hitbtc.service.polling.HitbtcMarketDataService;
import org.knowm.xchange.hitbtc.service.polling.HitbtcMarketDataServiceRaw;
import org.knowm.xchange.hitbtc.service.polling.HitbtcTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class HitbtcExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  private HitbtcMetaData hitbtcMetaData;

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new HitbtcMarketDataService(this);
    this.pollingTradeService = new HitbtcTradeService(this);
    this.pollingAccountService = new HitbtcAccountService(this);
  }

  @Override
  protected void loadMetaData(InputStream is) {
    hitbtcMetaData = loadMetaData(is, HitbtcMetaData.class);
    metaData = HitbtcAdapters.adaptToExchangeMetaData(null, hitbtcMetaData);
  }

  @Override
  public void remoteInit() throws IOException {
    HitbtcSymbols hitbtcSymbols = ((HitbtcMarketDataServiceRaw) pollingMarketDataService).getHitbtcSymbols();
    metaData = HitbtcAdapters.adaptToExchangeMetaData(hitbtcSymbols, hitbtcMetaData);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.hitbtc.com");
    exchangeSpecification.setHost("hitbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setPlainTextUriStreaming("ws://api.hitbtc.com/");
    exchangeSpecification.setExchangeName("Hitbtc");
    exchangeSpecification.setExchangeDescription("Hitbtc is a Bitcoin exchange.");
    exchangeSpecification.setExchangeSpecificParametersItem("demo-api", "http://demo-api.hitbtc.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

}
