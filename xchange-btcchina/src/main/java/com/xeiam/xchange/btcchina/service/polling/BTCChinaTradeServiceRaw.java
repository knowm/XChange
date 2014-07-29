package com.xeiam.xchange.btcchina.service.polling;

import static com.xeiam.xchange.btcchina.BTCChinaUtils.getNonce;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.HttpStatusIOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrders;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetMarketDepthRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrderResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import com.xeiam.xchange.dto.Order.OrderType;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the trade service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to trade functions</li>
 *         </ul>
 */
public class BTCChinaTradeServiceRaw extends BTCChinaBasePollingService<BTCChina> {

  private final Logger log = LoggerFactory.getLogger(BTCChinaTradeServiceRaw.class);

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTCChinaTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCChina.class, exchangeSpecification);
  }

  /**
   * @see BTCChinaGetMarketDepthRequest#BTCChinaGetMarketDepthRequest(Integer, String)
   * @see BTCChina#getMarketDepth(si.mazi.rescu.ParamsDigest, si.mazi.rescu.ValueFactory, BTCChinaGetMarketDepthRequest)
   */
  public BTCChinaGetMarketDepthResponse getMarketDepth(Integer limit, String market) throws IOException {

    BTCChinaGetMarketDepthRequest request = new BTCChinaGetMarketDepthRequest(limit, market);
    BTCChinaGetMarketDepthResponse response = btcChina.getMarketDepth(signatureCreator, tonce, request);
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
    BTCChinaGetOrderResponse returnObject = btcChina.getOrder(signatureCreator, getNonce(), request);
    return checkResult(returnObject);
  }

  /**
   * @deprecated Use {@link #getBTCChinaOrder(int)} instead.
   */
  @Deprecated
  public BTCChinaGetOrderResponse getBTCChinaOrder(long id) throws IOException {

    return getBTCChinaOrder((int) id);
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
    BTCChinaGetOrderResponse returnObject = btcChina.getOrder(signatureCreator, getNonce(), request);
    return checkResult(returnObject);
  }

  /**
   * @deprecated Use {@link #getBTCChinaOrder(int, String)} instead.
   */
  @Deprecated
  public BTCChinaGetOrderResponse getBTCChinaOrder(long id, String market) throws IOException {

    return getBTCChinaOrder((int) id, market);
  }

  /**
   * @return Set of BTCChina Orders
   * @throws IOException
   * @deprecated Use {@link #getBTCChinaOrders(Boolean, String, Integer, Integer)} instead.
   */
  @Deprecated
  public BTCChinaResponse<BTCChinaOrders> getBTCChinaOpenOrders() throws IOException {

    return checkResult(btcChina.getOrders(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaGetOrdersRequest()));
  }

  /**
   * @see BTCChinaGetOrdersRequest#BTCChinaGetOrdersRequest(Boolean, String, Integer, Integer)
   */
  public BTCChinaGetOrdersResponse getBTCChinaOrders(Boolean openOnly, String market, Integer limit, Integer offset) throws IOException {

    BTCChinaGetOrdersRequest request = new BTCChinaGetOrdersRequest(openOnly, market, limit, offset);
    BTCChinaGetOrdersResponse response = btcChina.getOrders(signatureCreator, BTCChinaUtils.getNonce(), request);
    return checkResult(response);
  }

  /**
   * @return BTCChinaIntegerResponse of new limit order status.
   * @deprecated use {@link #buy(BigDecimal, BigDecimal, String)} or {@link #sell(BigDecimal, BigDecimal, String)} instead.
   */
  @Deprecated
  public BTCChinaIntegerResponse placeBTCChinaLimitOrder(BigDecimal price, BigDecimal amount, OrderType orderType) throws IOException {

    BTCChinaIntegerResponse response = null;

    if (orderType == OrderType.BID) {

      response = btcChina.buyOrder2(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaBuyOrderRequest(price, amount));
    }
    else {
      response = btcChina.sellOrder2(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaSellOrderRequest(price, amount));
    }

    return checkResult(response);
  }

  /**
   * Place a buy order.
   *
   * @param price The price in quote currency to buy 1 base currency.
   *          Max 2 decimals for BTC/CNY and LTC/CNY markets.
   *          4 decimals for LTC/BTC market.
   *          Market order is executed by setting price to 'null'.
   * @param amount The amount of LTC/BTC to buy.
   *          Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param market [ BTCCNY | LTCCNY | LTCBTC ]
   * @return order ID.
   * @throws IOException
   */
  public BTCChinaIntegerResponse buy(BigDecimal price, BigDecimal amount, String market) throws IOException {

    BTCChinaBuyOrderRequest request = new BTCChinaBuyOrderRequest(price, amount, market);
    final BTCChinaIntegerResponse response;
    try {
      response = btcChina.buyOrder2(signatureCreator, BTCChinaUtils.getNonce(), request);
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
   * @param price The price in quote currency to sell 1 base currency.
   *          Max 2 decimals for BTC/CNY and LTC/CNY markets.
   *          4 decimals for LTC/BTC market.
   *          Market order is executed by setting price to 'null'.
   * @param amount The amount of LTC/BTC to sell.
   *          Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param market [ BTCCNY | LTCCNY | LTCBTC ]
   * @return order ID.
   * @throws IOException
   */
  public BTCChinaIntegerResponse sell(BigDecimal price, BigDecimal amount, String market) throws IOException {

    BTCChinaSellOrderRequest request = new BTCChinaSellOrderRequest(price, amount, market);
    final BTCChinaIntegerResponse response;
    try {
      response = btcChina.sellOrder2(signatureCreator, BTCChinaUtils.getNonce(), request);
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

    return checkResult(btcChina.cancelOrder(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaCancelOrderRequest(id)));
  }

  /**
   * @deprecated Use {@link #cancelBTCChinaOrder(int)} instead.
   */
  public BTCChinaBooleanResponse cancelBTCChinaOrder(String orderId) throws IOException {
    
    return cancelBTCChinaOrder(Integer.parseInt(orderId));
  }

  public BTCChinaTransactionsResponse getTransactions() throws IOException {

    return checkResult(btcChina.getTransactions(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaTransactionsRequest()));
  }

  /**
   * @see BTCChinaTransactionsRequest#BTCChinaTransactionsRequest(String, Integer, Integer)
   */
  public BTCChinaTransactionsResponse getTransactions(String type, Integer limit, Integer offset) throws IOException {

    BTCChinaTransactionsRequest request = new BTCChinaTransactionsRequest(type, limit, offset);
    BTCChinaTransactionsResponse response = btcChina.getTransactions(signatureCreator, BTCChinaUtils.getNonce(), request);
    return checkResult(response);
  }

  public BTCChinaIntegerResponse buyIcebergOrder(BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance, String market) throws IOException {

    BTCChinaBuyIcebergOrderRequest request = new BTCChinaBuyIcebergOrderRequest(price, amount, disclosedAmount, variance, market);
    BTCChinaIntegerResponse response = btcChina.buyIcebergOrder(signatureCreator, tonce, request);
    return checkResult(response);
  }

  public BTCChinaIntegerResponse sellIcebergOrder(BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance, String market) throws IOException {

    BTCChinaSellIcebergOrderRequest request = new BTCChinaSellIcebergOrderRequest(price, amount, disclosedAmount, variance, market);
    BTCChinaIntegerResponse response = btcChina.sellIcebergOrder(signatureCreator, tonce, request);
    return checkResult(response);
  }

  public BTCChinaGetIcebergOrderResponse getIcebergOrders(int id, String market) throws IOException {

    BTCChinaGetIcebergOrderRequest request = new BTCChinaGetIcebergOrderRequest(id, market);
    BTCChinaGetIcebergOrderResponse response = btcChina.getIcebergOrder(signatureCreator, tonce, request);
    return checkResult(response);
  }

  public BTCChinaGetIcebergOrdersResponse getIcebergOrders(Integer limit, Integer offset, String market) throws IOException {

    BTCChinaGetIcebergOrdersRequest request = new BTCChinaGetIcebergOrdersRequest(limit, offset, market);
    BTCChinaGetIcebergOrdersResponse response = btcChina.getIcebergOrders(signatureCreator, tonce, request);
    return checkResult(response);
  }

  public BTCChinaBooleanResponse cancelIcebergOrder(int id, String market) throws IOException {

    BTCChinaCancelIcebergOrderRequest request = new BTCChinaCancelIcebergOrderRequest(id, market);
    BTCChinaBooleanResponse response = btcChina.cancelIcebergOrder(signatureCreator, tonce, request);
    return checkResult(response);
  }

}
