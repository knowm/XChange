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

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicOrder;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders.CryptoTradeOrder;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades.CryptoTradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.currency.MoneyUtils;
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
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from CryptoTrade DTOs to XChange DTOs
 */
public final class CryptoTradeAdapters {

  private CryptoTradeAdapters() {

  }

  public static LimitOrder adaptOrder(CryptoTradePublicOrder order, String tradableIdentifier, String currency, OrderType orderType) {

    BigMoney limitPrice = MoneyUtils.parseMoney(currency, order.getPrice());

    return new LimitOrder(orderType, order.getAmount(), tradableIdentifier, currency, "", null, limitPrice);
  }

  public static List<LimitOrder> adaptOrders(List<CryptoTradePublicOrder> cryptoTradeOrders, String tradableIdentifier, String currency, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CryptoTradePublicOrder order : cryptoTradeOrders)
      limitOrders.add(adaptOrder(order, tradableIdentifier, currency, orderType));

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(String tradableIdentifier, String currency, CryptoTradeDepth cryptoTradeDepth) {

    List<LimitOrder> asks = CryptoTradeAdapters.adaptOrders(cryptoTradeDepth.getAsks(), tradableIdentifier, currency, OrderType.ASK);
    List<LimitOrder> bids = CryptoTradeAdapters.adaptOrders(cryptoTradeDepth.getBids(), tradableIdentifier, currency, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static AccountInfo adaptAccountInfo(String userName, CryptoTradeAccountInfo accountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    for (Entry<String, BigDecimal> fundsEntry : accountInfo.getFunds().entrySet())
      wallets.add(Wallet.createInstance(fundsEntry.getKey().toUpperCase(), fundsEntry.getValue()));

    return new AccountInfo(userName, wallets);
  }

  public static Ticker adaptTicker(String tradeCurrency, String priceCurrency, CryptoTradeTicker cryptoTradeTicker) {

    CurrencyUnit priceCurrencyUnit = CurrencyUnit.of(priceCurrency);
    BigMoney ask = toBigMoneyIfNotNull(priceCurrencyUnit, cryptoTradeTicker.getMinAsk());
    BigMoney bid = toBigMoneyIfNotNull(priceCurrencyUnit, cryptoTradeTicker.getMaxBid());
    BigMoney last = toBigMoneyIfNotNull(priceCurrencyUnit, cryptoTradeTicker.getLast());
    BigMoney low = toBigMoneyIfNotNull(priceCurrencyUnit, cryptoTradeTicker.getLow());
    BigMoney high = toBigMoneyIfNotNull(priceCurrencyUnit, cryptoTradeTicker.getHigh());
    BigDecimal volume = cryptoTradeTicker.getVolumePriceCurrency();

    return TickerBuilder.newInstance().withTradableIdentifier(tradeCurrency).withAsk(ask).withBid(bid).withLast(last).withLow(low).withHigh(high).withVolume(volume).build();
  }

  private static BigMoney toBigMoneyIfNotNull(CurrencyUnit currencyUnit, BigDecimal number) {

    return number == null ? null : BigMoney.of(currencyUnit, number);
  }

  public static OrderType adaptOrderType(CryptoTradeOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(CryptoTradeOrderType.Buy)) ? OrderType.BID : OrderType.ASK;
  }

  public static LimitOrder adaptOrder(CryptoTradeOrder order) {

    CurrencyPair currencyPair = order.getCurrencyPair();
    BigMoney limitPrice = MoneyUtils.parseMoney(currencyPair.counterCurrency, order.getRate());
    Date timestamp = DateUtils.fromMillisUtc(order.getTimestamp());
    OrderType orderType = adaptOrderType(order.getType());

    return new LimitOrder(orderType, order.getRemainingAmount(), currencyPair.baseCurrency, currencyPair.counterCurrency, String.valueOf(order.getId()), timestamp, limitPrice);
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
    CurrencyPair currencyPair = trade.getCurrencyPair();
    BigMoney limitPrice = MoneyUtils.parseMoney(currencyPair.counterCurrency, trade.getRate());
    Date timestamp = DateUtils.fromMillisUtc(trade.getTimestamp());

    return new Trade(orderType, trade.getAmount(), currencyPair.baseCurrency, currencyPair.counterCurrency, limitPrice, timestamp, String.valueOf(trade.getId()), String.valueOf(trade.getMyOrder()));
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
