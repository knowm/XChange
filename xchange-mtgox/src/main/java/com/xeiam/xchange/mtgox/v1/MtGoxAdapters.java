/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.time.DateTime;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxOrder;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxWallet;
import com.xeiam.xchange.mtgox.v1.dto.trade.Wallets;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * Various adapters for converting from mtgox DTOs to XChange DTOs
 */
public class MtGoxAdapters {

  /**
   * Adapts a MtGoxOrder to a LimitOrder
   * 
   * @param mtGoxOrder
   * @param currency
   * @param orderTypeString
   * @return
   */
  public static LimitOrder adaptOrder(long amount_int, double price, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal tradeableAmount = (new BigDecimal(amount_int).divide(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)));
    String tradableIdentifier = Currencies.BTC;
    String transactionCurrency = currency;
    BigMoney limitPrice = MoneyUtils.parseFiat(currency + " " + price);

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, tradableIdentifier, transactionCurrency, limitPrice);

    return limitOrder;

  }

  /**
   * Adapts a List of MtGoxOrders to a List of LimitOrders
   * 
   * @param mtGoxOrders
   * @param currency
   * @param orderType
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<MtGoxOrder> mtGoxOrders, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (MtGoxOrder mtGoxOrder : mtGoxOrders) {
      limitOrders.add(adaptOrder(mtGoxOrder.getAmount_int(), mtGoxOrder.getPrice(), currency, orderType, id));
    }

    return limitOrders;
  }

  public static List<LimitOrder> adaptOrders(MtGoxOpenOrder[] mtGoxOpenOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (int i = 0; i < mtGoxOpenOrders.length; i++) {
      limitOrders.add(adaptOrder(mtGoxOpenOrders[i].getAmount().getValue_int(), mtGoxOpenOrders[i].getPrice().getValue(), mtGoxOpenOrders[i].getCurrency(), mtGoxOpenOrders[i].getType(), mtGoxOpenOrders[i].getOid()));
    }

    return limitOrders;
  }

  /**
   * Adapts a MtGox Wallet to a XChange Wallet
   * 
   * @param mtGoxWallet
   * @return
   */
  public static Wallet adaptWallet(MtGoxWallet mtGoxWallet) {

    if (mtGoxWallet == null) { // use the presence of a currency String to indicate existing wallet at MtGox
      return null; // an account maybe doesn't contain a MtGoxWallet
    } else {
      // TODO what about JPY? could be no problem here.
      BigMoney cash = MoneyUtils.parseFiat(mtGoxWallet.getBalance().getCurrency() + " " + mtGoxWallet.getBalance().getValue());
      return new com.xeiam.xchange.dto.trade.Wallet(cash);
    }

  }

  /**
   * Adapts a List of MtGox Wallets to a List of XChange Wallets
   * 
   * @param mtGoxWallets
   * @return
   */
  public static List<com.xeiam.xchange.dto.trade.Wallet> adaptWallets(Wallets mtGoxWallets) {

    List<com.xeiam.xchange.dto.trade.Wallet> wallets = new ArrayList<com.xeiam.xchange.dto.trade.Wallet>();

    for (MtGoxWallet mtGoxWallet : mtGoxWallets.getMtGoxWallets()) {
      com.xeiam.xchange.dto.trade.Wallet wallet = adaptWallet(mtGoxWallet);
      if (wallet != null) {
        wallets.add(wallet);
      }
    }
    return wallets;

  }

  /**
   * Adapts a MtGoxTrade to a Trade Object
   * 
   * @param mtGoxTrade
   * @return
   */
  public static Trade adaptTrade(MtGoxTrade mtGoxTrade) {

    OrderType orderType = mtGoxTrade.getTrade_type().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = new BigDecimal(mtGoxTrade.getAmount_int()).divide(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    String tradableIdentifier = mtGoxTrade.getItem();
    String transactionCurrency = mtGoxTrade.getPrice_currency();
    BigMoney price = MtGoxUtils.getPrice(transactionCurrency, mtGoxTrade.getPrice_int());

    DateTime dateTime = DateUtils.fromMillisUtc(mtGoxTrade.getDate() * 1000L);

    return new Trade(orderType, amount, tradableIdentifier, transactionCurrency, price, dateTime);
  }

  /**
   * Adapts a MtGoxTrade[] to a Trades Object
   * 
   * @param mtGoxTrades
   * @return
   */
  public static Trades adaptTrades(MtGoxTrade[] mtGoxTrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (int i = 0; i < mtGoxTrades.length; i++) {

      tradesList.add(adaptTrade(mtGoxTrades[i]));
    }
    return new Trades(tradesList);
  }

  public static Ticker adaptTicker(MtGoxTicker mtGoxTicker) {

    BigMoney last = MoneyUtils.parseFiat(mtGoxTicker.getLast().getCurrency() + " " + mtGoxTicker.getLast().getValue());
    BigMoney bid = MoneyUtils.parseFiat(mtGoxTicker.getBuy().getCurrency() + " " + mtGoxTicker.getBuy().getValue());
    BigMoney ask = MoneyUtils.parseFiat(mtGoxTicker.getSell().getCurrency() + " " + mtGoxTicker.getSell().getValue());
    long volume = mtGoxTicker.getVol().getValue_int();

    return new Ticker(last, bid, ask, mtGoxTicker.getVol().getCurrency(), volume);

  }

}
