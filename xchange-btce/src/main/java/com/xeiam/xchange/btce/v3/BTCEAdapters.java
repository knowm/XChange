/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.btce.v3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
public final class BTCEAdapters {

  public static final Logger log = LoggerFactory.getLogger(BTCEAdapters.class);

  /**
   * private Constructor
   */
  private BTCEAdapters() {

  }

  /**
   * Adapts a List of BTCEOrders to a List of LimitOrders
   * 
   * @param bTCEOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bTCEOrders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] btceOrder : bTCEOrders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(btceOrder[1], btceOrder[0], currencyPair, orderType, id));
      }
      else {
        limitOrders.add(adaptOrder(btceOrder[1], btceOrder[0], currencyPair, orderType, id));
      }
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCEOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal limitPrice;
    limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, currencyPair, id, null, limitPrice);

  }

  /**
   * Adapts a BTCETradeV3 to a Trade Object
   * 
   * @param bTCETrade BTCE trade object v.3
   * @param tradableIdentifier First currency in the pair
   * @param currency Second currency in the pair
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCETrade bTCETrade, CurrencyPair currencyPair) {

    OrderType orderType = bTCETrade.getTradeType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = bTCETrade.getAmount();
    BigDecimal price = MoneyUtils.parse(currency + " " + bTCETrade.getPrice());
    Date date = DateUtils.fromMillisUtc(bTCETrade.getDate() * 1000L);

    final String tradeId = String.valueOf(bTCETrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BTCETradeV3[] to a Trades Object
   * 
   * @param BTCETrades The BTCE trade data returned by API v.3
   * @param tradableIdentifier First currency of the pair
   * @param currency Second currency of the pair
   * @return The trades
   */
  public static Trades adaptTrades(BTCETrade[] BTCETrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BTCETrade BTCETrade : BTCETrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(BTCETrade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCETicker to a Ticker Object
   * 
   * @param bTCETicker
   * @return
   */
  public static Ticker adaptTicker(BTCETicker bTCETicker, CurrencyPair currencyPair) {

    BigDecimal last = MoneyUtils.parse(currency + " " + bTCETicker.getLast());
    BigDecimal bid = MoneyUtils.parse(currency + " " + bTCETicker.getSell());
    BigDecimal ask = MoneyUtils.parse(currency + " " + bTCETicker.getBuy());
    BigDecimal high = MoneyUtils.parse(currency + " " + bTCETicker.getHigh());
    BigDecimal low = MoneyUtils.parse(currency + " " + bTCETicker.getLow());
    BigDecimal volume = bTCETicker.getVolCur();
    Date timestamp = DateUtils.fromMillisUtc(bTCETicker.getUpdated() * 1000L);

    return TickerBuilder.newInstance().withCurrencyPair(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp)
        .build();
  }

  public static AccountInfo adaptAccountInfo(BTCEAccountInfo btceAccountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    Map<String, BigDecimal> funds = btceAccountInfo.getFunds();

    for (String lcCurrency : funds.keySet()) {
      String currency = lcCurrency.toUpperCase();
      try {
        CurrencyUnit.of(currency);
      } catch (IllegalCurrencyException e) {
        log.warn("Ignoring unknown currency {}", currency);
        continue;
      }
      wallets.add(Wallet.createInstance(currency, funds.get(lcCurrency)));
    }
    return new AccountInfo(null, wallets);
  }

  public static OpenOrders adaptOrders(Map<Long, BTCEOrder> btceOrderMap) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (Long id : btceOrderMap.keySet()) {
      BTCEOrder bTCEOrder = btceOrderMap.get(id);
      OrderType orderType = bTCEOrder.getType() == BTCEOrder.Type.buy ? OrderType.BID : OrderType.ASK;
      String[] pair = bTCEOrder.getPair().split("_");
      String currency = pair[1].toUpperCase();
      BigDecimal price = BigDecimal.of(CurrencyUnit.of(currency), bTCEOrder.getRate());
      Date timestamp = DateUtils.fromMillisUtc(bTCEOrder.getTimestampCreated() * 1000L);
      limitOrders.add(new LimitOrder(orderType, bTCEOrder.getAmount(), pair[0].toUpperCase(), currency, Long.toString(id), timestamp, price));
    }
    return new OpenOrders(limitOrders);
  }

  public static Trades adaptTradeHistory(Map<Long, BTCETradeHistoryResult> tradeHistory) {

    List<Trade> trades = new ArrayList<Trade>(tradeHistory.size());
    for (Entry<Long, BTCETradeHistoryResult> entry : tradeHistory.entrySet()) {
      BTCETradeHistoryResult result = entry.getValue();
      OrderType type = result.getType() == BTCETradeHistoryResult.Type.buy ? OrderType.BID : OrderType.ASK;
      String[] pair = result.getPair().split("_");
      String tradableIdentifier = pair[0].toUpperCase();
      String transactionCurrency = pair[1].toUpperCase();
      BigDecimal price = BigDecimal.of(CurrencyUnit.of(transactionCurrency), result.getRate());
      BigDecimal tradableAmount = result.getAmount();
      Date timeStamp = DateUtils.fromMillisUtc(result.getTimestamp() * 1000L);
      String orderId = String.valueOf(result.getOrderId());
      String tradeId = String.valueOf(entry.getKey());
      trades.add(new Trade(type, tradableAmount, tradableIdentifier, transactionCurrency, price, timeStamp, tradeId, orderId));
    }
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static ExchangeInfo adaptExchangeInfo(BTCEExchangeInfo infoV3) {

    Set<String> btcePairs = infoV3.getPairs().keySet();
    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>();
    for (String s : btcePairs) {
      String[] p = s.split("_");
      pairs.add(new CurrencyPair(p[0].toUpperCase(), p[1].toUpperCase()));
    }
    return new ExchangeInfo(pairs);
  }

}
