package org.knowm.xchange.kraken;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPairs;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssets;
import org.knowm.xchange.kraken.service.KrakenAccountService;
import org.knowm.xchange.kraken.service.KrakenMarketDataService;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.kraken.service.KrakenTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Benedikt Bünz */
public class KrakenExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new KrakenMarketDataService(this);
    this.tradeService = new KrakenTradeService(this);
    this.accountService = new KrakenAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.kraken.com");
    exchangeSpecification.setHost("api.kraken.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kraken");
    exchangeSpecification.setExchangeDescription(
        "Kraken is a Bitcoin exchange operated by Payward, Inc.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    KrakenAssetPairs assetPairs =
        ((KrakenMarketDataServiceRaw) marketDataService).getKrakenAssetPairs();
    KrakenAssets assets = ((KrakenMarketDataServiceRaw) marketDataService).getKrakenAssets();
    // other endpoints?
    // hard-coded meta data from json file not available at an endpoint?
    exchangeMetaData =
        KrakenAdapters.adaptToExchangeMetaData(
            exchangeMetaData, assetPairs.getAssetPairMap(), assets.getAssetPairMap());
  }
}
