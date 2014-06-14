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
package com.xeiam.xchange.cryptotrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicOrder;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders.CryptoTradeOrder;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades.CryptoTradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from CryptoTrade DTOs to XChange DTOs
 */
public final class CryptoTradeAdapters {

  private CryptoTradeAdapters() {

  }

  public static LimitOrder adaptOrder(CryptoTradePublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<CryptoTradePublicOrder> cryptoTradeOrders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CryptoTradePublicOrder order : cryptoTradeOrders)
      limitOrders.add(adaptOrder(order, currencyPair, orderType));

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(CurrencyPair currencyPair, CryptoTradeDepth cryptoTradeDepth) {

    List<LimitOrder> asks = CryptoTradeAdapters.adaptOrders(cryptoTradeDepth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = CryptoTradeAdapters.adaptOrders(cryptoTradeDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static AccountInfo adaptAccountInfo(String userName, CryptoTradeAccountInfo accountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    for (Entry<String, BigDecimal> fundsEntry : accountInfo.getFunds().entrySet())
      wallets.add(new Wallet(fundsEntry.getKey().toUpperCase(), fundsEntry.getValue()));

    return new AccountInfo(userName, wallets);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, CryptoTradeTicker cryptoTradeTicker) {

    BigDecimal ask = cryptoTradeTicker.getMinAsk();
    BigDecimal bid = cryptoTradeTicker.getMaxBid();
    BigDecimal last = cryptoTradeTicker.getLast();
    BigDecimal low = cryptoTradeTicker.getLow();
    BigDecimal high = cryptoTradeTicker.getHigh();
    BigDecimal volume = cryptoTradeTicker.getVolumeTradeCurrency();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withAsk(ask).withBid(bid).withLast(last).withLow(low).withHigh(high).withVolume(volume).build();
  }

  public static OrderType adaptOrderType(CryptoTradeOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(CryptoTradeOrderType.Buy)) ? OrderType.BID : OrderType.ASK;
  }

  public static LimitOrder adaptOrder(CryptoTradeOrder order) {

    Date timestamp = new Date(order.getTimestamp());
    OrderType orderType = adaptOrderType(order.getType());

    return new LimitOrder(orderType, order.getRemainingAmount(), order.getCurrencyPair(), String.valueOf(order.getId()), timestamp, order.getRate());
  }

  public static OpenOrders adaptOpenOrders(CryptoTradeOrders cryptoTradeOrders) {

    List<LimitOrder> orderList = new ArrayList<LimitOrder>();
    for (CryptoTradeOrder cryptoTradeOrder : cryptoTradeOrders.getOrders()) {
      // TODO Change to check cryptoTradeOrder.getStatus() once all statuses are known.
      if (cryptoTradeOrder.getRemainingAmount().compareTo(BigDecimal.ZERO) > 0) {
        LimitOrder adaptedOrder = adaptOrder(cryptoTradeOrder);
        orderList.add(adaptedOrder);
      }
    }

    return new OpenOrders(orderList);
  }

  public static Trade adaptTrade(CryptoTradeTrade trade) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = new Date(trade.getTimestamp());

    return new Trade(orderType, trade.getAmount(), trade.getCurrencyPair(), trade.getRate(), timestamp, String.valueOf(trade.getId()), String.valueOf(trade.getMyOrder()));
  }

  public static Trades adaptTrades(CryptoTradeTrades cryptoTradeTrades) {

    List<Trade> tradeList = new ArrayList<Trade>();
    for (CryptoTradeTrade cryptoTradeTrade : cryptoTradeTrades.getTrades()) {
      Trade trade = adaptTrade(cryptoTradeTrade);
      tradeList.add(trade);
    }

    return new Trades(tradeList, TradeSortType.SortByTimestamp);
  }
}
