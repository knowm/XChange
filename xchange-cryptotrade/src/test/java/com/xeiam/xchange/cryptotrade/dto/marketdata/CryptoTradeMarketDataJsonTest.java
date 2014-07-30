package com.xeiam.xchange.cryptotrade.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;

public class CryptoTradeMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeTicker ticker = mapper.readValue(is, CryptoTradeTicker.class);

    assertThat(ticker.getLast()).isEqualTo("128");
    assertThat(ticker.getLow()).isEqualTo("127.9999");
    assertThat(ticker.getHigh()).isEqualTo("129.1");
    assertThat(ticker.getVolumeTradeCurrency()).isEqualTo("5.4");
    assertThat(ticker.getVolumePriceCurrency()).isEqualTo("693.8199");
    assertThat(ticker.getMinAsk()).isEqualTo("129.1");
    assertThat(ticker.getMaxBid()).isEqualTo("128");

    assertThat(ticker.getStatus()).isEqualTo("success");
    assertThat(ticker.getError()).isEmpty();
  }

  @Test
  public void testDeserializeDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeDepth depth = mapper.readValue(is, CryptoTradeDepth.class);

    List<CryptoTradePublicOrder> asks = depth.getAsks();
    assertThat(asks.size()).isEqualTo(3);

    CryptoTradePublicOrder ask = asks.get(0);
    assertThat(ask.getPrice()).isEqualTo("102");
    assertThat(ask.getAmount()).isEqualTo("0.81718312");
  }

  @Test
  public void testDeserializePublicTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePublicTrades trades = mapper.readValue(is, CryptoTradePublicTrades.class);

    List<CryptoTradePublicTrade> tradeList = trades.getPublicTrades();
    assertThat(tradeList).hasSize(2);

    CryptoTradePublicTrade trade = tradeList.get(0);
    assertThat(trade.getId()).isEqualTo(399328);
    assertThat(trade.getTimestamp()).isEqualTo(1405856801);
    assertThat(trade.getAssetPair()).isEqualTo("btc_usd");
    assertThat(trade.getType()).isEqualTo(CryptoTradeOrderType.Sell);
    assertThat(trade.getOrderAmount()).isEqualTo("0.08540439");
    assertThat(trade.getRate()).isEqualTo("618.19");
  }
}
