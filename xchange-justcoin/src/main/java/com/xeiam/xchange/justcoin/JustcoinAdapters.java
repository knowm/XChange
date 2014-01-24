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
package com.xeiam.xchange.justcoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * Various adapters for converting from Justcoin DTOs to XChange DTOs
 * 
 * jamespedwards42
 */
public final class JustcoinAdapters {

  /**
   * private Constructor
   */
  private JustcoinAdapters() {

  }

  /**
   * Adapts a List of Justcoin Orders to a List of LimitOrders
   * 
   * @param justcoinOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(final List<List<BigDecimal>> justcoinOrders, final String tradableIdentifier, final String currency, final OrderType orderType) {

    final List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (final List<BigDecimal> justcoinOrder : justcoinOrders) {
      limitOrders.add(adaptOrder(justcoinOrder.get(1), justcoinOrder.get(0), tradableIdentifier, currency, orderType));
    }

    return limitOrders;
  }

  /**
   * Adapts a Justcoin Order to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(final BigDecimal amount, final BigDecimal price, final String tradableIdentifier, final String currency, final OrderType orderType) {

    final BigMoney limitPrice = MoneyUtils.parseMoney(currency, price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, null, null, limitPrice);
  }

  /**
   * Adapts a JustcoinTicker to a Ticker
   * 
   * @param justcoinTickers
   * @return
   */
  public static Ticker adaptTicker(final JustcoinTicker[] justcoinTickers, final String tradableIdentifier, final String currency) {

    for (final JustcoinTicker justcointTicker : justcoinTickers) {
      if (justcointTicker.getId().equals(tradableIdentifier + currency)) {
        final BigMoney last = MoneyUtils.parseMoney(currency, justcointTicker.getLast());
        final BigMoney bid = MoneyUtils.parseMoney(currency, justcointTicker.getAsk());
        final BigMoney ask = MoneyUtils.parseMoney(currency, justcointTicker.getBid());
        final BigMoney high = MoneyUtils.parseMoney(currency, justcointTicker.getHigh());
        final BigMoney low = MoneyUtils.parseMoney(currency, justcointTicker.getLow());
        final BigDecimal volume = justcointTicker.getVolume();

        return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
      }
    }

    return null;
  }

  public static AccountInfo adaptAccountInfo(final String username, final JustcoinBalance[] justcoinBalances) {

    final List<Wallet> wallets = new ArrayList<Wallet>();
    for (final JustcoinBalance balanceForCurrency : justcoinBalances) {
      final String currency = balanceForCurrency.getCurrency();
      final BigDecimal balance = balanceForCurrency.getBalance();
      wallets.add(Wallet.createInstance(currency, balance));
    }

    return new AccountInfo(username, wallets);
  }

}
