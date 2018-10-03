package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcMetaData;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.service.HitbtcAccountService;
import org.knowm.xchange.hitbtc.v2.service.HitbtcMarketDataService;
import org.knowm.xchange.hitbtc.v2.service.HitbtcMarketDataServiceRaw;
import org.knowm.xchange.hitbtc.v2.service.HitbtcTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class HitbtcExchange extends BaseExchange implements org.knowm.xchange.Exchange {

  private static final Logger LOGGER = LoggerFactory.getLogger(HitbtcExchange.class);

  static {
    setupPatchSupport();
  }

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  private HitbtcMetaData hitbtcMetaData;

  private static void setupPatchSupport() {

    try {
      Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
      methodsField.setAccessible(true);
      // get the methods field modifiers
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      // bypass the "private" modifier
      modifiersField.setAccessible(true);

      // remove the "final" modifier
      modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

      /* valid HTTP methods */
      String[] methods = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"};
      // set the new methods - including patch
      methodsField.set(null, methods);
    } catch (SecurityException
        | IllegalArgumentException
        | IllegalAccessException
        | NoSuchFieldException e) {
      LOGGER.error("Error while setting up PATCH support");
    }
  }

  @Override
  protected void initServices() {

    marketDataService = new HitbtcMarketDataService(this);
    tradeService = new HitbtcTradeService(this);
    accountService = new HitbtcAccountService(this);
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {

    hitbtcMetaData = loadMetaData(is, HitbtcMetaData.class);
    exchangeMetaData =
        HitbtcAdapters.adaptToExchangeMetaData(
            null, hitbtcMetaData.getCurrencies(), hitbtcMetaData.getCurrencyPairs());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.hitbtc.com");
    exchangeSpecification.setHost("hitbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Hitbtc");
    exchangeSpecification.setExchangeDescription("Hitbtc is a Bitcoin exchange.");
    exchangeSpecification.setExchangeSpecificParametersItem(
        "demo-api", "http://demo-api.hitbtc.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
    List<HitbtcSymbol> hitbtcSymbols =
        ((HitbtcMarketDataServiceRaw) marketDataService).getHitbtcSymbols();
    exchangeMetaData =
        HitbtcAdapters.adaptToExchangeMetaData(
            hitbtcSymbols, hitbtcMetaData.getCurrencies(), hitbtcMetaData.getCurrencyPairs());
  }
}
