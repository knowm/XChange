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

/** @author Benedikt Bünz */
public class KrakenExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new KrakenMarketDataService(this);
    this.tradeService = new KrakenTradeService(this);
    this.accountService = new KrakenAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.kraken.com");
    exchangeSpecification.setHost("api.kraken.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kraken");
    exchangeSpecification.setExchangeDescription(
        "Kraken is a Bitcoin exchange operated by Payward, Inc.");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException {
    KrakenAssetPairs assetPairs =
        ((KrakenMarketDataServiceRaw) marketDataService).getKrakenAssetPairs();
    KrakenAssets assets = ((KrakenMarketDataServiceRaw) marketDataService).getKrakenAssets();
    KrakenUtils.clearAssets();

    // Note: CurrencyPair Metadata will not contain accurate maker/taker fees
    // Note: Currency Metadata will only contain price scale
    exchangeMetaData =
        KrakenAdapters.adaptToExchangeMetaData(
            exchangeMetaData, assetPairs.getAssetPairMap(), assets.getAssetPairMap());
  }
}
