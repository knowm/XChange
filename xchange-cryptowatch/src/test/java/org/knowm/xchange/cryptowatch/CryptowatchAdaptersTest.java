package org.knowm.xchange.cryptowatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOrderBook;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchTrade;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchAssetPairsResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchAssetsResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOrderBookResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchTradesResult;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class CryptowatchAdaptersTest {

  @Test
  public void adaptToExchangeMetaData() throws Exception {
    List<CryptowatchAssetPair> cryptowatchAssetPairs =
        TestUtils.getObject(
                "/org/knowm/xchange/cryptowatch/marketdata/example-pairs-results.json",
                CryptowatchAssetPairsResult.class)
            .getResult();
    List<CryptowatchAsset> cryptowatchAssets =
        TestUtils.getObject(
                "/org/knowm/xchange/cryptowatch/marketdata/example-assets-results.json",
                CryptowatchAssetsResult.class)
            .getResult();
    ExchangeMetaData originalExchangeMetaData =
        new ExchangeMetaData(new HashMap<>(), new HashMap<>(), null, null, null);
    ExchangeMetaData metaData =
        CryptowatchAdapters.adaptToExchangeMetaData(
            originalExchangeMetaData, cryptowatchAssetPairs, cryptowatchAssets);
    assertNotNull(metaData);
    assertEquals(2328, metaData.getCurrencyPairs().size());
    assertEquals(725, metaData.getCurrencies().size());
  }

  @Test
  public void adaptToCurrencyPair1STBTC() throws IOException {
    List<CryptowatchAssetPair> cryptowatchAssetPairs =
        TestUtils.getObject(
                "/org/knowm/xchange/cryptowatch/marketdata/example-pairs-results.json",
                CryptowatchAssetPairsResult.class)
            .getResult();
    CryptowatchAssetPair assetPair =
        cryptowatchAssetPairs.stream()
            .filter(pair -> pair.getSymbol().equals("1stbtc"))
            .findFirst()
            .orElse(null);
    CurrencyPair currencyPair = CryptowatchAdapters.adaptToCurrencyPair(assetPair);
    assertNotNull(currencyPair);
    assertEquals("1ST", currencyPair.base.getCurrencyCode());
    assertEquals("BTC", currencyPair.counter.getCurrencyCode());
  }

  @Test
  public void adaptToCurrencyPairBTCEUR() throws IOException {
    List<CryptowatchAssetPair> cryptowatchAssetPairs =
        TestUtils.getObject(
                "/org/knowm/xchange/cryptowatch/marketdata/example-pairs-results.json",
                CryptowatchAssetPairsResult.class)
            .getResult();
    CryptowatchAssetPair assetPair =
        cryptowatchAssetPairs.stream()
            .filter(pair -> pair.getSymbol().equals("btceur"))
            .findFirst()
            .orElse(null);
    CurrencyPair currencyPair = CryptowatchAdapters.adaptToCurrencyPair(assetPair);
    assertNotNull(currencyPair);
    assertEquals("BTC", currencyPair.base.getCurrencyCode());
    assertEquals("EUR", currencyPair.counter.getCurrencyCode());
  }

  @Test
  public void adaptToTicker() {}

  @Test
  public void adaptToOrderbook() throws IOException {
    CryptowatchOrderBook cryptowatchOrderBook =
        TestUtils.getObject(
                "/org/knowm/xchange/cryptowatch/marketdata/example-orderbook-results.json",
                CryptowatchOrderBookResult.class)
            .getResult();
    OrderBook orderBook =
        CryptowatchAdapters.adaptToOrderbook(cryptowatchOrderBook, CurrencyPair.BTC_EUR);
    assertNotNull(orderBook);
  }

  @Test
  public void adaptToTrades() throws IOException {
    List<CryptowatchTrade> cryptowatchTrades =
        TestUtils.getObject(
                "/org/knowm/xchange/cryptowatch/marketdata/example-trades-results.json",
                CryptowatchTradesResult.class)
            .getResult();
    Trades trades = CryptowatchAdapters.adaptToTrades(cryptowatchTrades, CurrencyPair.BTC_EUR);
    assertNotNull(trades);
  }
}
