package org.knowm.xchange.bitmarket;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

/** @author kfonal */
public class BitMarketAdaptersTest extends BitMarketTestSupport {

  @Test
  public void testAccountInfoAdapter() throws IOException {
    // given
    BitMarketAccountInfoResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/account/example-info-data",
            BitMarketAccountInfoResponse.class);
    final Balance[] expectedInfoBalances = expectedInfoBalances();

    // when
    Wallet wallet = BitMarketAdapters.adaptWallet(response.getData().getBalance());

    // then
    Map<Currency, Balance> balances = wallet.getBalances();

    assertThat(balances).hasSize(3);
    for (Balance balance : expectedInfoBalances) {
      BitMarketAssert.assertEquals(balances.get(balance.getCurrency()), balance);
      assertThat(wallet.toString()).contains(balance.toString());
    }
  }

  @Test
  public void testOpenOrdersAdapter() throws IOException {
    // given
    BitMarketOrdersResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-orders-data",
            BitMarketOrdersResponse.class);
    final LimitOrder[] expectedOrders = expectedOrders();

    // when
    OpenOrders orders = BitMarketAdapters.adaptOpenOrders(response.getData());
    List<LimitOrder> openOrders = orders.getOpenOrders();

    // then
    assertThat(openOrders).hasSize(2);
    for (int i = 0; i < openOrders.size(); i++) {
      BitMarketAssert.assertEquals(openOrders.get(i), expectedOrders[i]);
      assertThat(orders.toString()).contains(expectedOrders[i].toString());
    }
  }

  @Test
  public void testTradeHistoryAdapter() throws IOException {

    // given
    BitMarketHistoryTradesResponse historyTradesResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-trades-data",
            BitMarketHistoryTradesResponse.class);
    BitMarketHistoryOperationsResponse marketHistoryOperationsResponse =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-history-operations-data",
            BitMarketHistoryOperationsResponse.class);

    final UserTrade[] expectedUserTrades = expectedUserTrades();

    // when
    UserTrades trades =
        BitMarketAdapters.adaptTradeHistory(
            historyTradesResponse.getData(), marketHistoryOperationsResponse.getData());
    List<UserTrade> userTrades = trades.getUserTrades();

    // then
    assertThat(userTrades).hasSize(5);
    for (int i = 0; i < userTrades.size(); i++) {
      BitMarketAssert.assertEquals(userTrades.get(i), expectedUserTrades[i]);
    }
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // given
    BitMarketTicker bitMarketTicker =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-ticker-data",
            BitMarketTicker.class);

    // when
    Ticker ticker = BitMarketAdapters.adaptTicker(bitMarketTicker, CurrencyPair.BTC_AUD);

    // then
    BitMarketAssert.assertEquals(ticker, TICKER);
  }

  // https://www.bitmarket.pl/json/LTCPLN/trades.json example has addition field 'type' which is not
  // specified in API description (https://www.bitmarket.net/docs.php?file=api_public.html)
  // should be changed after issue #1141 fix
  @Test
  public void testTradesAdapter() throws IOException {
    // given
    BitMarketTrade[] bitMarketTrades =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-trades-data",
            BitMarketTrade[].class);
    final Trade[] expectedTrades = expectedTrades();

    // when
    Trades trades = BitMarketAdapters.adaptTrades(bitMarketTrades, CurrencyPair.BTC_AUD);
    List<Trade> tradeList = trades.getTrades();

    // then
    assertThat(tradeList).hasSize(3);
    for (int i = 0; i < tradeList.size(); i++) {
      BitMarketAssert.assertEquals(tradeList.get(i), expectedTrades[i]);
    }
  }

  @Test
  public void testOrderBookAdapter() throws IOException {
    // given
    BitMarketOrderBook bitMarketOrderBook =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-order-book-data",
            BitMarketOrderBook.class);

    // when
    OrderBook orderBook =
        BitMarketAdapters.adaptOrderBook(bitMarketOrderBook, CurrencyPair.BTC_AUD);

    // then
    BitMarketAssert.assertEquals(orderBook, ORDER_BOOK);
  }
}
