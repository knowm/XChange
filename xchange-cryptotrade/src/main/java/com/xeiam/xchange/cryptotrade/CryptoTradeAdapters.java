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
import java.util.List;
import java.util.Map.Entry;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from CryptoTrade DTOs to XChange DTOs
 */
public final class CryptoTradeAdapters {

  /**
   * private Constructor
   */
  private CryptoTradeAdapters() {

  }

  /**
   * Adapts a cryptoTradeOrders to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradableIdentifier, String currency, OrderType orderType, String id) {

    BigMoney limitPrice = MoneyUtils.parseMoney(currency, price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, "", null, limitPrice);
  }

  /**
   * Adapts a List of cryptoTradeOrders to a List of LimitOrders
   * 
   * @param cryptoTradeOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> cryptoTradeOrders, String tradableIdentifier, String currency, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    // Bid orderbook is reversed order. Insert at index 0 instead of
    for (BigDecimal[] order : cryptoTradeOrders) {
      switch (orderType) {
      case ASK:
        limitOrders.add(adaptOrder(order[1], order[0], tradableIdentifier, currency, orderType, ""));
        break;
      case BID:
        limitOrders.add(0, adaptOrder(order[1], order[0], tradableIdentifier, currency, orderType, ""));
        break;
      default:
        break;

      }
    }

    return limitOrders;
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
}
