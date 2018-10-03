package org.knowm.xchange.quoine.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.quoine.QuoineAdapters;
import org.knowm.xchange.quoine.dto.account.QuoineAccountInfo;
import org.knowm.xchange.quoine.dto.account.QuoineWalletJSONTest;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBook;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBookJSONTest;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import org.knowm.xchange.quoine.dto.marketdata.QuoineTickerJSONTest;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersListJSONTest;

public class QuoineAdaptersTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        QuoineTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineProduct quoineTicker = mapper.readValue(is, QuoineProduct.class);
    Ticker ticker = QuoineAdapters.adaptTicker(quoineTicker, CurrencyPair.BTC_USD);

    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("227.09383"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("226.78383"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("227.38586"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("0.16"));
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        QuoineOrdersListJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/trade/example-orders-list.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineOrdersList quoineOrdersList = mapper.readValue(is, QuoineOrdersList.class);
    OpenOrders openOrders = QuoineAdapters.adapteOpenOrders(quoineOrdersList);

    // Verify that the example data was unmarshalled correctly
    assertThat(openOrders.getOpenOrders().size()).isEqualTo(6);
    assertThat(openOrders.getOpenOrders().get(0).getId()).isEqualTo("52362");
    assertThat(openOrders.getOpenOrders().get(0).getLimitPrice())
        .isEqualTo(new BigDecimal("250.0"));
    assertThat(openOrders.getOpenOrders().get(0).getTimestamp())
        .isEqualTo(new Date(1429953404000L));
  }

  @Test
  public void testAdaptOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        QuoineOrderBookJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/marketdata/example-depth-data.json");

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
    InputStream is =
        QuoineWalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/account/example-account-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineAccountInfo quoineWallet = mapper.readValue(is, QuoineAccountInfo.class);

    Wallet wallet = QuoineAdapters.adaptWallet(quoineWallet);

    // Verify that the example data was unmarshalled correctly
    assertThat(wallet.getBalances()).hasSize(6);
    System.out.println(wallet.getBalance(Currency.JPY).toString());
    assertThat(wallet.getBalance(Currency.JPY).getCurrency()).isEqualTo(Currency.JPY);
    assertThat(wallet.getBalance(Currency.JPY).getTotal()).isEqualTo(new BigDecimal("12546.36144"));
  }
}
