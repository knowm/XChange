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
package com.xeiam.xchange.btce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.btce.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.dto.trade.BTCEOrder;
import com.xeiam.xchange.currency.MoneyUtils;
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
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
public final class BTCEAdapters {

  private static final Logger log = LoggerFactory.getLogger(BTCEAdapters.class);

  /**
   * private Constructor
   */
  private BTCEAdapters() {

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
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradableIdentifier, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigMoney limitPrice;
    limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, limitPrice);

  }

  /**
   * Adapts a List of BTCEOrders to a List of LimitOrders
   * 
   * @param BTCEOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> BTCEOrders, String tradableIdentifier, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] btceOrder : BTCEOrders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of
      // appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(btceOrder[1], btceOrder[0], tradableIdentifier, currency, orderType, id));
      } else {
        limitOrders.add(adaptOrder(btceOrder[1], btceOrder[0], tradableIdentifier, currency, orderType, id));
      }
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCETrade to a Trade Object
   * 
   * @param BTCETrade A BTCE trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCETrade BTCETrade) {

    OrderType orderType = BTCETrade.equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = BTCETrade.getAmount();
    String currency = BTCETrade.getPriceCurrency();
    BigMoney price = MoneyUtils.parse(currency + " " + BTCETrade.getPrice());
    String tradableIdentifier = BTCETrade.getItem();
    Date date = DateUtils.fromMillisUtc(BTCETrade.getDate() * 1000L);

    return new Trade(orderType, amount, tradableIdentifier, currency, price, date);
  }

  /**
   * Adapts a BTCETrade[] to a Trades Object
   * 
   * @param BTCETrades The BTCE trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCETrade[] BTCETrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BTCETrade BTCETrade : BTCETrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(BTCETrade));
    }
    return new Trades(tradesList);
  }

  /**
   * Adapts a BTCETicker to a Ticker Object
   * 
   * @param bTCETicker
   * @return
   */
  public static Ticker adaptTicker(BTCETicker bTCETicker, String tradableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parse(currency + " " + bTCETicker.getTicker().getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + bTCETicker.getTicker().getSell());
    BigMoney ask = MoneyUtils.parse(currency + " " + bTCETicker.getTicker().getBuy());
    BigMoney high = MoneyUtils.parse(currency + " " + bTCETicker.getTicker().getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + bTCETicker.getTicker().getLow());
    BigDecimal volume = bTCETicker.getTicker().getVolCur();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
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

    List<LimitOrder> os = new ArrayList<LimitOrder>();
    for (Long id : btceOrderMap.keySet()) {
      BTCEOrder o = btceOrderMap.get(id);
      OrderType orderType = o.getType() == BTCEOrder.Type.buy ? OrderType.BID : OrderType.ASK;
      String[] pair = o.getPair().split("_");
      String currency = pair[1].toUpperCase();
      BigMoney price = BigMoney.of(CurrencyUnit.of(currency), o.getAmount());
      os.add(new LimitOrder(orderType, o.getAmount(), pair[0].toUpperCase(), currency, Long.toString(id), price));
    }
    return new OpenOrders(os);
  }
}
