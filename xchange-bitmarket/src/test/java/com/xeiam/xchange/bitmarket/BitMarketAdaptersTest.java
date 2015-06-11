package com.xeiam.xchange.bitmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTrades;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author kfonal
 */
public class BitMarketAdaptersTest {
  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketAdaptersTest.class.getResourceAsStream("/account/example-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketAccountInfoResponse response = mapper.readValue(is, BitMarketAccountInfoResponse.class);

    AccountInfo accountInfo = BitMarketAdapters.adaptAccountInfo(response.getData().getBalance(), "Jan Kowalski");
    assertThat(accountInfo.getUsername()).isEqualTo("Jan Kowalski");
    assertThat(accountInfo.getTradingFee()).isNull();
    assertThat(accountInfo.getWallet(Currencies.PLN).getCurrency()).isEqualTo("PLN");
    assertThat(accountInfo.getWallet(Currencies.PLN).getAvailable().toString()).isEqualTo("4.166000000000");
    assertThat(accountInfo.getWallet(Currencies.BTC).getCurrency()).isEqualTo("BTC");
    assertThat(accountInfo.getWallet(Currencies.BTC).getBalance().toString()).isEqualTo("0.029140000000");
    assertThat(accountInfo.getWallet(Currencies.BTC).getAvailable().toString()).isEqualTo("0.029140000000");
    assertThat(accountInfo.getWallet(Currencies.BTC).getFrozen().toString()).isEqualTo("0");
    assertThat(accountInfo.getWallet(Currencies.LTC).getCurrency()).isEqualTo("LTC");
  }

  @Test
  public void testOpenOrdersAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketAdaptersTest.class.getResourceAsStream("/trade/example-orders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketOrdersResponse response = mapper.readValue(is, BitMarketOrdersResponse.class);

    OpenOrders orders = BitMarketAdapters.adaptOpenOrders(response.getData());
    assertThat(orders.getOpenOrders().size()).isEqualTo(2);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("31393");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice()).isEqualTo(new BigDecimal("3000.0000"));
    assertThat(orders.getOpenOrders().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(orders.getOpenOrders().get(1).getTradableAmount()).isEqualTo(new BigDecimal("0.08000000"));
  }

  @Test
  public void testTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketAdaptersTest.class.getResourceAsStream("/trade/example-history-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketHistoryTradesResponse response = mapper.readValue(is, BitMarketHistoryTradesResponse.class);

    // Verify that the example data was unmarshalled correctly
    UserTrades trades = BitMarketAdapters.adaptTradeHistory(response.getData());

    assertThat(trades.getUserTrades().size()).isEqualTo(5);

    UserTrade trade = trades.getUserTrades().get(4);

    assertThat(trade.getTradableAmount()).isEqualTo(new BigDecimal("1.08260046"));
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("877.0000"));
    assertThat(trade.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(trade.getId()).isEqualTo("389406");
  }
}
