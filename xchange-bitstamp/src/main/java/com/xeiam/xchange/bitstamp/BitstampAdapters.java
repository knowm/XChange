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
package com.xeiam.xchange.bitstamp;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.currency.Currencies;
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
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitstamp DTOs to XChange DTOs
 */
public final class BitstampAdapters {

  /**
   * private Constructor
   */
  private BitstampAdapters() {

  }

  /**
   * Adapts a BitstampBalance to a AccountInfo
   * 
   * @param bitstampBalance The Bitstamp balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(BitstampBalance bitstampBalance, String userName) {

    // Adapt to XChange DTOs
    Wallet usdWallet = Wallet.createInstance(Currencies.USD, bitstampBalance.getUsdBalance());
    Wallet btcWallet = Wallet.createInstance(Currencies.BTC, bitstampBalance.getBtcBalance());

    return new AccountInfo(userName, Arrays.asList(usdWallet, btcWallet));
  }

  /**
   * Adapts a com.xeiam.xchange.bitstamp.api.model.OrderBook to a OrderBook Object
   * 
   * @param bitstampOrderBook The bitstamp order book
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrders(BitstampOrderBook bitstampOrderBook, String tradableIdentifier, String currency) {

    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, bitstampOrderBook.getBids());
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
   * 
   * @param transactions The Bitstamp transactions
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitstampTransaction[] transactions, String tradableIdentifier, String currency) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BitstampTransaction tx : transactions) {
      trades.add(new Trade(null, tx.getAmount(), tradableIdentifier, currency, BigMoney.of(CurrencyUnit.of(currency), tx.getPrice()), DateUtils.fromMillisUtc(tx.getDate() * 1000L)));
    }

    return new Trades(trades);
  }

  /**
   * Adapts a BitstampTicker to a Ticker Object
   * 
   * @param bitstampTicker The exchange specific ticker
   * @param tradableIdentifier The tradeable identifier (e.g. BTC in BTC/USD)
   * @param currency The currency (e.g. USD in BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitstampTicker bitstampTicker, String tradableIdentifier, String currency) {

    BigMoney last = MoneyUtils.parse(currency + " " + bitstampTicker.getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + bitstampTicker.getBid());
    BigMoney ask = MoneyUtils.parse(currency + " " + bitstampTicker.getAsk());
    BigMoney high = MoneyUtils.parse(currency + " " + bitstampTicker.getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + bitstampTicker.getLow());
    BigDecimal volume = bitstampTicker.getVolume();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();

  }

}
