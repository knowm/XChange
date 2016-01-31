package com.xeiam.xchange.gatecoin;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import com.xeiam.xchange.currency.Currency;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;

/**
 * Tests the GatecoinAdapter class
 */
public class GatecoinAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GatecoinAdapterTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinBalanceResult gatecoinBalance = mapper.readValue(is, GatecoinBalanceResult.class);

    Wallet wallet = GatecoinAdapters.adaptWallet(gatecoinBalance.getBalances());
    assertThat(wallet.getBalance(Currency.BTC).getCurrency()).isEqualTo(Currency.BTC);
    assertThat(wallet.getBalance(Currency.BTC).getTotal().toString()).isEqualTo("2.94137538");
    assertThat(wallet.getBalance(Currency.BTC).getAvailable().toString()).isEqualTo("2.94137538");
    assertThat(wallet.getBalance(Currency.BTC).getFrozen().toString()).isEqualTo("0");
  }

  @Test
  public void testTradesAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GatecoinAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTransactionResult transactions = mapper.readValue(is, GatecoinTransactionResult.class);

    Trades trades = GatecoinAdapters.adaptTrades(transactions.getTransactions(), CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(2);
    assertThat(trades.getlastID()).isEqualTo(1392484);

    assertThat(trades.getTrades().get(0).getId()).isEqualTo("1392449");
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("229.39");
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GatecoinAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTickerResult gatecoinTickerResult = mapper.readValue(is, GatecoinTickerResult.class);

    GatecoinTicker[] gatecoinTicker = gatecoinTickerResult.getTicker();

    Ticker ticker = GatecoinAdapters.adaptTicker(gatecoinTicker, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("241.58");
    assertThat(ticker.getBid().toString()).isEqualTo("241.58");
    assertThat(ticker.getAsk().toString()).isEqualTo("242");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("158.12"));
  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GatecoinAdapterTest.class.getResourceAsStream("/trade/example-user-trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTradeHistoryResult gatecointradeHistory = mapper.readValue(is, GatecoinTradeHistoryResult.class);

    UserTrades userTradeHistory = GatecoinAdapters.adaptTradeHistory(gatecointradeHistory);

    assertThat(userTradeHistory.getUserTrades().get(0).getId()).isEqualTo("24579");
    assertThat(userTradeHistory.getUserTrades().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(userTradeHistory.getUserTrades().get(0).getPrice().toString()).isEqualTo("150");
    assertThat(userTradeHistory.getUserTrades().get(0).getFeeAmount().doubleValue()).isEqualTo(0.0015 * 0.222 * 150, offset(0.0001));
  }
}
