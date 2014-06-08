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
package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.CryptsyAuthenticated;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCalculatedFeesReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCancelMultipleOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCancelOrderReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyPlaceOrderReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyTradeServiceRaw extends CryptsyBasePollingService<CryptsyAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CryptsyTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptsyAuthenticated.class, exchangeSpecification);
  }

  /**
   * Gets the trade history for a single market
   * 
   * @param marketID marketID for which this TradeHistory request will be executed against
   * @param args - limit (int) of how many past trades should be shown (defualt: 1000)
   * @return CryptsyTradeHistoryReturn DTO representing results of this request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyTradeHistoryReturn getCryptsySingleMarketTradeHistory(int marketID, int... args) throws IOException, ExchangeException {

    int limit = 1000; // default value
    if (args.length > 0) {
      limit = args[0];
    }

    return checkResult(cryptsy.mytrades(apiKey, signatureCreator, nextNonce(), marketID, limit));
  }

  /**
   * Gets the trade history for a all markets
   * 
   * @param startDate start date to get trade history from
   * @param endDate end date to get trade history until
   * @return CryptsyTradeHistoryReturn DTO representing results of this request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyTradeHistoryReturn getCryptsyTradeHistory(Date startDate, Date endDate) throws IOException, ExchangeException {

    SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");

    return checkResult(cryptsy.allmytrades(apiKey, signatureCreator, nextNonce(), outputFormatter.format(startDate), outputFormatter.format(endDate)));
  }

  /**
   * Gets the open orders in a specific market
   * 
   * @param marketID marketID for which this OpenOrders request will be executed against
   * @return CryptsyOpenOrdersReturn DTO representing open order for that market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyOpenOrdersReturn getCryptsySingleMarketOpenOrders(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsy.myorders(apiKey, signatureCreator, nextNonce(), marketID));
  }

  /**
   * Gets the open orders in all markets
   * 
   * @return CryptsyOpenOrdersReturn DTO representing open order for that market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyOpenOrdersReturn getCryptsyOpenOrders() throws IOException, ExchangeException {

    return checkResult(cryptsy.allmyorders(apiKey, signatureCreator, nextNonce()));
  }

  /**
   * Places a limit order based on parameters given
   * 
   * @param marketID marketID for this order to be placed in
   * @param orderType Order type (CryptsyOrderType.Buy or CryptsyOrderType.Sell)
   * @param quantity BigDecimal represenation of the quantity to trade
   * @param price BigDecimal represenation of the limit price
   * @return CryptsyPlaceOrderReturn DTO representing the transactionID
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyPlaceOrderReturn placeCryptsyLimitOrder(int marketID, CryptsyOrderType orderType, BigDecimal quantity, BigDecimal price) throws IOException, ExchangeException {

    return checkResult(cryptsy.createorder(apiKey, signatureCreator, nextNonce(), marketID, orderType.toString(), quantity, price));
  }

  /**
   * Cancels a order of given orderID
   * 
   * @param orderID ID of order to cancel
   * @return CryptsyCancelOrderReturn DTO representing result of request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyCancelOrderReturn cancelSingleCryptsyLimitOrder(int orderID) throws IOException, ExchangeException {

    return checkResult(cryptsy.cancelorder(apiKey, signatureCreator, nextNonce(), orderID));
  }

  /**
   * Cancels all orders in given marketID
   * 
   * @param marketID ID of market in which all orders should be cancelled
   * @return CryptsyCancelMultipleOrdersReturn DTO representing result of request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyCancelMultipleOrdersReturn cancelMarketCryptsyLimitOrders(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsy.cancelmarketorders(apiKey, signatureCreator, nextNonce(), marketID));
  }

  /**
   * Cancels all orders across all markets
   * 
   * @return CryptsyCancelMultipleOrdersReturn DTO representing result of request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyCancelMultipleOrdersReturn cancelAllCryptsyLimitOrders() throws IOException, ExchangeException {

    return checkResult(cryptsy.cancelallorders(apiKey, signatureCreator, nextNonce()));
  }

  /**
   * Estimates fees and net trade volume
   * 
   * @param orderType Order type (CryptsyOrderType.Buy or CryptsyOrderType.Sell)
   * @param quantity BigDecimal represenation of the quantity to trade
   * @param price BigDecimal represenation of the limit price
   * @return CryptsyCalculatedFeesReturn DTO representing results of calculation
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyCalculatedFeesReturn calculateCryptsyFees(CryptsyOrderType orderType, BigDecimal quantity, BigDecimal price) throws IOException, ExchangeException {

    return checkResult(cryptsy.calculatefees(apiKey, signatureCreator, nextNonce(), orderType.toString(), quantity, price));
  }

}
