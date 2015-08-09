package com.xeiam.xchange.hitbtc;

import java.io.IOException;
import java.io.InputStream;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.meta.HitbtcMetaData;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcAccountService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcMarketDataService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcMarketDataServiceRaw;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

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
