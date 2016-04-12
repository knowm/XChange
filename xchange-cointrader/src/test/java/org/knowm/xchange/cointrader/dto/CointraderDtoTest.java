package org.knowm.xchange.cointrader.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.cointrader.CointraderAdapters;
import org.knowm.xchange.cointrader.dto.account.CointraderBalanceResponse;
import org.knowm.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrder;
import org.knowm.xchange.cointrader.dto.trade.CointraderTradeHistoryResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.UserTrade;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class CointraderDtoTest {

  private static ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testOrderBook() throws Exception {
    final CointraderOrderBook response = parse("orderbook.json", CointraderOrderBook.class);
    assertEquals(new Integer(43), response.getTotalCount());
    assertEquals(43, response.getData().size());

    CointraderOrderBook.Entry entry = response.getData().get(4);

    assertEquals(CurrencyPair.BTC_USD, entry.getCurrencyPair());
    assertEquals(new BigDecimal("666.00"), entry.getPrice());
    assertEquals(CointraderOrder.Type.Sell, entry.getType());
    assertEquals(new BigDecimal("1.22298971"), entry.getQuantity());
    assertEquals(new BigDecimal("814.51"), entry.getTotal());
    assertEquals(getDate("Tue Jan 27 16:23:07 UTC 2015"), entry.getCreated()); // "2015-01-27 16:23:07"

    OrderBook orderBook = CointraderAdapters.adaptOrderBook(response);
    assertEquals(18, orderBook.getBids().size());
    assertEquals(25, orderBook.getAsks().size());
  }

  @Test
  public void testUserTrades() throws Exception {
    final CointraderTradeHistoryResponse resp = parse("trade-history.json", CointraderTradeHistoryResponse.class);

    assertEquals(new Integer(2), resp.getTotalCount());
    assertEquals("Showing 2 Executed BTCUSD Trades", resp.getMessage());

    CointraderUserTrade[] trades = resp.getData();

    assertEquals(2, trades.length);

    assertEquals(33378, trades[0].getTradeId().longValue());
    assertEquals(43805339, trades[0].getOrderId().longValue());
    assertEquals(getDate("Tue Apr 28 08:38:25 CEST 2015"), trades[0].getExecuted());
    assertEquals(CointraderOrder.Type.Sell, trades[0].getType());
    assertEquals(new BigDecimal("0.03000000"), trades[0].getQuantity());
    assertEquals(new BigDecimal("219.53"), trades[0].getPrice());
    assertEquals(new BigDecimal("0.00015000"), trades[0].getFee());
    assertEquals(new BigDecimal("6.59"), trades[0].getTotal());

    assertTrade(trades[0], Order.OrderType.ASK, Currency.BTC, "0.00015000");
    assertTrade(trades[1], Order.OrderType.BID, Currency.USD, "0.03");
  }

  @Test
  public void testBalance() throws Exception {
    final CointraderBalanceResponse balance = parse("balance.json", CointraderBalanceResponse.class);
    assertEquals(new BigDecimal("1283.24"), balance.getData().get("USD").getAvailable());
    assertEquals(new BigDecimal("1283.24"), balance.getData().get("USD").getTotal());
    assertEquals(new BigDecimal("1.10000000"), balance.getData().get("BTC").getOnHold());
    assertEquals(new BigDecimal("1.10000000"), balance.getData().get("BTC").getTotal());
    assertEquals(new BigDecimal("0.00"), balance.getData().get("CAD").getAvailable());
    assertEquals(new BigDecimal("0.00"), balance.getData().get("CAD").getTotal());
    assertNull(balance.getData().get("CNY"));
  }

  private void assertTrade(CointraderUserTrade trade, Order.OrderType type, Currency currency, String amt) {
    UserTrade tr = CointraderAdapters.adaptTrade(trade);
    assertEquals(new BigDecimal(amt), tr.getFeeAmount());
    assertEquals(currency, tr.getFeeCurrency());
    assertEquals(type, tr.getType());
  }

  private Date getDate(String dateStr) throws ParseException {
    return new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(dateStr);
  }

  private static <E> E parse(String filename, Class<E> type) throws java.io.IOException {
    InputStream is = CointraderDtoTest.class.getResourceAsStream("/" + filename);
    return mapper.readValue(is, type);
  }
}