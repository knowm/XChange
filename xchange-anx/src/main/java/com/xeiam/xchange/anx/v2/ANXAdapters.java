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
package com.xeiam.xchange.anx.v2;

import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWallet;
import com.xeiam.xchange.anx.v2.dto.account.polling.Wallets;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthUpdate;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXOrder;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTicker;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Various adapters for converting from anx DTOs to XChange DTOs
 */
public final class ANXAdapters {

  /**
   * private Constructor
   */
  private ANXAdapters() {

  }

  /**
   * Adapts a ANXAccountInfo to a AccountInfo
   * 
   * @param anxAccountInfo
   * @return
   */
  public static AccountInfo adaptAccountInfo(ANXAccountInfo anxAccountInfo) {

    // Adapt to XChange DTOs
    AccountInfo accountInfo = new AccountInfo(anxAccountInfo.getLogin(), anxAccountInfo.getTradeFee(), ANXAdapters.adaptWallets(anxAccountInfo.getWallets()));
    return accountInfo;
  }

  /**
   * Adapts a ANXOrder to a LimitOrder
   * 
   * @param price
   * @param currency
   * @param orderTypeString
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradedCurrency, String currency, String orderTypeString, String id, Date timestamp) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    String tradableIdentifier = tradedCurrency;
    String transactionCurrency = currency;
    CurrencyPair currencyPair = new CurrencyPair(tradableIdentifier, transactionCurrency);

    LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, id, timestamp, price);

    return limitOrder;

  }

  /**
   * Adapts a List of ANXOrders to a List of LimitOrders
   * 
   * @param anxOrders
   * @param currency
   * @param orderType
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<ANXOrder> anxOrders, String tradedCurrency, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (ANXOrder anxOrder : anxOrders) {
      limitOrders.add(adaptOrder(anxOrder.getAmount(), anxOrder.getPrice(), tradedCurrency, currency, orderType, id, new Date(anxOrder.getStamp() / 1000)));
    }

    return limitOrders;
  }

  public static List<LimitOrder> adaptOrders(ANXOpenOrder[] anxOpenOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (int i = 0; i < anxOpenOrders.length; i++) {
      limitOrders.add(adaptOrder(anxOpenOrders[i].getAmount().getValue(), anxOpenOrders[i].getPrice().getValue(), anxOpenOrders[i].getItem(), anxOpenOrders[i].getCurrency(), anxOpenOrders[i].getType(),
          anxOpenOrders[i].getOid(), new Date(anxOpenOrders[i].getDate() * 1000)));
    }

    return limitOrders;
  }

  /**
   * Adapts a ANX Wallet to a XChange Wallet
   * 
   * @param anxWallet
   * @return
   */
  public static Wallet adaptWallet(ANXWallet anxWallet) {

    if (anxWallet == null) { // use the presence of a currency String to indicate existing wallet at ANX
      return null; // an account maybe doesn't contain a ANXWallet
    }
    else {
//      BigMoney cash = MoneyUtils.parse(anxWallet.getBalance().getCurrency() + " " + anxWallet.getBalance().getValue());
      return new Wallet(anxWallet.getBalance().getCurrency(), anxWallet.getBalance().getValue());
    }

  }

  /**
   * Adapts a List of ANX Wallets to a List of XChange Wallets
   * 
   * @param anxWallets
   * @return
   */
  public static List<Wallet> adaptWallets(Wallets anxWallets) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (ANXWallet anxWallet : anxWallets.getANXWallets()) {
      Wallet wallet = adaptWallet(anxWallet);
      if (wallet != null) {
        wallets.add(wallet);
      }
    }
    return wallets;

  }

  /**
   * Adapts a ANXTrade to a Trade Object
   * 
   * @param anxTrade
   * @return
   */
  public static Trade adaptTrade(ANXTrade anxTrade) {
    OrderType orderType = anxTrade.getTradeType().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = new BigDecimal(anxTrade.getAmountInt()).divide(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    BigDecimal price = ANXUtils.getPrice(anxTrade.getPriceInt());

    String tradableIdentifier = anxTrade.getItem();
    String transactionCurrency = anxTrade.getPriceCurrency();

    CurrencyPair currencyPair = new CurrencyPair(tradableIdentifier, transactionCurrency);
    Date dateTime = DateUtils.fromMillisUtc(anxTrade.getTid() / 1000L); // Note: the getDate is not millisecond precise therefore we use getTid()!

    final String tradeId = String.valueOf(anxTrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, dateTime, tradeId, null);
  }

  public static OrderBookUpdate adaptDepthUpdate(ANXDepthUpdate anxDepthUpdate) {

    OrderType orderType = anxDepthUpdate.getTradeType().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal volume = new BigDecimal(anxDepthUpdate.getVolumeInt()).divide(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));

    String tradableIdentifier = anxDepthUpdate.getItem();
    String transactionCurrency = anxDepthUpdate.getCurrency();
    CurrencyPair currencyPair = new CurrencyPair(tradableIdentifier, transactionCurrency);

    BigDecimal price = ANXUtils.getPrice(anxDepthUpdate.getPriceInt());

    BigDecimal totalVolume = new BigDecimal(anxDepthUpdate.getTotalVolumeInt()).divide(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    Date date = new Date(anxDepthUpdate.getNow() / 1000);

    OrderBookUpdate orderBookUpdate = new OrderBookUpdate(orderType, volume, currencyPair, price, date, totalVolume);

    return orderBookUpdate;
  }

  /**
   * Adapts a ANXTrade[] to a Trades Object
   * 
   * @param anxTrades
   * @return
   */
  public static Trades adaptTrades(ANXTrade[] anxTrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (int i = 0; i < anxTrades.length; i++) {

      tradesList.add(adaptTrade(anxTrades[i]));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(ANXTicker anxTicker) {
      BigDecimal volume = anxTicker.getVol().getValue();
      BigDecimal last = anxTicker.getLast().getValue();
      BigDecimal bid = anxTicker.getBuy().getValue();
      BigDecimal ask = anxTicker.getSell().getValue();
      BigDecimal high = anxTicker.getHigh().getValue();
      BigDecimal low = anxTicker.getLow().getValue();
      Date timestamp = new Date(anxTicker.getNow());

      CurrencyPair currencyPair = null;

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();

  }
}
