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
package com.xeiam.xchange.btctrade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
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

/**
 * Various adapters for converting from BTCTrade DTOs to XChange DTOs.
 */
public final class BTCTradeAdapters {

  /**
   * private Constructor
   */
  private BTCTradeAdapters() {

  }

  public static Date adaptDatetime(String datetime) {

    try {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static Ticker adaptTicker(BTCTradeTicker btcTradeTicker, CurrencyPair currencyPair) {

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withHigh(btcTradeTicker.getHigh()).withLow(btcTradeTicker.getLow()).withBid(btcTradeTicker.getBuy()).withAsk(
        btcTradeTicker.getSell()).withLast(btcTradeTicker.getLast()).withVolume(btcTradeTicker.getVol()).build();
  }

  public static OrderBook adaptOrderBook(BTCTradeDepth btcTradeDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptLimitOrders(btcTradeDepth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = adaptLimitOrders(btcTradeDepth.getBids(), currencyPair, OrderType.BID);
    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptLimitOrders(BigDecimal[][] orders, CurrencyPair currencyPair, OrderType type) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);
    for (BigDecimal[] order : orders) {
      limitOrders.add(adaptLimitOrder(order, currencyPair, type));
    }
    return limitOrders;
  }

  private static LimitOrder adaptLimitOrder(BigDecimal[] order, CurrencyPair currencyPair, OrderType type) {

    return new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
  }

  public static Trades adaptTrades(BTCTradeTrade[] btcTradeTrades, CurrencyPair currencyPair) {

    int length = btcTradeTrades.length;
    List<Trade> trades = new ArrayList<Trade>(length);
    for (BTCTradeTrade btcTradeTrade : btcTradeTrades) {
      trades.add(adaptTrade(btcTradeTrade, currencyPair));
    }
    long lastID = length > 0 ? btcTradeTrades[length - 1].getTid() : 0L;
    return new Trades(trades, lastID, TradeSortType.SortByID);
  }

  private static Trade adaptTrade(BTCTradeTrade btcTradeTrade, CurrencyPair currencyPair) {

    return new Trade(adaptOrderType(btcTradeTrade.getType()), btcTradeTrade.getAmount(), currencyPair, btcTradeTrade.getPrice(), new Date(btcTradeTrade.getDate() * 1000), String.valueOf(btcTradeTrade
        .getTid()));
  }

  private static OrderType adaptOrderType(String type) {

    return type.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  private static void checkException(BTCTradeResult result) {

    if (!result.isSuccess()) {
      throw new ExchangeException(result.getMessage());
    }
  }

  public static boolean adaptResult(BTCTradeResult result) {

    checkException(result);

    return true;
  }

  public static AccountInfo adaptAccountInfo(BTCTradeBalance balance) {

    checkException(balance);

    List<Wallet> wallets = new ArrayList<Wallet>(4);
    wallets.add(new Wallet(Currencies.BTC, balance.getBtcBalance().add(balance.getBtcReserved())));
    wallets.add(new Wallet(Currencies.LTC, balance.getLtcBalance().add(balance.getLtcReserved())));
    wallets.add(new Wallet(Currencies.DOGE, balance.getDogeBalance().add(balance.getDogeReserved())));
    wallets.add(new Wallet("YBC", balance.getYbcBalance().add(balance.getYbcReserved())));
    wallets.add(new Wallet(Currencies.CNY, balance.getCnyBalance().add(balance.getCnyReserved())));
    return new AccountInfo(null, wallets);
  }

  public static String adaptDepositAddress(BTCTradeWallet wallet) {

    checkException(wallet);

    return wallet.getAddress();
  }

  public static String adaptPlaceOrderResult(BTCTradePlaceOrderResult result) {

    checkException(result);

    return result.getId();
  }

  public static OpenOrders adaptOpenOrders(BTCTradeOrder[] btcTradeOrders, CurrencyPair currencyPair) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>(btcTradeOrders.length);
    for (BTCTradeOrder order : btcTradeOrders) {
      openOrders.add(adaptLimitOrder(order, currencyPair));
    }
    return new OpenOrders(openOrders);
  }

  private static LimitOrder adaptLimitOrder(BTCTradeOrder order, CurrencyPair currencyPair) {

    return new LimitOrder(adaptOrderType(order.getType()), order.getAmountOutstanding(), currencyPair, order.getId(), adaptDatetime(order.getDatetime()), order.getPrice());
  }

  public static Trades adaptTrades(BTCTradeOrder[] btcTradeOrders, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BTCTradeOrder order : btcTradeOrders) {
      for (com.xeiam.xchange.btctrade.dto.trade.BTCTradeTrade trade : order.getTrades()) {
        trades.add(adaptTrade(order, trade, currencyPair));
      }
    }
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(BTCTradeOrder order, com.xeiam.xchange.btctrade.dto.trade.BTCTradeTrade trade, CurrencyPair currencyPair) {

    return new Trade(adaptOrderType(order.getType()), trade.getAmount(), currencyPair, trade.getPrice(), adaptDatetime(trade.getDatetime()), trade.getTradeId(), order.getId());
  }

}
