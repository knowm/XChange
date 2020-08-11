package org.knowm.xchange.cryptowatch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptowatch.CryptowatchExchange;
import org.knowm.xchange.cryptowatch.dto.CryptowatchException;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOHLCs;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchPrice;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchSummary;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;

public class CryptowatchMarketDataServiceRawIntegration {
  private CryptowatchMarketDataServiceRaw marketDataService;

  @Before
  public void setUp() {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(CryptowatchExchange.class.getName());
    marketDataService = (CryptowatchMarketDataServiceRaw) exchange.getMarketDataService();
  }

  @Test
  public void getCryptowatchAssetPairs() {
    List<CryptowatchAssetPair> assetPairs = marketDataService.getCryptowatchAssetPairs();
    assertThat(assetPairs).isNotNull().isNotEmpty();
  }

  @Test
  public void getCryptowatchAssets() {
    List<CryptowatchAsset> assets = marketDataService.getCryptowatchAssets();
    assertThat(assets).isNotNull().isNotEmpty();
  }

  @Test
  public void getCryptowatchPrice() {
    CryptowatchPrice price = marketDataService.getCryptowatchPrice(CurrencyPair.BTC_EUR, "kraken");
    assertThat(price).isNotNull();
    assertThat(price.getPrice()).isPositive();
  }

  @Test
  public void getCryptowatchPriceCurrencyPairError() {
    Throwable throwable =
        catchThrowable(
            () ->
                marketDataService.getCryptowatchPrice(
                    new CurrencyPair("UNKNOWN", "EUR"), "kraken"));
    assertThat(throwable).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }

  @Test
  public void getCryptowatchPriceMarketError() {
    Throwable throwable =
        catchThrowable(
            () -> marketDataService.getCryptowatchPrice(new CurrencyPair("BTC", "EUR"), "UNKNOWN"));
    assertThat(throwable).isExactlyInstanceOf(ExchangeUnavailableException.class);
  }

  @Test
  public void getCryptowatchSummary() {
    CryptowatchSummary summary =
        marketDataService.getCryptowatchSummary(CurrencyPair.BTC_EUR, "kraken");
    assertThat(summary).isNotNull();
    assertThat(summary.getPrice()).isNotNull();
    assertThat(summary.getPrice().getHigh()).isPositive();
    assertThat(summary.getPrice().getLow()).isPositive();
    assertThat(summary.getPrice().getLast()).isPositive();
    assertThat(summary.getVolume()).isPositive();
    assertThat(summary.getVolumeQuote()).isPositive();
  }

  @Test
  public void getCryptowatchOHLCs1800() {
    CryptowatchOHLCs chart =
        marketDataService.getCryptowatchOHLCs(CurrencyPair.BTC_EUR, "kraken", null, null, 1800);
    assertThat(chart).isNotNull();
    assertThat(chart.getOHLCs()).isNotNull().isNotEmpty();
    assertThat(chart.getOHLCs().get("1800")).isNotNull().isNotEmpty();
  }

  @Test
  public void getCryptowatchOHLCs60() {
    CryptowatchOHLCs chart =
        marketDataService.getCryptowatchOHLCs(CurrencyPair.BTC_EUR, "kraken", null, null, 60);
    assertThat(chart).isNotNull();
    assertThat(chart.getOHLCs()).isNotNull().isNotEmpty();
    assertThat(chart.getOHLCs().get("60")).isNotNull().isNotEmpty();
  }

  @Test
  public void getCryptowatchOHLCsPeriodError() {
    Throwable throwable =
        catchThrowable(
            () ->
                marketDataService.getCryptowatchOHLCs(
                    CurrencyPair.BTC_EUR, "kraken", null, null, 51));
    assertThat(throwable).isExactlyInstanceOf(CryptowatchException.class);
    assertThat(throwable.getMessage()).isEqualTo("Period \"51\" is invalid");
  }
}
