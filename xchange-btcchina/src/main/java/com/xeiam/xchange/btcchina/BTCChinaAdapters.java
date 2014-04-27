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
package com.xeiam.xchange.btcchina;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.BTCChinaValue;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
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
 * Various adapters for converting from BTCChina DTOs to XChange DTOs
 */
public final class BTCChinaAdapters {

  /**
   * private Constructor
   */
  private BTCChinaAdapters() {

  }

  /**
   * Adapts a List of btcchinaOrders to a List of LimitOrders
   * 
   * @param btcchinaOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> btcchinaOrders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(btcchinaOrders.size());

    for (BigDecimal[] btcchinaOrder : btcchinaOrders) {
      limitOrders.add(adaptOrder(btcchinaOrder[1], btcchinaOrder[0], currencyPair, orderType));
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCChinaOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, amount, currencyPair, "", null, price);

  }

  /**
   * Adapts a BTCChinaTrade to a Trade Object
   * 
   * @param btcChinaTrade A BTCChina trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCChinaTrade btcChinaTrade, CurrencyPair currencyPair) {

    BigDecimal amount = btcChinaTrade.getAmount();
    BigDecimal price = btcChinaTrade.getPrice();
    Date date = DateUtils.fromMillisUtc(btcChinaTrade.getDate() * 1000L);
    OrderType orderType = btcChinaTrade.getOrderType().equals("sell") ? OrderType.ASK : OrderType.BID;

    final String tradeId = String.valueOf(btcChinaTrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BTCChinaTrade[] to a Trades Object
   * 
   * @param btcchinaTrades The BTCChina trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCChinaTrade[] btcchinaTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(btcchinaTrades.length);
    for (BTCChinaTrade btcchinaTrade : btcchinaTrades) {
      tradesList.add(adaptTrade(btcchinaTrade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCChinaTicker to a Ticker Object
   * 
   * @param btcChinaTicker
   * @return
   */
  public static Ticker adaptTicker(BTCChinaTicker btcChinaTicker, CurrencyPair currencyPair) {

    BigDecimal last = btcChinaTicker.getTicker().getLast();
    BigDecimal high = btcChinaTicker.getTicker().getHigh();
    BigDecimal low = btcChinaTicker.getTicker().getLow();
    BigDecimal buy = btcChinaTicker.getTicker().getBuy();
    BigDecimal sell = btcChinaTicker.getTicker().getSell();
    BigDecimal volume = btcChinaTicker.getTicker().getVol();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).build();
  }

  /**
   * Adapts a BTCChinaAccountInfoResponse to AccountInfo Object
   * 
   * @param response
   * @return
   */
  public static AccountInfo adaptAccountInfo(BTCChinaResponse<BTCChinaAccountInfo> response) {

    BTCChinaAccountInfo result = response.getResult();
    return new AccountInfo(result.getProfile().getUsername(), result.getProfile().getTradeFee(), BTCChinaAdapters.adaptWallets(result.getBalances(), result.getFrozens()));
  }

  /**
   * @param balances
   * @param frozens
   * @return
   */
  public static List<Wallet> adaptWallets(Map<String, BTCChinaValue> balances, Map<String, BTCChinaValue> frozens) {

    List<Wallet> wallets = new ArrayList<Wallet>(balances.size());

    for (Map.Entry<String, BTCChinaValue> entry : balances.entrySet()) {
      Wallet wallet;
      BTCChinaValue frozen = frozens.get(entry.getKey());
      if (frozen != null) {
        wallet = adaptWallet(entry.getValue(), frozen);
        if (wallet != null) {
          wallets.add(wallet);
        }
      }
    }
    return wallets;

  }

  /**
   * Adapts BTCChinaValue balance, BTCChinaValue frozen to wallet
   * 
   * @param balance
   * @param frozen
   * @return
   */
  public static Wallet adaptWallet(BTCChinaValue balance, BTCChinaValue frozen) {

    if (balance != null && frozen != null) {
      BigDecimal balanceAmount = BTCChinaUtils.valueToBigDecimal(balance);
      BigDecimal frozenAmount = BTCChinaUtils.valueToBigDecimal(frozen);
      BigDecimal cash = balanceAmount.add(frozenAmount);
      return new Wallet(balance.getCurrency(), cash);
    }
    else {
      return null;
    }
  }

  // /**
  // * Adapts List<BTCChinaOrder> to OpenOrders
  // *
  // * @param orders
  // * @return
  // */
  // todo: can't have <> in javadoc
  /**
   * @param orders
   * @return
   */
  public static OpenOrders adaptOpenOrders(List<BTCChinaOrder> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders == null ? 0 : orders.size());

    if (orders != null) {
      for (BTCChinaOrder order : orders) {
        if (order.getStatus().equals("open")) {
          LimitOrder limitOrder = adaptLimitOrder(order);
          if (limitOrder != null) {
            limitOrders.add(limitOrder);
          }
        }
      }
    }

    return new OpenOrders(limitOrders);
  }

  /**
   * Adapts BTCChinaOrder to LimitOrder
   * 
   * @param order
   * @return
   */
  public static LimitOrder adaptLimitOrder(BTCChinaOrder order) {

    OrderType orderType = order.getType().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = order.getAmount();
    String id = Long.toString(order.getId());
    Date date = new Date(order.getDate() * 1000);
    BigDecimal price = order.getPrice();

    return new LimitOrder(orderType, amount, CurrencyPair.BTC_CNY, id, date, price);
  }

  public static Trade adaptTransaction(BTCChinaTransaction transaction) {

    String type = transaction.getType();

    // could also be 'rebate' or other
    if (!(type.startsWith("buy") || type.startsWith("sell"))) {
      return null;
    }

    OrderType orderType = type.startsWith("buy") ? OrderType.BID : OrderType.ASK;
    CurrencyPair currencyPair = null;
    BigDecimal price = BigDecimal.ZERO;
    BigDecimal amount = BigDecimal.ZERO;

    if (!transaction.getBtcAmount().equals(BigDecimal.ZERO)) {
      currencyPair = new CurrencyPair("BTC", "CNY");
      price = transaction.getCnyAmount().divide(transaction.getBtcAmount()).abs();
      amount = transaction.getBtcAmount().abs();
    }
    else {
      currencyPair = new CurrencyPair("LTC", "CNY");
      price = transaction.getCnyAmount().divide(transaction.getLtcAmount()).abs();
      amount = transaction.getLtcAmount().abs();
    }

    Date date = DateUtils.fromMillisUtc(transaction.getDate() * 1000L);

    return new Trade(orderType, BTCChinaUtils.truncateAmount(amount), currencyPair, price, date, String.valueOf(transaction.getId()));
  }

  /**
   * Adapt BTCChinaTransactions to Trades
   * 
   * @param transactions
   * @return
   */
  public static Trades adaptTransactions(List<BTCChinaTransaction> transactions) {

    List<Trade> tradeHistory = new ArrayList<Trade>(transactions.size());

    for (BTCChinaTransaction transaction : transactions) {
      Trade adaptTransaction = adaptTransaction(transaction);
      if (adaptTransaction != null) {
        tradeHistory.add(adaptTransaction);
      }
    }

    return new Trades(tradeHistory, TradeSortType.SortByID);
  }

}
