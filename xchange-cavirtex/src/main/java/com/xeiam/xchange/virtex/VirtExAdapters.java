/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.virtex;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExOrder;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.virtex.VirtExUtils;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.utils.MoneyUtils;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;

import org.joda.money.BigMoney;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Various adapters for converting from VirtEx DTOs to XChange DTOs
 */
public class VirtExAdapters {
	
	  /**
	   * Adapts a MtGoxOrder to a LimitOrder
	   * 
	   * @param mtGoxOrder
	   * @param currency
	   * @param orderTypeString
	   * @return
	   */
	  public static LimitOrder adaptOrder(long amount_int, double price, String currency, String orderTypeString, String id) {

	    // place a limit order
	    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
	    BigDecimal tradeableAmount = (new BigDecimal(amount_int).divide(new BigDecimal(VirtExUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)));
	    String tradableIdentifier = Currencies.BTC;
	    String transactionCurrency = currency;
	    BigMoney limitPrice = MoneyUtils.parseFiat(currency + " " + price);

	    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, tradableIdentifier, transactionCurrency, limitPrice);

	    return limitOrder;

	  }
	
	/**
	   * Adapts a List of MtGoxOrders to a List of LimitOrders
	   * 
	   * @param mtGoxOrders
	   * @param currency
	   * @param orderType
	   * @return
	   */
	  public static List<LimitOrder> adaptOrders(List<VirtExOrder> virtExOrders, String currency, String orderType, String id) {

	    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

	    for (VirtExOrder virtExOrder : virtExOrders) {
	      limitOrders.add(adaptOrder((long)virtExOrder.getAmount(), virtExOrder.getPrice(), currency, orderType, id));
	    }

	    return limitOrders;
	  }


  /**
   * Adapts a VirtExTrade to a Trade Object
   * 
   * @param VirtExTrade
   * @return
   */
  public static Trade adaptTrade(VirtExTrade virtExTrade) {

    OrderType orderType = virtExTrade.equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = new BigDecimal(virtExTrade.getAmount());
    //BigMoney price = MoneyUtils.parseFiat("CAD" + VirtExTrade.getPrice());
    BigMoney price = VirtExUtils.getPrice("CAD", virtExTrade.getPrice());

    DateTime dateTime = DateUtils.fromMillisUtc((long)virtExTrade.getDate() * 1000L);

    return new Trade(orderType, amount, null, "CAD", price, dateTime);
  }

  /**
   * Adapts a MtGoxTrade[] to a Trades Object
   * 
   * @param mtGoxTrades
   * @return
   */
  public static Trades adaptTrades(VirtExTrade[] virtexTrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (int i = 0; i < virtexTrades.length; i++) {

      tradesList.add(adaptTrade(virtexTrades[i]));
    }
    return new Trades(tradesList);
  }
  public static String getPriceString(BigMoney price) {

	    if (!price.getCurrencyUnit().toString().equals("JPY")) {
	      return price.getAmount().multiply(new BigDecimal(VirtExUtils.PRICE_INT_2_DECIMAL_FACTOR)).stripTrailingZeros().toPlainString();
	    } else { // JPY
	      return price.getAmount().multiply(new BigDecimal(VirtExUtils.JPY_PRICE_INT_2_DECIMAL_FACTOR)).stripTrailingZeros().toPlainString();
	    }
	  }

  public static Ticker adaptTicker(VirtExTicker virtExTicker) {

    BigMoney last = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getLast());
    BigMoney low = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getLow());
    BigMoney high = MoneyUtils.parseFiat("CAD" + " " + virtExTicker.getHigh());
    long volume = (long) virtExTicker.getVol();
    

    return new Ticker(last, low, high, "CAD", volume);
  }

}
