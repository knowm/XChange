/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.BTCChinaValue;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
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
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> btcchinaOrders, String currency, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] btcchinaOrder : btcchinaOrders) {
      limitOrders.add(adaptOrder(btcchinaOrder[1], btcchinaOrder[0], currency, orderType));
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
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String currency, OrderType orderType) {

    // place a limit order
    String tradableIdentifier = Currencies.BTC;
    BigMoney limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, "", null, limitPrice);

  }

  /**
   * Adapts a BTCChinaTrade to a Trade Object
   * 
   * @param btcChinaTrade A BTCChina trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCChinaTrade btcChinaTrade, String currency, String tradableIdentifier) {

    BigDecimal amount = btcChinaTrade.getAmount();
    BigMoney price = MoneyUtils.parse(currency + " " + btcChinaTrade.getPrice());
    Date date = DateUtils.fromMillisUtc(btcChinaTrade.getDate() * 1000L);

    return new Trade(null, amount, tradableIdentifier, currency, price, date, btcChinaTrade.getTid());
  }

  /**
   * Adapts a BTCChinaTrade[] to a Trades Object
   * 
   * @param btcchinaTrades The BTCChina trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCChinaTrade[] btcchinaTrades, String currency, String tradableIdentifier) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BTCChinaTrade btcchinaTrade : btcchinaTrades) {
      tradesList.add(adaptTrade(btcchinaTrade, currency, tradableIdentifier));
    }
    return new Trades(tradesList);
  }

  public static String getPriceString(BigMoney price) {

    return price.getAmount().stripTrailingZeros().toPlainString();
  }

  /**
   * Adapts a BTCChinaTicker to a Ticker Object
   * 
   * @param btcChinaTicker
   * @return
   */
  public static Ticker adaptTicker(BTCChinaTicker btcChinaTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getLast());
    BigMoney high = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getLow());
    BigMoney buy = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getBuy());
    BigMoney sell = MoneyUtils.parse(currency + " " + btcChinaTicker.getTicker().getSell());
    BigDecimal volume = btcChinaTicker.getTicker().getVol();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).build();
  }

  /**
   * Adapts a BTCChinaAccountInfoResponse to AccountInfo Object
   * 
   * @param response
   * @return
   */
  public static AccountInfo adaptAccountInfo(BTCChinaResponse<BTCChinaAccountInfo> response) {

    BTCChinaAccountInfo result = response.getResult();
    return new AccountInfo(result.getProfile().getUsername(), BTCChinaAdapters.adaptWallets(result.getBalances(), result.getFrozens()));
  }

  // /**
  // * Adapts Map<String, BTCChinaValue> balances, Map<String,BTCChinaValue> frozens to List<Wallet>
  // *
  // * @param balances
  // * @param frozens
  // * @return
  // */
  // todo: can't have <> in javadoc
  /**
   * @param balances
   * @param frozens
   * @return
   */
  public static List<Wallet> adaptWallets(Map<String, BTCChinaValue> balances, Map<String, BTCChinaValue> frozens) {

    List<Wallet> wallets = new ArrayList<Wallet>();

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
      BigMoney cash = BigMoney.of(CurrencyUnit.of(balance.getCurrency()), balanceAmount.add(frozenAmount));
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

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    if (orders != null) {
      for (BTCChinaOrder order : orders) {
        System.out.println(order);
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

    Order.OrderType orderType = order.getType().equals("bid") ? Order.OrderType.BID : Order.OrderType.ASK;
    BigDecimal amount = order.getAmount();
    String id = Long.toString(order.getId());
    Date date = new Date(order.getDate() * 1000);
    BigMoney price = BigMoney.of(CurrencyUnit.of(order.getCurrency()), order.getPrice());

    return new LimitOrder(orderType, amount, "BTC", "CNY", id, date, price);
  }

}
