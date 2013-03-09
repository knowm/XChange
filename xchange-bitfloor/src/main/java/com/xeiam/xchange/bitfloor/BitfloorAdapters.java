/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitfloor;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.bitfloor.dto.account.BitfloorBalance;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorDayInfo;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorOrderBook;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTicker;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTransaction;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from Bitfloor DTOs to XChange DTOs
 */
public final class BitfloorAdapters {

  /**
   * private Constructor
   */
  private BitfloorAdapters() {

  }

  /**
   * Adapts a BitfloorBalance to a AccountInfo
   * 
   * @param bitfloorBalance@return
   */
  public static AccountInfo adaptAccountInfo(BitfloorBalance[] bitfloorBalance, String userName) {

    // Adapt to XChange DTOs
    List<Wallet> wallets = new ArrayList<Wallet>();
    for (BitfloorBalance balance : bitfloorBalance) {
      wallets.add(new Wallet(balance.getCurrency(), BigMoney.of(CurrencyUnit.getInstance(balance.getCurrency()), balance.getAmount())));
    }
    return new AccountInfo(userName, wallets);
  }

  /**
   * Adapts a com.xeiam.xchange.bitfloor.api.model.OrderBook to a OrderBook Object
   */
  public static OrderBook adaptOrders(BitfloorOrderBook bitfloorOrderBook, String currency, String tradableIdentifier) {

    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, bitfloorOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, bitfloorOrderBook.getBids());
    return new OrderBook(asks, bids);
  }

  private static List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(tradableIdentifier, currency, ask, orderType));
    }
    return limitOrders;
  }

  private static LimitOrder createOrder(String tradableIdentifier, String currency, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), tradableIdentifier, currency, BigMoney.of(CurrencyUnit.USD, priceAndAmount.get(0)));
  }

  private static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   */
  public static Trades adaptTrades(BitfloorTransaction[] transactions, String currency, String tradableIdentifier) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BitfloorTransaction tx : transactions) {
      trades.add(new Trade(null, tx.getAmount(), tradableIdentifier, currency, BigMoney.of(CurrencyUnit.of(currency), tx.getPrice()), tx.getTimestamp()));
    }
    return new Trades(trades);
  }

  /**
   * Adapts a BitfloorTicker to a Ticker Object
   */
  public static Ticker adaptTicker(BitfloorTicker bitfloorTicker, BitfloorDayInfo dayInfo, String currency, String tradableIdentifier) {

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(getMoney(currency, bitfloorTicker.getPrice())).withVolume(dayInfo.getVolume()).withHigh(
        getMoney(currency, dayInfo.getHigh())).withLow(getMoney(currency, dayInfo.getLow())).withTimestamp(bitfloorTicker.getTimestamp()).build();
  }

  private static BigMoney getMoney(String currency, BigDecimal price) {

    return MoneyUtils.parse(currency + " " + price);
  }

}
