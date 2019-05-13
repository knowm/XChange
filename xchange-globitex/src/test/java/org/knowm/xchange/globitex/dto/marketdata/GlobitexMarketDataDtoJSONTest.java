package org.knowm.xchange.globitex.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class GlobitexMarketDataDtoJSONTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void globitexSymbolsTest() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/marketdata/globitex-symbols-example.json");

    GlobitexSymbols symbols = mapper.readValue(is, GlobitexSymbols.class);
    Assert.assertEquals(1, symbols.getSymbols().size());
    assertThat(symbols.getSymbols().get(0).getSymbol()).isEqualTo("XBTEUR");
    assertThat(symbols.getSymbols().get(0).getPriceIncrement()).isEqualTo("0.01");
    assertThat(symbols.getSymbols().get(0).getSizeIncrement()).isEqualTo("0.00000001");
    assertThat(symbols.getSymbols().get(0).getSizeMin()).isEqualTo("0.002");
    assertThat(symbols.getSymbols().get(0).getCurrency()).isEqualTo("EUR");
    assertThat(symbols.getSymbols().get(0).getCommodity()).isEqualTo("XBT");
  }

  @Test
  public void globitexTickerTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/marketdata/globitex-ticker-example.json");

    GlobitexTicker ticker = mapper.readValue(is, GlobitexTicker.class);

    assertThat(ticker.getSymbol()).isEqualTo("XBTEUR");
    assertThat(ticker.getAsk()).isEqualTo("554.12");
    assertThat(ticker.getBid()).isEqualTo("549.56");
    assertThat(ticker.getLast()).isEqualTo("550.73");
    assertThat(ticker.getLow()).isEqualTo("400.70");
    assertThat(ticker.getHigh()).isEqualTo("600.10");
    assertThat(ticker.getOpen()).isEqualTo("449.73");
    assertThat(ticker.getVolume()).isEqualTo("567.90");
    assertThat(ticker.getVolumeQuote()).isEqualTo("289002.81");
    assertThat(ticker.getTimestamp()).isEqualTo(1393492619000L);
  }

  @Test
  public void globitexOrderBookTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/marketdata/globitex-orderbook-example.json");

    GlobitexOrderBook orderbook = mapper.readValue(is, GlobitexOrderBook.class);
    assertThat(orderbook.getAsks().get(0).getPrice()).isEqualTo("405.71");
    assertThat(orderbook.getAsks().get(0).getVolume()).isEqualTo("0.09");
    assertThat(orderbook.getBids().get(1).getPrice()).isEqualTo("396.99");
    assertThat(orderbook.getBids().get(1).getVolume()).isEqualTo("0.13");
  }

  @Test
  public void globitexTickersTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/marketdata/globitex-tickers-example.json");

    GlobitexTickers tickers = mapper.readValue(is, GlobitexTickers.class);

    assertThat(tickers.getGlobitexTickerList().get(0).getSymbol()).isEqualTo("XBTUSD");
    assertThat(tickers.getGlobitexTickerList().get(1).getSymbol()).isEqualTo("XBTEUR");
  }

  @Test
  public void globitexTradeTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/marketdata/globitex-trade-example.json");

    GlobitexTrade trade = mapper.readValue(is, GlobitexTrade.class);

    assertThat(trade.getTimestamp()).isEqualTo(1411045690003L);
    assertThat(trade.getPrice()).isEqualTo("442.12");
    assertThat(trade.getAmount()).isEqualTo("0.09");
    assertThat(trade.getTid()).isEqualTo(1413901);
    assertThat(trade.getSide()).isEqualTo("sell");
  }

  @Test
  public void globitexTradesTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/marketdata/globitex-trades-example.json");

    GlobitexTrades trades = mapper.readValue(is, GlobitexTrades.class);

    assertThat(trades.getRecentTrades().size()).isEqualTo(10);
    assertThat(trades.getRecentTrades().get(1).getPrice()).isEqualTo("442.02");
    assertThat(trades.getRecentTrades().get(1).getTimestamp()).isEqualTo(1411045690003L);
  }
}
