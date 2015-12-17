package com.xeiam.xchange.bitmarket;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.xeiam.xchange.bitmarket.dto.BitMarketDtoTestSupport;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;

/**
 * @author kfonal
 */
public class BitMarketAdaptersTest extends BitMarketDtoTestSupport {
  @Test
  public void testAccountInfoAdapter() throws IOException {
    // given
    BitMarketAccountInfoResponse response = parse("account/example-info-data", BitMarketAccountInfoResponse.class);

    // when
    Wallet wallet = BitMarketAdapters.adaptWallet(response.getData().getBalance());

    // then
    assertThat(wallet.getBalance(Currency.PLN).getCurrency()).isEqualTo(Currency.PLN);
    assertThat(wallet.getBalance(Currency.PLN).getAvailable().toString()).isEqualTo("4.166000000000");
    assertThat(wallet.getBalance(Currency.BTC).getCurrency()).isEqualTo(Currency.BTC);
    assertThat(wallet.getBalance(Currency.BTC).getTotal().toString()).isEqualTo("0.029140000000");
    assertThat(wallet.getBalance(Currency.BTC).getAvailable().toString()).isEqualTo("0.029140000000");
    assertThat(wallet.getBalance(Currency.BTC).getFrozen().toString()).isEqualTo("0");
    assertThat(wallet.getBalance(Currency.LTC).getCurrency()).isEqualTo(Currency.LTC);

    assertThat(wallet.toString()).contains("Balance [currency=LTC, total=10.390000000000, available=10.301000000000, frozen=0.089, borrowed=0, loaned=0, withdrawing=0, depositing=0]");
    assertThat(wallet.toString()).contains("Balance [currency=BTC, total=0.029140000000, available=0.029140000000, frozen=0, borrowed=0, loaned=0, withdrawing=0, depositing=0]");
    assertThat(wallet.toString()).contains("Balance [currency=PLN, total=63.566000000000, available=4.166000000000, frozen=59.4, borrowed=0, loaned=0, withdrawing=0, depositing=0]");
  }

  @Test
  public void testOpenOrdersAdapter() throws IOException {

    // given
    BitMarketOrdersResponse response = parse("trade/example-orders-data", BitMarketOrdersResponse.class);

    // when
    OpenOrders orders = BitMarketAdapters.adaptOpenOrders(response.getData());

    // then
    assertThat(orders.getOpenOrders().size()).isEqualTo(2);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("31393");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice()).isEqualTo(new BigDecimal("3000.0000"));
    assertThat(orders.getOpenOrders().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(orders.getOpenOrders().get(1).getTradableAmount()).isEqualTo(new BigDecimal("0.08000000"));

    assertThat(orders.toString()).contains(
        String.format("[order=LimitOrder [limitPrice=3000.0000, Order [type=ASK, tradableAmount=0.20000000, currencyPair=BTC/PLN, id=31393, timestamp=%s]]]", new Date(1432661682000L)));
    assertThat(orders.toString()).contains(
        String.format("[order=LimitOrder [limitPrice=4140.0000, Order [type=BID, tradableAmount=0.08000000, currencyPair=BTC/PLN, id=31391, timestamp=%s]]]", new Date(1432551696000L)));
  }

  @Test
  public void testTradeHistoryAdapter() throws IOException {

    // given
    BitMarketHistoryTradesResponse historyTradesResponse = parse("trade/example-history-trades-data", BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsResponse = parse("trade/example-history-operations-data", BitMarketHistoryOperationsResponse.class);

    // when
    UserTrades trades = BitMarketAdapters.adaptTradeHistory(historyTradesResponse.getData(), marketHistoryOperationsResponse.getData());

    // then
    assertThat(trades.getUserTrades().size()).isEqualTo(5);

    UserTrade trade = trades.getUserTrades().get(4);

    assertThat(trade.getTradableAmount()).isEqualTo(new BigDecimal("1.08260046"));
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("877.0000"));
    assertThat(trade.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(trade.getId()).isEqualTo("389406");
    assertThat(trade.getFeeAmount()).isEqualTo(new BigDecimal("0.30312011"));
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.PLN);

    assertThat(trade.toString()).isEqualTo(String.format("UserTrade[type=BID, tradableAmount=1.08260046, currencyPair=BTC/PLN, price=877.0000, "
        + "timestamp=%s, id=389406, orderId='11852566', feeAmount=0.30312011, feeCurrency='PLN']", new Date(1430687948000L)));
  }
}
