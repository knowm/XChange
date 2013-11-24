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
package com.xeiam.xchange.bitcoinium;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitcoinium DTOs to XChange DTOs
 */
public final class BitcoiniumAdapters {

  /**
   * private Constructor
   */
  private BitcoiniumAdapters() {

  }

  /**
   * Adapts a BitcoiniumOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String currency, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    String tradableIdentifier = Currencies.BTC;
    BigMoney limitPrice = MoneyUtils.parse(currency + " " + price);

    return new LimitOrder(orderType, amount, tradableIdentifier, currency, limitPrice);

  }

  /**
   * Adapts a List of bitcoiniumOrders to a List of LimitOrders
   * 
   * @param bitcoiniumOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(BitcoiniumOrderbook bitcoiniumOrders, String currency, String orderType, String id) {

    int listSize = 0;
	ArrayList<BigDecimal> priceList;
	ArrayList<BigDecimal> amountList;

    if(orderType.equals("ask")){
    	priceList = bitcoiniumOrders.getAskPriceList();
    	amountList = bitcoiniumOrders.getAskVolumeList();  
    } else {
    	priceList = bitcoiniumOrders.getBidPriceList();
    	amountList = bitcoiniumOrders.getBidVolumeList();
    }
	listSize = priceList.size();
    
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (int i = 0; i < listSize; i++) {
    	limitOrders.add(adaptOrder(amountList.get(i), priceList.get(i), currency, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a BitcoiniumTickerHistory[] to a Trades Object
   * 
   * @param TradesList The Bitcoinium trade data
   * @return The trades
   */
  public static Trades adaptTrades(BitcoiniumTickerHistory bitcoiniumTrades, String currency, String tradableIdentifier) {

    List<Trade> tradesList = new ArrayList<Trade>();
    long baseTime = bitcoiniumTrades.getBaseTimestamp();

    for (int i = 0; bitcoiniumTrades.getPriceHistoryList().size() > i; i++) {
    	
    	BigMoney price = MoneyUtils.parse(currency + " " + bitcoiniumTrades.getPriceHistoryList().get(i));
    	
    	// Get the date by adding the time delta of each trade to base timestamp
    	long delta = bitcoiniumTrades.getTimeStampOffsets().get(i).longValue();
    	baseTime += delta;
    	Date date = DateUtils.fromMillisUtc(baseTime* 1000L);
    	
    	tradesList.add(new Trade(null, null, tradableIdentifier, currency, price, date, 0L));
    }
    return new Trades(tradesList);
  }

  public static String getPriceString(BigMoney price) {

    return price.getAmount().stripTrailingZeros().toPlainString();
  }

  /**
   * Adapts a BitcoiniumTicker to a Ticker Object
   * 
   * @param bitcoiniumTicker
   * @return
   */
  public static Ticker adaptTicker(BitcoiniumTicker bitcoiniumTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getLast());
    BigMoney high = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getHigh());
    BigMoney low = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getLow());
    BigMoney ask = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getAsk());
    BigMoney bid = MoneyUtils.parse(currency + " " + bitcoiniumTicker.getBid());
    BigDecimal volume = bitcoiniumTicker.getVolume();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withHigh(high).withLow(low).withVolume(volume).withAsk(ask).withBid(bid).build();
  }

}
