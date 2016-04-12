package org.knowm.xchange.virtex.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTicker;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTrade;

/**
 * Various adapters for converting from VirtEx DTOs to XChange DTOs
 */
public final class VirtExAdapters {

  /**
   * private Constructor
   */
  private VirtExAdapters() {

  }

  /**
   * Adapts a VirtExOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);

  }

  /**
   * Adapts a List of virtexOrders to a List of LimitOrders
   * 
   * @param virtexOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> virtexOrders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] virtexOrder : virtexOrders) {
      limitOrders.add(adaptOrder(virtexOrder[1], virtexOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a VirtExTrade to a Trade Object
   * 
   * @param virtExTrade A VirtEx trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(VirtExTrade virtExTrade, CurrencyPair currencyPair) {

    BigDecimal amount = virtExTrade.getAmount();
    Date date = DateUtils.fromMillisUtc((long) virtExTrade.getDate() * 1000L);
    final String tradeId = String.valueOf(virtExTrade.getTid());
    return new Trade(null, amount, currencyPair, virtExTrade.getPrice(), date, tradeId);
  }

  /**
   * Adapts a VirtExTrade[] to a Trades Object
   * 
   * @param virtExTrades The VirtEx trade data
   * @return The trades
   */
  public static Trades adaptTrades(List<VirtExTrade> virtExTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (VirtExTrade virtexTrade : virtExTrades) {
      tradesList.add(adaptTrade(virtexTrade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a VirtExTicker to a Ticker Object
   * 
   * @param virtExTicker
   * @return
   */
  public static Ticker adaptTicker(VirtExTicker virtExTicker, CurrencyPair currencyPair) {

    BigDecimal last = virtExTicker.getLast();
    BigDecimal high = virtExTicker.getHigh();
    BigDecimal low = virtExTicker.getLow();
    BigDecimal volume = virtExTicker.getVolume();
    BigDecimal buy = virtExTicker.getBuy();
    BigDecimal sell = virtExTicker.getSell();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).ask(sell).bid(buy).volume(volume).build();
  }

}
