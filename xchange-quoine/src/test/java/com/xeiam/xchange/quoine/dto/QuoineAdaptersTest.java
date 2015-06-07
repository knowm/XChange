package com.xeiam.xchange.quoine.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.quoine.QuoineAdapters;
import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;
import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfoJSONTest;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineOrderBook;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineOrderBookJSONTest;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineProduct;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineTickerJSONTest;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrdersList;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrdersListJSONTest;

public class QuoineAdaptersTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = QuoineTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineProduct quoineTicker = mapper.readValue(is, QuoineProduct.class);
    Ticker ticker = QuoineAdapters.adaptTicker(quoineTicker, CurrencyPair.BTC_USD);

    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("227.09383"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("226.78383"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("227.38976"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("0.16"));
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = QuoineOrdersListJSONTest.class.getResourceAsStream("/trade/example-orders-list.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineOrdersList quoineOrdersList = mapper.readValue(is, QuoineOrdersList.class);
    OpenOrders openOrders = QuoineAdapters.adapteOpenOrders(quoineOrdersList);

    // Verify that the example data was unmarshalled correctly
    assertThat(openOrders.getOpenOrders().size()).isEqualTo(6);
    assertThat(openOrders.getOpenOrders().get(0).getId()).isEqualTo("52362");
    assertThat(openOrders.getOpenOrders().get(0).getLimitPrice()).isEqualTo(new BigDecimal("250.0"));
    assertThat(openOrders.getOpenOrders().get(0).getTimestamp()).isEqualTo(new Date(1429953404000L));
  }

  @Test
  public void testAdaptOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = QuoineOrderBookJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineOrderBook quoineOrderBook = mapper.readValue(is, QuoineOrderBook.class);

    OrderBook orderBook = QuoineAdapters.adaptOrderBook(quoineOrderBook, CurrencyPair.BTC_USD);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getAsks().size()).isEqualTo(20);
    assertThat(orderBook.getBids().size()).isEqualTo(20);
    assertThat(orderBook.getBids().get(0).getId()).isEqualTo("");
    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("226.69718"));
  }

  @Test
  public void testAdaptAccountinfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = QuoineAccountInfoJSONTest.class.getResourceAsStream("/account/example-account-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineAccountInfo quoineAccountInfo = mapper.readValue(is, QuoineAccountInfo.class);

    AccountInfo accountInfo = QuoineAdapters.adaptAccountinfo(quoineAccountInfo);

    // Verify that the example data was unmarshalled correctly
    assertThat(accountInfo.getWallets().size()).isEqualTo(6);
    System.out.println(accountInfo.getWallet("JPY").toString());
    assertThat(accountInfo.getWallet("JPY").getCurrency()).isEqualTo("JPY");
    assertThat(accountInfo.getWallet("JPY").getBalance()).isEqualTo(new BigDecimal("12546.36144"));
  }

}
