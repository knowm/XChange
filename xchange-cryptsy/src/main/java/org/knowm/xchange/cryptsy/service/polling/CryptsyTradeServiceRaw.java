package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCalculatedFeesReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCancelMultipleOrdersReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCancelOrderReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyPlaceOrderReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author ObsessiveOrange
 */
public class CryptsyTradeServiceRaw extends CryptsyBasePollingService {

  /**
   * @param exchange
   */
  public CryptsyTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Gets the trade history for a single market
   *
   * @param marketID marketID for which this TradeHistory request will be executed against
   * @param args - limit (int) of how many past trades should be shown (defualt: 1000)
   * @return CryptsyTradeHistoryReturn DTO representing results of this request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyTradeHistoryReturn getCryptsySingleMarketTradeHistory(int marketID, int... args) throws IOException, ExchangeException {

    int limit = 1000; // default value
    if (args.length > 0) {
      limit = args[0];
    }

    return checkResult(cryptsyAuthenticated.mytrades(apiKey, signatureCreator, exchange.getNonceFactory(), marketID, limit));
  }

  /**
   * Gets the trade history for a all markets
   *
   * @param startDate start date to get trade history from
   * @param endDate end date to get trade history until
   * @return CryptsyTradeHistoryReturn DTO representing results of this request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyTradeHistoryReturn getCryptsyTradeHistory(Date startDate, Date endDate) throws IOException, ExchangeException {

    SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");

    return checkResult(cryptsyAuthenticated.allmytrades(apiKey, signatureCreator, exchange.getNonceFactory(),
        (startDate == null ? null : outputFormatter.format(startDate)), (endDate == null ? null : outputFormatter.format(endDate))));
  }

  /**
   * Gets the open orders in a specific market
   *
   * @param marketID marketID for which this OpenOrders request will be executed against
   * @return CryptsyOpenOrdersReturn DTO representing open order for that market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyOpenOrdersReturn getCryptsySingleMarketOpenOrders(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.myorders(apiKey, signatureCreator, exchange.getNonceFactory(), marketID));
  }

  /**
   * Gets the open orders in all markets
   *
   * @return CryptsyOpenOrdersReturn DTO representing open order for that market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyOpenOrdersReturn getCryptsyOpenOrders() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.allmyorders(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  /**
   * Places a limit order based on parameters given
   *
   * @param marketID marketID for this order to be placed in
   * @param orderType Order type (CryptsyOrderType.Buy or CryptsyOrderType.Sell)
   * @param quantity BigDecimal represenation of the quantity to trade
   * @param price BigDecimal represenation of the limit price
   * @return CryptsyPlaceOrderReturn DTO representing the transactionID
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyPlaceOrderReturn placeCryptsyLimitOrder(int marketID, CryptsyOrderType orderType, BigDecimal quantity, BigDecimal price)
      throws IOException, ExchangeException {

    return checkResult(
        cryptsyAuthenticated.createorder(apiKey, signatureCreator, exchange.getNonceFactory(), marketID, orderType.toString(), quantity, price));
  }

  /**
   * Cancels a order of given orderID
   *
   * @param orderID ID of order to cancel
   * @return CryptsyCancelOrderReturn DTO representing result of request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyCancelOrderReturn cancelSingleCryptsyLimitOrder(int orderID) throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.cancelorder(apiKey, signatureCreator, exchange.getNonceFactory(), orderID));
  }

  /**
   * Cancels all orders in given marketID
   *
   * @param marketID ID of market in which all orders should be cancelled
   * @return CryptsyCancelMultipleOrdersReturn DTO representing result of request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyCancelMultipleOrdersReturn cancelMarketCryptsyLimitOrders(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.cancelmarketorders(apiKey, signatureCreator, exchange.getNonceFactory(), marketID));
  }

  /**
   * Cancels all orders across all markets
   *
   * @return CryptsyCancelMultipleOrdersReturn DTO representing result of request
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyCancelMultipleOrdersReturn cancelAllCryptsyLimitOrders() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.cancelallorders(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  /**
   * Estimates fees and net trade volume
   *
   * @param orderType Order type (CryptsyOrderType.Buy or CryptsyOrderType.Sell)
   * @param quantity BigDecimal represenation of the quantity to trade
   * @param price BigDecimal represenation of the limit price
   * @return CryptsyCalculatedFeesReturn DTO representing results of calculation
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyCalculatedFeesReturn calculateCryptsyFees(CryptsyOrderType orderType, BigDecimal quantity, BigDecimal price)
      throws IOException, ExchangeException {

    return checkResult(
        cryptsyAuthenticated.calculatefees(apiKey, signatureCreator, exchange.getNonceFactory(), orderType.toString(), quantity, price));
  }

}
