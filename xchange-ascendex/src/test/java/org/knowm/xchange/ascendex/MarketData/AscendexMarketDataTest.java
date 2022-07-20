package org.knowm.xchange.ascendex.MarketData;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.ascendex.dto.marketdata.*;
import org.knowm.xchange.ascendex.service.AscendexMarketDataServiceRaw;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AscendexMarketDataTest {

  public Exchange getExchange() throws IOException{
  ExchangeSpecification exSpec = new AscendexExchange().getDefaultExchangeSpecification();
  exSpec.setSslUri("https://asdx.me/");
  exSpec.setShouldLoadRemoteMetaData(false);
  return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  // @Test
  public void testAllAsset() throws IOException{
    Exchange asdx = getExchange();
    AscendexMarketDataServiceRaw marketData = (AscendexMarketDataServiceRaw) asdx.getMarketDataService();
    //List<AscendexAssetDto> allAssets = marketData.getAllAssets();
  List<AscendexAssetDto> allAssetsV2 = marketData.getAllAssetsV2();
  Assert.assertTrue(Objects.nonNull(allAssetsV2));
  List<AscendexAssetDto> collect = allAssetsV2.stream().filter(asset -> {
    return "BTC".equals(asset.getAssetCode());
  }).collect(Collectors.toList());
  assertThat(collect.size()).isEqualTo(1);
  assertThat(collect.get(0).getBlockChain().size()).isEqualTo(1);
  System.out.println();
  }

  // @Test
  public void testAllProducts() throws IOException{
    Exchange asdx = getExchange();
    // allProducts
    AscendexMarketDataServiceRaw marketData = (AscendexMarketDataServiceRaw) asdx.getMarketDataService();
    List<AscendexProductDto> allProducts = marketData.getAllProducts();
    List<AscendexProductDto> collect = allProducts.stream().filter(asset -> {
      return "BTC/USDT".equals(asset.getSymbol());
    }).collect(Collectors.toList());
    assertThat(collect.size()).isEqualTo(1);
    assertThat(collect.get(0).getBaseAsset()).isEqualTo("BTC");
    assertThat(collect.get(0).getSymbol()).isEqualTo("BTC/USDT");

    // according to margin
    List<AscendexProductKindDto> marginProducts = marketData.getAllProducts(AccountCategory.margin);
    List<AscendexProductKindDto> margins = marginProducts.stream().filter(asset -> {
      return "BTC/USDT".equals(asset.getSymbol());
    }).collect(Collectors.toList());
    assertThat(margins.size()).isEqualTo(1);
    assertThat(margins.get(0).isUseLot()).isEqualTo(false);
    assertThat(margins.get(0).getSymbol()).isEqualTo("BTC/USDT");

  }
  // @Test
  public void testTicker()throws IOException{
    Exchange asdx = getExchange();
    AscendexMarketDataServiceRaw marketData = (AscendexMarketDataServiceRaw) asdx.getMarketDataService();
    AscendexTickerDto ticker = marketData.getTicker("ASD/USDT");
    Assert.assertTrue(Objects.nonNull(ticker));
    assertThat(ticker.getSymbol()).isEqualTo("ASD/USDT");
    assertThat(ticker.getAsk().size()).isEqualTo(2);
  }
  public static void main(String[] args) {
   }


  // @Test
  public void testBarHist() throws IOException {
    Exchange asdx = getExchange();
    AscendexMarketDataServiceRaw marketData = (AscendexMarketDataServiceRaw) asdx.getMarketDataService();
    List<AscendexBarHistDto> barHistDtos = marketData.getBarHistoryData("BTC/USDT", "15", null, null, 100);
  /*
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(AscendexExchange.class.getCanonicalName());
    exchange.remoteInit();

    List<AscendexBarHistDto> barHistDtos =
        ((AscendexMarketDataService) exchange.getMarketDataService())
            .getBarHistoryData("BTC/USDT", "15", null, null, 100);*/
    Assert.assertTrue(Objects.nonNull(barHistDtos) && !barHistDtos.isEmpty());
  }
  // @Test
  public void getOrderbookDepth() throws IOException {
    Exchange asdx = getExchange();
    AscendexMarketDataServiceRaw marketData = (AscendexMarketDataServiceRaw) asdx.getMarketDataService();
    AscendexOrderbookDto orderbook = marketData.getAscendexOrderbook("ASD/USDT");

    Assert.assertTrue(Objects.nonNull(orderbook) && !orderbook.getData().getAsks().isEmpty());
  }

  // @Test
  public void getMarketTrades() throws IOException {
    Exchange asdx = getExchange();
    AscendexMarketDataServiceRaw marketData = (AscendexMarketDataServiceRaw) asdx.getMarketDataService();
    AscendexMarketTradesDto ascendexTrades = marketData.getAscendexTrades("ASD/USDT",20);
    Assert.assertTrue(Objects.nonNull(ascendexTrades) && ascendexTrades.getData().size()==20);
  }
}
