package com.xeiam.xchange.vaultofsatoshi.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.DepthWrapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.TickerWrapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.TradesWrapper;
import com.xeiam.xchange.vaultofsatoshi.service.marketdata.VaultOfSatoshiDepthJSONTest;
import com.xeiam.xchange.vaultofsatoshi.service.marketdata.VaultOfSatoshiTickerJSONTest;
import com.xeiam.xchange.vaultofsatoshi.service.marketdata.VaultOfSatoshiTradesJSONTest;

/**
 * Tests the vosAdapter class
 */
public class VaultOfSatoshiAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DepthWrapper VaultOfSatoshiDepth = mapper.readValue(is, DepthWrapper.class);

    List<LimitOrder> asks = VaultOfSatoshiAdapters.adaptOrders(VaultOfSatoshiDepth.getDepth().getAsks(), new CurrencyPair("BTC", "USD"), "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(682.00);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(0.22000000);
    assertThat(asks.get(0).getCurrencyPair().baseSymbol).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counterSymbol).isEqualTo("USD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TradesWrapper vosTrades = mapper.readValue(is, TradesWrapper.class);

    Trades trades = VaultOfSatoshiAdapters.adaptTrades(vosTrades.getTrades(), CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(2);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice()).isEqualTo("641.17165850");
    assertThat(trades.getTrades().get(0).getTradableAmount()).isEqualTo(".25000000");
    assertThat(trades.getTrades().get(0).getCurrencyPair().baseSymbol == "BTC");
    assertThat(trades.getlastID()).isEqualTo(294649);
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2014-06-21 01:06:53 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TickerWrapper VaultOfSatoshiTicker = mapper.readValue(is, TickerWrapper.class);

    Ticker ticker = VaultOfSatoshiAdapters.adaptTicker(VaultOfSatoshiTicker.getTicker(), CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("684.00000000");
    assertThat(ticker.getLow().toString()).isEqualTo("601.00000000");
    assertThat(ticker.getHigh().toString()).isEqualTo("686.50000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("29.32450256"));
    assertThat(ticker.getCurrencyPair().baseSymbol).isEqualTo("BTC");

  }
}
