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
package com.xeiam.xchange.bitcurex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitcurex DTOs to XChange DTOs
 */
public final class BitcurexAdapters {

  /**
   * private Constructor
   */
  private BitcurexAdapters() {

  }

  /**
   * Adapts a List of bitcurexOrders to a List of LimitOrders
   * 
   * @param bitcurexOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bitcurexOrders, CurrencyPair currencyPair, OrderType orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] bitcurexOrder : bitcurexOrders) {
      limitOrders.add(adaptOrder(bitcurexOrder[1], bitcurexOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a BitcurexOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, String id) {

    // place a limit order
    BigDecimal limitPrice = price;

    return new LimitOrder(orderType, amount, currencyPair, id, null, limitPrice);
  }

  /**
   * Adapts a BitcurexTrade to a Trade Object
   * 
   * @param bitcurexTrade A Bitcurex trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BitcurexTrade bitcurexTrade, CurrencyPair currencyPair) {

    BigDecimal amount = bitcurexTrade.getAmount();
    BigDecimal price = bitcurexTrade.getPrice();
    Date date = DateUtils.fromMillisUtc(bitcurexTrade.getDate() * 1000L);
    final String tradeId = String.valueOf(bitcurexTrade.getTid());

    return new Trade(bitcurexTrade.getType() == 1 ? OrderType.ASK : OrderType.BID, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BitcurexTrade[] to a Trades Object
   * 
   * @param bitcurexTrades The Bitcurex trade data
   * @return The trades
   */
  public static Trades adaptTrades(BitcurexTrade[] bitcurexTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BitcurexTrade bitcurexTrade : bitcurexTrades) {
      tradesList.add(adaptTrade(bitcurexTrade, currencyPair));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a BitcurexTicker to a Ticker Object
   * 
   * @param bitcurexTicker
   * @return
   */
  public static Ticker adaptTicker(BitcurexTicker bitcurexTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitcurexTicker.getLast();
    BigDecimal high = bitcurexTicker.getHigh();
    BigDecimal low = bitcurexTicker.getLow();
    BigDecimal buy = bitcurexTicker.getBuy();
    BigDecimal sell = bitcurexTicker.getSell();
    BigDecimal volume = bitcurexTicker.getVol();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).build();
  }

  /**
   * Adapts a BitcurexFunds to an AccountInfo Object
   * 
   * @param bitcurexFunds
   * @param user name for the accountInfo
   * @return
   */
  public static AccountInfo adaptAccountInfo(BitcurexFunds funds, String userName) {

	    // Adapt to XChange DTOs
	    Wallet eurWallet = new Wallet(Currencies.EUR, funds.getEurs());
	    Wallet btcWallet = new Wallet(Currencies.BTC, funds.getBtcs());

	    return new AccountInfo( userName, null, 
	    		                Arrays.asList(eurWallet, btcWallet));
	  }

}
