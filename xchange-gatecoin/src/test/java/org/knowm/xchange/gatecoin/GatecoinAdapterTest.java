package org.knowm.xchange.gatecoin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;

/** Tests the GatecoinAdapter class */
public class GatecoinAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GatecoinAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/account/example-accountinfo-data.json");

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
    InputStream is =
        GatecoinAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTransactionResult transactions = mapper.readValue(is, GatecoinTransactionResult.class);

    Trades trades =
        GatecoinAdapters.adaptTrades(transactions.getTransactions(), CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(2);
    assertThat(trades.getlastID()).isEqualTo(1392484);

    assertThat(trades.getTrades().get(0).getId()).isEqualTo("1392449");
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("229.39");
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GatecoinAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/marketdata/example-ticker-data.json");

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
    InputStream is =
        GatecoinAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/trade/example-user-trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTradeHistoryResult gatecointradeHistory =
        mapper.readValue(is, GatecoinTradeHistoryResult.class);

    UserTrades userTradeHistory = GatecoinAdapters.adaptTradeHistory(gatecointradeHistory);

    assertThat(userTradeHistory.getUserTrades().get(0).getId()).isEqualTo("24579");
    assertThat(userTradeHistory.getUserTrades().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(userTradeHistory.getUserTrades().get(0).getPrice().toString()).isEqualTo("150");
    assertThat(userTradeHistory.getUserTrades().get(0).getFeeAmount().doubleValue())
        .isEqualTo(0.0015 * 0.222 * 150, offset(0.0001));
  }
}
