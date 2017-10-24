package org.knowm.xchange.bitcurex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitcurex DTOs to XChange DTOs
 */
public final class BitcurexAdapters {

  /**
   * private Constructor
   */
  private BitcurexAdapters() {

  }

  /**
   * Adapts a List of bitcurexOrders to a List of LimitOrders
   *
   * @param bitcurexOrders
   * @param currencyPair
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bitcurexOrders, CurrencyPair currencyPair, OrderType orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (BigDecimal[] bitcurexOrder : bitcurexOrders) {
      limitOrders.add(adaptOrder(bitcurexOrder[1], bitcurexOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a BitcurexOrder to a LimitOrder
   *
   * @param amount
   * @param price
   * @param currencyPair
   * @param orderType
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, String id) {

    // place a limit order
    BigDecimal limitPrice = price;

    return new LimitOrder(orderType, amount, currencyPair, id, null, limitPrice);
  }

  /**
   * Adapts a BitcurexTrade to a Trade Object
   *
   * @param bitcurexTrade A Bitcurex trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BitcurexTrade bitcurexTrade, CurrencyPair currencyPair) {

    BigDecimal amount = bitcurexTrade.getAmount();
    BigDecimal price = bitcurexTrade.getPrice();
    Date date = DateUtils.fromMillisUtc(bitcurexTrade.getDate() * 1000L);
    final String tradeId = String.valueOf(bitcurexTrade.getTid());

    return new Trade(bitcurexTrade.getType() == 1 ? OrderType.ASK : OrderType.BID, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BitcurexTrade[] to a Trades Object
   *
   * @param bitcurexTrades The Bitcurex trade data
   * @return The trades
   */
  public static Trades adaptTrades(BitcurexTrade[] bitcurexTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>();
    long lastTradeId = 0;
    for (BitcurexTrade bitcurexTrade : bitcurexTrades) {
      long tradeId = bitcurexTrade.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(bitcurexTrade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a BitcurexTicker to a Ticker Object
   *
   * @param bitcurexTicker
   * @return
   */
  public static Ticker adaptTicker(BitcurexTicker bitcurexTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitcurexTicker.getLast();
    BigDecimal high = bitcurexTicker.getHigh();
    BigDecimal low = bitcurexTicker.getLow();
    BigDecimal buy = bitcurexTicker.getBid();
    BigDecimal sell = bitcurexTicker.getAsk();
    BigDecimal volume = bitcurexTicker.getVolume();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).build();
  }

  /**
   * Adapts a BitcurexFunds to an Wallet Object
   *
   * @param funds
   * @return
   */
  public static Wallet adaptWallet(BitcurexFunds funds) {

    // Adapt to XChange DTOs
    List<Balance> balances = new ArrayList<>(2);
    balances.add(new Balance(Currency.BTC, funds.getBtcs()));

    BigDecimal eur = funds.getEurs();
    if (eur != null) {
      balances.add(new Balance(Currency.EUR, eur));
    }

    BigDecimal pln = funds.getPlns();
    if (pln != null) {
      balances.add(new Balance(Currency.PLN, pln));
    }

    return new Wallet(balances);
  }

}
