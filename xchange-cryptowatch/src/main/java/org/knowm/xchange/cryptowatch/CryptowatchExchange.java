package org.knowm.xchange.cryptowatch;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;
import org.knowm.xchange.cryptowatch.service.CryptowatchAccountService;
import org.knowm.xchange.cryptowatch.service.CryptowatchMarketDataService;
import org.knowm.xchange.cryptowatch.service.CryptowatchMarketDataServiceRaw;
import org.knowm.xchange.cryptowatch.service.CryptowatchTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author massi.gerardi */
public class CryptowatchExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CryptowatchMarketDataService(this);
    this.tradeService = new CryptowatchTradeService();
    this.accountService = new CryptowatchAccountService();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.cryptowat.ch");
    exchangeSpecification.setHost("api.cryptowat.ch");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptowatch");
    exchangeSpecification.setExchangeDescription(
        "Cryptowatch is a cryptocurrency charting and trading platform owned by Kraken");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  /**
   * as Cryptowatch does not expose an API for trading, we do not need metadata.
   *
   * @throws IOException
   */
  @Override
  public void remoteInit() throws IOException {
    List<CryptowatchAssetPair> cryptowatchAssetPairs =
        ((CryptowatchMarketDataServiceRaw) marketDataService).getCryptowatchAssetPairs();
    List<CryptowatchAsset> cryptowatchAssets =
        ((CryptowatchMarketDataServiceRaw) marketDataService).getCryptowatchAssets();
    exchangeMetaData =
        CryptowatchAdapters.adaptToExchangeMetaData(
            exchangeMetaData, cryptowatchAssetPairs, cryptowatchAssets);
  }
}
