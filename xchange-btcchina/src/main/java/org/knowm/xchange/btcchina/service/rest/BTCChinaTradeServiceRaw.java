package org.knowm.xchange.btcchina.service.rest;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChina;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaBuyIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaBuyStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaCancelIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaCancelStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetMarketDepthRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetArchivedOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetStopOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaSellIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaSellStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrderResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetStopOrderResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetStopOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.HttpStatusIOException;

/**
 * Implementation of the trade service for BTCChina.
 * <ul>
 * <li>Provides access to trade functions</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaTradeServiceRaw extends BTCChinaBaseService {

  private final Logger log = LoggerFactory.getLogger(BTCChinaTradeServiceRaw.class);

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCChinaTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * @see BTCChinaGetMarketDepthRequest#BTCChinaGetMarketDepthRequest(Integer, String)
   * @see BTCChina#getMarketDepth(si.mazi.rescu.ParamsDigest, si.mazi.rescu.SynchronizedValueFactory, BTCChinaGetMarketDepthRequest)
   */
  public BTCChinaGetMarketDepthResponse getMarketDepth(Integer limit, String market) throws IOException {

    BTCChinaGetMarketDepthRequest request = new BTCChinaGetMarketDepthRequest(limit, market);
    BTCChinaGetMarketDepthResponse response = btcChina.getMarketDepth(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * Get order status.
   *
   * @param id the order id.
   * @return order status.
   * @throws IOException indicates I/O exception.
   */
  public BTCChinaGetOrderResponse getBTCChinaOrder(int id) throws IOException {

    BTCChinaGetOrderRequest request = new BTCChinaGetOrderRequest(id);
    BTCChinaGetOrderResponse returnObject = btcChina.getOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(returnObject);
  }

  /**
   * Get order status.
   *
   * @param id the order id.
   * @param market BTCCNY | LTCCNY | LTCBTC
   * @return order status.
   * @throws IOException indicates I/O exception.
   */
  public BTCChinaGetOrderResponse getBTCChinaOrder(int id, String market) throws IOException {

    BTCChinaGetOrderRequest request = new BTCChinaGetOrderRequest(id, market);
    BTCChinaGetOrderResponse returnObject = btcChina.getOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(returnObject);
  }

  public BTCChinaGetOrderResponse getBTCChinaOrder(int id, String market, Boolean withdetail) throws IOException {

    BTCChinaGetOrderRequest request = new BTCChinaGetOrderRequest(id, market, withdetail);
    BTCChinaGetOrderResponse response = btcChina.getOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaGetOrdersRequest#BTCChinaGetOrdersRequest(Boolean, String, Integer, Integer)
   */
  public BTCChinaGetOrdersResponse getBTCChinaOrders(Boolean openOnly, String market, Integer limit, Integer offset) throws IOException {

    BTCChinaGetOrdersRequest request = new BTCChinaGetOrdersRequest(openOnly, market, limit, offset);
    BTCChinaGetOrdersResponse response = btcChina.getOrders(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaGetOrdersRequest#BTCChinaGetOrdersRequest(Boolean, String, Integer, Integer, Integer, Boolean)
   */
  public BTCChinaGetOrdersResponse getBTCChinaOrders(Boolean openOnly, String market, Integer limit, Integer offset, Integer since,
      Boolean withdetail) throws IOException {

    BTCChinaGetOrdersRequest request = new BTCChinaGetOrdersRequest(openOnly, market, limit, offset, since, withdetail);
    BTCChinaGetOrdersResponse response = btcChina.getOrders(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaGetArchivedOrdersRequest#BTCChinaGetArchivedOrdersRequest(String, Integer, Integer, Boolean)
   */
  public BTCChinaGetOrdersResponse getBTCChinaArchivedOrders(String market, Integer limit, Integer lessThanOrderId, Boolean withdetail) throws IOException {

    BTCChinaGetArchivedOrdersRequest request = new BTCChinaGetArchivedOrdersRequest(market, limit, lessThanOrderId, withdetail);
    BTCChinaGetOrdersResponse response = btcChina.getArchivedOrders(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * Place a buy order.
   *
   * @param price The price in quote currency to buy 1 base currency. Max 2 decimals for BTC/CNY and LTC/CNY markets. 4 decimals for LTC/BTC market.
   * Market order is executed by setting price to 'null'.
   * @param amount The amount of LTC/BTC to buy. Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param market [ BTCCNY | LTCCNY | LTCBTC ]
   * @return order ID.
   * @throws IOException
   */
  public BTCChinaIntegerResponse buy(BigDecimal price, BigDecimal amount, String market) throws IOException {

    BTCChinaBuyOrderRequest request = new BTCChinaBuyOrderRequest(price, amount, market);
    final BTCChinaIntegerResponse response;
    try {
      response = btcChina.buyOrder2(signatureCreator, exchange.getNonceFactory(), request);
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == 401) {
        log.error("{}, request: {}, response: {}", e.getMessage(), request, e.getHttpBody());
      }
      throw e;
    }
    return checkResult(response);
  }

  /**
   * Place a sell order.
   *
   * @param price The price in quote currency to sell 1 base currency. Max 2 decimals for BTC/CNY and LTC/CNY markets. 4 decimals for LTC/BTC market.
   * Market order is executed by setting price to 'null'.
   * @param amount The amount of LTC/BTC to sell. Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param market [ BTCCNY | LTCCNY | LTCBTC ]
   * @return order ID.
   * @throws IOException
   */
  public BTCChinaIntegerResponse sell(BigDecimal price, BigDecimal amount, String market) throws IOException {

    BTCChinaSellOrderRequest request = new BTCChinaSellOrderRequest(price, amount, market);
    final BTCChinaIntegerResponse response;
    try {
      response = btcChina.sellOrder2(signatureCreator, exchange.getNonceFactory(), request);
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == 401) {
        log.error("{}, request: {}, response: {}", e.getMessage(), request, e.getHttpBody());
      }
      throw e;
    }
    return checkResult(response);
  }

  /**
   * @return BTCChinaBooleanResponse of limit order cancellation status.
   */
  public BTCChinaBooleanResponse cancelBTCChinaOrder(int id) throws IOException {

    return checkResult(btcChina.cancelOrder(signatureCreator, exchange.getNonceFactory(), new BTCChinaCancelOrderRequest(id)));
  }

  public BTCChinaTransactionsResponse getTransactions() throws IOException {

    return checkResult(btcChina.getTransactions(signatureCreator, exchange.getNonceFactory(), new BTCChinaTransactionsRequest()));
  }

  /**
   * @see BTCChinaTransactionsRequest#BTCChinaTransactionsRequest(String, Integer, Integer, Integer, String)
   */
  public BTCChinaTransactionsResponse getTransactions(String type, Integer limit, Integer offset, Integer since,
      String sincetype) throws IOException {

    BTCChinaTransactionsRequest request = new BTCChinaTransactionsRequest(type, limit, offset, since, sincetype);
    BTCChinaTransactionsResponse response = btcChina.getTransactions(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaIntegerResponse buyIcebergOrder(BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance,
      String market) throws IOException {

    BTCChinaBuyIcebergOrderRequest request = new BTCChinaBuyIcebergOrderRequest(price, amount, disclosedAmount, variance, market);
    BTCChinaIntegerResponse response = btcChina.buyIcebergOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaIntegerResponse sellIcebergOrder(BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance,
      String market) throws IOException {

    BTCChinaSellIcebergOrderRequest request = new BTCChinaSellIcebergOrderRequest(price, amount, disclosedAmount, variance, market);
    BTCChinaIntegerResponse response = btcChina.sellIcebergOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaGetIcebergOrderResponse getIcebergOrders(int id, String market) throws IOException {

    BTCChinaGetIcebergOrderRequest request = new BTCChinaGetIcebergOrderRequest(id, market);
    BTCChinaGetIcebergOrderResponse response = btcChina.getIcebergOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaGetIcebergOrdersResponse getIcebergOrders(Integer limit, Integer offset, String market) throws IOException {

    BTCChinaGetIcebergOrdersRequest request = new BTCChinaGetIcebergOrdersRequest(limit, offset, market);
    BTCChinaGetIcebergOrdersResponse response = btcChina.getIcebergOrders(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaBooleanResponse cancelIcebergOrder(int id, String market) throws IOException {

    BTCChinaCancelIcebergOrderRequest request = new BTCChinaCancelIcebergOrderRequest(id, market);
    BTCChinaBooleanResponse response = btcChina.cancelIcebergOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaBuyStopOrderRequest
   */
  public BTCChinaIntegerResponse buyStopOrder(BigDecimal stopPrice, BigDecimal price, BigDecimal amount, BigDecimal trailingAmount,
      BigDecimal trailingPercentage, String market) throws IOException {

    BTCChinaBuyStopOrderRequest request = new BTCChinaBuyStopOrderRequest(stopPrice, price, amount, trailingAmount, trailingPercentage, market);
    BTCChinaIntegerResponse response = btcChina.buyStopOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaSellStopOrderRequest
   */
  public BTCChinaIntegerResponse sellStopOrder(BigDecimal stopPrice, BigDecimal price, BigDecimal amount, BigDecimal trailingAmount,
      BigDecimal trailingPercentage, String market) throws IOException {

    BTCChinaSellStopOrderRequest request = new BTCChinaSellStopOrderRequest(stopPrice, price, amount, trailingAmount, trailingPercentage, market);
    BTCChinaIntegerResponse response = btcChina.sellStopOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaGetStopOrderRequest
   */
  public BTCChinaGetStopOrderResponse getStopOrder(int id, String market) throws IOException {

    BTCChinaGetStopOrderRequest request = new BTCChinaGetStopOrderRequest(id, market);
    BTCChinaGetStopOrderResponse response = btcChina.getStopOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaGetStopOrdersRequest
   */
  public BTCChinaGetStopOrdersResponse getStopOrders(String status, String type, BigDecimal stopPrice, Integer limit, Integer offset,
      String market) throws IOException {

    BTCChinaGetStopOrdersRequest request = new BTCChinaGetStopOrdersRequest(status, type, stopPrice, limit, offset, market);
    BTCChinaGetStopOrdersResponse response = btcChina.getStopOrders(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @see BTCChinaCancelStopOrderRequest
   */
  public BTCChinaBooleanResponse cancelStopOrder(int id, String market) throws IOException {

    BTCChinaCancelStopOrderRequest request = new BTCChinaCancelStopOrderRequest(id, market);
    BTCChinaBooleanResponse response = btcChina.cancelStopOrder(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

}
