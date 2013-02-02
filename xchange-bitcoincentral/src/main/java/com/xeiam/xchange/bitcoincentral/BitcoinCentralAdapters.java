/**
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
package com.xeiam.xchange.bitcoincentral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.bitcoincentral.dto.account.BitcoinCentralAccountInfo;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BidAsk;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralDepth;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTicker;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTrade;
import com.xeiam.xchange.bitcoincentral.dto.trade.BitcoinCentralMyOrder;
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
 * Various adapters for converting from Bitcoin Central DTOs to XChange DTOs
 * 
 * @author Matija Mazi
 */
public final class BitcoinCentralAdapters {

  /**
   * private Constructor
   */
  private BitcoinCentralAdapters() {

  }

  /**
   * Adapts a BitcoinCentralAccountInfo to a AccountInfo
   */
  public static AccountInfo adaptAccountInfo(BitcoinCentralAccountInfo accountInfo, String userName) {

    Wallet eurWallet = new Wallet("EUR", BigMoney.of(CurrencyUnit.EUR, accountInfo.getEur()));
    Wallet btcWallet = new Wallet("BTC", BigMoney.of(CurrencyUnit.of("BTC"), accountInfo.getBtc()));
    Wallet usdWallet = new Wallet("GBP", BigMoney.of(CurrencyUnit.CAD, accountInfo.getGbp()));
    Wallet inrWallet = new Wallet("USD", BigMoney.of(CurrencyUnit.getInstance("INR"), accountInfo.getUsd()));

    return new AccountInfo(userName, Arrays.asList(btcWallet, usdWallet, eurWallet, inrWallet));
  }

  /**
   * Adapts a BitcoinCentralTicker to a Ticker Object
   * 
   * @param bitcoinCentralTicker
   * @return
   */
  public static Ticker adaptTicker(BitcoinCentralTicker bitcoinCentralTicker, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(bitcoinCentralTicker.getCurrency().toUpperCase() + " " + bitcoinCentralTicker.getPrice());
    BigMoney bid = MoneyUtils.parse(bitcoinCentralTicker.getCurrency().toUpperCase() + " " + bitcoinCentralTicker.getBid());
    BigMoney ask = MoneyUtils.parse(bitcoinCentralTicker.getCurrency().toUpperCase() + " " + bitcoinCentralTicker.getAsk());
    BigMoney high = MoneyUtils.parse(bitcoinCentralTicker.getCurrency().toUpperCase() + " " + bitcoinCentralTicker.getHigh());
    BigMoney low = MoneyUtils.parse(bitcoinCentralTicker.getCurrency().toUpperCase() + " " + bitcoinCentralTicker.getLow());
    BigDecimal volume = bitcoinCentralTicker.getVolume();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).build();
  }

  /**
   * BitcoinCentralDepth to a OrderBook Object
   * 
   * @param bitcoinCentralDepth
   * @param currency
   * @param tradableIdentifier
   * @return
   */
  public static OrderBook adaptOrders(BitcoinCentralDepth bitcoinCentralDepth, String currency, String tradableIdentifier) {

    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, bitcoinCentralDepth.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, bitcoinCentralDepth.getBids());
    return new OrderBook(asks, bids);
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @param orderType
   * @param orders
   * @return
   */
  private static List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<BidAsk> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (BidAsk bidAsk : orders) {
      limitOrders.add(new LimitOrder(orderType, bidAsk.getAmount(), tradableIdentifier, currency, MoneyUtils.parse(currency + " " + bidAsk.getPrice())));
    }
    return limitOrders;
  }

  /**
   * Adapts a BitcoinCentralTrade[] to Trades DTO
   * 
   * @param bitcoinCentralTrades
   * @param currency
   * @param tradableIdentifier
   * @return
   */
  // TODO implement timestamp after bitcoin central provides a better timestamp
  public static Trades adaptTrades(BitcoinCentralTrade[] bitcoinCentralTrades, String currency, String tradableIdentifier) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BitcoinCentralTrade bitcoinCentralTrade : bitcoinCentralTrades) {
      trades.add(new Trade(null, bitcoinCentralTrade.getTradedBtc(), tradableIdentifier, bitcoinCentralTrade.getCurrency(), BigMoney.of(CurrencyUnit.of(bitcoinCentralTrade.getCurrency()),
          bitcoinCentralTrade.getPpc()), null));
    }
    return new Trades(trades);
  }

  public static List<LimitOrder> adaptActive(BitcoinCentralMyOrder[] accountTradeOrders) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (BitcoinCentralMyOrder order : accountTradeOrders) {
      if (order.isPendingExecution()) {
        orders.add(new LimitOrder(order.getCategory().type, order.getAmount(), "BTC", order.getCurrency(), Integer.toString(order.getId()), BigMoney.of(CurrencyUnit.getInstance(order.getCurrency()),
            order.getPpc())));
      }
    }
    return orders;
  }
}
