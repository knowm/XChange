package org.knowm.xchange.taurus;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.taurus.dto.account.TaurusBalance;
import org.knowm.xchange.taurus.dto.marketdata.TaurusOrderBook;
import org.knowm.xchange.taurus.dto.marketdata.TaurusTicker;
import org.knowm.xchange.taurus.dto.marketdata.TaurusTransaction;
import org.knowm.xchange.taurus.dto.trade.TaurusUserTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the TaurusAdapter class
 */
public class TaurusAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TaurusAdapterTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusBalance taurusBalance = mapper.readValue(is, TaurusBalance.class);

    //    {"btc_available":"0.02350921","btc_reserved":"0.00000000","btc_balance":"0.02350921", "cad_available":"6.16","cad_reserved":"0.00","cad_balance":"6.16","fee":"0.5000"}
    AccountInfo accountInfo = TaurusAdapters.adaptAccountInfo(taurusBalance, "Joe Mama");
    assertThat(accountInfo.getUsername()).isEqualTo("Joe Mama");
    assertThat(accountInfo.getTradingFee()).isEqualTo(new BigDecimal("0.5000"));
    assertThat(accountInfo.getWallet().getBalances().size()).isEqualTo(2);
    assertThat(accountInfo.getWallet().getBalance(Currency.CAD).getTotal()).isEqualTo(new BigDecimal("6.16"));
    assertThat(accountInfo.getWallet().getBalance(Currency.CAD).getAvailable()).isEqualTo(new BigDecimal("6.16"));
    assertThat(accountInfo.getWallet().getBalance(Currency.CAD).getFrozen()).isEqualTo(new BigDecimal("0.00"));
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getTotal()).isEqualTo(new BigDecimal("0.02350921"));
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getAvailable()).isEqualTo(new BigDecimal("0.02350921"));
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getFrozen()).isEqualTo(new BigDecimal("0.00000000"));
  }

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TaurusAdapterTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusOrderBook taurusOrderBook = mapper.readValue(is, TaurusOrderBook.class);

    OrderBook orderBook = TaurusAdapters.adaptOrderBook(taurusOrderBook, CurrencyPair.BTC_CAD);
    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1427275444000L);

    assertThat(orderBook.getBids().size()).isEqualTo(7);
    assertThat(orderBook.getAsks().size()).isEqualTo(4);

    //    ["305.00", "1.00000000"],["302.00", "0.47300000"],
    assertThat(orderBook.getBids().get(2).getLimitPrice()).isEqualTo("305.00");
    assertThat(orderBook.getBids().get(2).getOriginalAmount()).isEqualTo("1.00000000");
    assertThat(orderBook.getBids().get(2).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getBids().get(3).getLimitPrice()).isEqualTo("302.00");
    assertThat(orderBook.getBids().get(3).getOriginalAmount()).isEqualTo("0.47300000");
    assertThat(orderBook.getBids().get(3).getType()).isEqualTo(OrderType.BID);

    //    ["318.00", "0.68500000"]
    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo("318.00");
    assertThat(orderBook.getAsks().get(0).getOriginalAmount()).isEqualTo("0.68500000");
    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(OrderType.ASK);
  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TaurusAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");
    //    [{"amount":"0.01000000","date":"1427270265","price":"310.00","tid":132}]

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusTransaction[] transactions = mapper.readValue(is, TaurusTransaction[].class);

    Trade trade = TaurusAdapters.adaptTrade(transactions[0], CurrencyPair.BTC_CAD);

    // verify all fields filled
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("310.00"));
    assertThat(trade.getType()).isNull();
    assertThat(trade.getOriginalAmount()).isEqualTo(new BigDecimal("0.01000000"));
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_CAD);
    assertThat(trade.getId()).isEqualTo("132");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1427270265000L);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TaurusAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusTicker taurusTicker = mapper.readValue(is, TaurusTicker.class);

    Ticker ticker = TaurusAdapters.adaptTicker(taurusTicker, CurrencyPair.BTC_CAD);

    // {"high":"310.00","last":"310.00","timestamp":"1427273414","bid":"310.00","vwap":"317.39","volume":"2.59493463","low":"310.00","ask":"318.00"}
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("310.00"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("310.00"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("310.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("310.00"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("318.00"));
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("317.39"));

    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("2.59493463"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1427273414000L);
  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = TaurusAdapterTest.class.getResourceAsStream("/trade/example-user-transactions.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusUserTransaction[] taurusUserTransactions = mapper.readValue(is, TaurusUserTransaction[].class);

    UserTrades uth = TaurusAdapters.adaptTradeHistory(taurusUserTransactions);

    // {"cad":"3.08","btc":"-0.01000000","datetime":"2015-03-25 07:57:45","fee":"0.02","id":132,"order_id":"e1mdwidxa4u9174l6keon1lj0er3zwrdxdrtw5r6amgbtkx05bptdbcdq4hp083y","rate":"310.00","type":2}
    final UserTrade t0 = uth.getUserTrades().get(1);
    assertThat(t0.getId()).isEqualTo("132");
    assertThat(t0.getType()).isEqualTo(OrderType.ASK);
    assertThat(t0.getPrice()).isEqualTo(new BigDecimal("310.00"));
    assertThat(t0.getFeeAmount()).isEqualTo(new BigDecimal("0.02"));
    assertThat(t0.getId()).isEqualTo("132");
    assertThat(t0.getOrderId()).isEqualTo("e1mdwidxa4u9174l6keon1lj0er3zwrdxdrtw5r6amgbtkx05bptdbcdq4hp083y");
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    format.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // The json date is UTC; Paris is UTC+1.
    assertThat(format.format(t0.getTimestamp())).isEqualTo("2015-03-25 08:57:45");

    // {"cad":"3.08","btc":"-0.01000000","datetime":"2015-03-25 06:36:33","fee":"0.02","id":131,"order_id":"kf20f4xw16wqv75gvomk7invftavb4rwkuxzyuv01sqxzwdvkzfsh3nxwys83k4n","rate":"310.00","type":2}
    final UserTrade t1 = uth.getUserTrades().get(0);
    assertThat(t1.getPrice()).isEqualTo(new BigDecimal("310.00"));
    assertThat(t1.getId()).isEqualTo("131");
    assertThat(t1.getType()).isEqualTo(OrderType.ASK);
    assertThat(t1.getFeeAmount().toString()).isEqualTo("0.02");
  }
}
