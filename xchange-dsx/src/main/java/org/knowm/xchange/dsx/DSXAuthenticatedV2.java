package org.knowm.xchange.dsx;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.dsx.dto.account.DSXAccountInfoReturn;
import org.knowm.xchange.dsx.dto.account.DSXCryptoDepositAddressReturn;
import org.knowm.xchange.dsx.dto.account.DSXCryptoWithdrawReturn;
import org.knowm.xchange.dsx.dto.account.DSXFiatWithdrawReturn;
import org.knowm.xchange.dsx.dto.account.DSXTransactionReturn;
import org.knowm.xchange.dsx.dto.account.DSXTransactionStatusReturn;
import org.knowm.xchange.dsx.dto.trade.DSXActiveOrdersReturn;
import org.knowm.xchange.dsx.dto.trade.DSXCancelAllOrdersReturn;
import org.knowm.xchange.dsx.dto.trade.DSXCancelOrderReturn;
import org.knowm.xchange.dsx.dto.trade.DSXOrder;
import org.knowm.xchange.dsx.dto.trade.DSXOrderHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTradeReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXFeesReturn;
import org.knowm.xchange.dsx.dto.trade.DSXOrderStatusReturn;
import org.knowm.xchange.dsx.dto.trade.DSXVolumeReturn;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Mikhail Wall
 */

@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface DSXAuthenticatedV2 extends DSX {

  /**
   * This method provides information about user balance, server time, active orders count, rights of the currently used keys.
   *
   * @return {"success": 1,"return": {"funds": {"BTC": {"total": 100,"available": 95},"USD": {"total": 10000,"available": 9995},"EUR":
   * {"total": 1000,"available": 995},"LTC": {"total": 1000,"available": 995}},"rights": {"info": 1,"trade": 1},"transactionCount": 0,"openOrders":
   * 0,"serverTime": 1496669169}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/info/account")
  DSXAccountInfoReturn getInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * This method provides information about active user orders.
   *
   * All parameters are obligatory (ie. none may be null)
   *
   * @param pair the pair to display the orders eg. btcusd
   * @return {"success": 1,"return": {"0": {"pair": "btcusd","type": "buy","remainingVolume": 10,"volume": 10,"rate": 1000.000,
   * "timestampCreated": 1496670,"status": 0,"orderType": "limit"}}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/orders")
  DSXActiveOrdersReturn getActiveOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("pair") String pair) throws IOException;

  /**
   * This method provides information about user transactions history.
   *
   * All parameters are nullable
   *
   * @param count Number of transactions to display. Default value is 1000
   * @param fromId ID of the first transaction of the selection
   * @param endId ID of the last transaction of the selection
   * @param order Order in which transactions are displayed. Possible values: "asc" — from first to last, "desc" — from last to first. Default
   * value is "desc"
   * @param since Time from which start selecting transaction by transaction time(UNIX time). If this value is not null order will become "ASK"
   * @param end Time to which start selecting transaction by transaction time(UNIX time). If this value is not null order will become "ASK"
   * @param type Type of transaction. Possible values: "Withdraw" — withdraw "Incoming" — deposit. Default value is all types.
   * @param status Transaction status. Default value is all statuses. Possible values: 1 - Failed, 2 - Completed, 3 - Processing, 4 - Rejected
   * @param currency Transactions currency. Default value is all currencies
   * @return {"success": 1,"return": {"1": {"id": 1,"timestamp": 11,"type": "Withdraw","amount": 1,"currency": "btc","confirmationsCount": 6,
   * "address": "address","status": 2,"commission": 0.0001}}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/history/transactions")
  DSXTransHistoryReturn TransHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("count") Long count,
      @FormParam("fromId") Long fromId, @FormParam("endId") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("type") DSXTransHistoryResult.Type type, @FormParam("status") DSXTransHistoryResult.Status status,
      @FormParam("currency") String currency) throws IOException;

  /**
   * This method provides information about user trades history.
   *
   * All parameters are nullable
   *
   * @param count Number of trades to display
   * @param fromId ID of the first trade of the selection
   * @param endId ID of the last trade of the selection
   * @param order Order in which trades are displayed. Possible values: "asc" — from first to last, "desc" — from last to first. Default
   * value is "desc"
   * @param since Time from which start selecting trades by trade time(UNIX time). If this value is not null order will become "asc"
   * @param end Time to which start selecting trades by trade time(UNIX time). If this value is not null order will become "asc"
   * @param pair Currency pair
   * @return {"success": 1,"return": {"0": {"pair": "btcusd","type": "buy","amount": 1,"rate": 1000.000,"orderId": 1,"timestamp": 1496671,
   * "commission": 0.001,"commissionCurrency": "btc"}}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/history/trades")
  DSXTradeHistoryReturn TradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("fromId") Long fromId, @FormParam("endId") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair) throws IOException;

  /**
   * This method provides information about user orders history.
   *
   * All parameters are nullable
   *
   * @param count Number of orders to display
   * @param fromId ID of the first order of the selection
   * @param endId ID of the last order of the selection
   * @param order Order in which orders shown. Possible values: "asc" — from first to last, "desc" — from last to first. Default value is "desc"
   * @param since Time from which start selecting orders by trade time(UNIX time). If this value is not null order will become "asc".
   * @param end Time to which start selecting orders by trade time(UNIX time). If this value is not null order will become "asc".
   * @param pair Currency pair.
   * @return {"success": 1,"return": {"0": {"pair": "btcusd","type": "buy","remainingVolume": 10,"volume": 10,"rate": 1000.000,
   * "timestampCreated": 1496670,"status": 0,"orderType": "limit"}}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/history/orders")
  DSXOrderHistoryReturn OrderHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("fromId") Long fromId, @FormParam("endId") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair) throws IOException;

  /**
   * This method provides trade operation. User can place limit, market, fill-or-kill orders. If you place fill-or-kill or market order, send any
   * random number for rate param
   *
   * All parameters, except rate param, are obligatory (ie. none may be null)
   *
   * @param type The transaction type (buy or sell)
   * @param rate The price to buy/sell
   * @param volume The amount which is necessary to buy/sell
   * @param pair pair, eg. btcusd
   * @param orderType The order type: limit, market, or fill-or-kill
   * @return {"success": 1,"return": {"received": 0,"remains": 10,"funds": {"BTC": {"total": 100,"available": 95},"USD": {"total": 10000,"available": 9995},
   * "EUR": {"total": 1000,"available": 995},"LTC": {"total": 1000,"available": 995}},"orderId": 0}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/order/new")
  DSXTradeReturn Trade(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("type") DSXOrder.Type type, @FormParam("rate") BigDecimal rate, @FormParam("volume") BigDecimal volume,
      @FormParam("pair") String pair, @FormParam("orderType") DSXOrder.OrderType orderType) throws IOException;

  /**
   * This method provides cancelling active order operation.
   *
   * All parameters are obligatory (ie. none may be null)
   *
   * @param orderId Id of order to cancel
   * @return {"success": 1,"return": {"funds": {"BTC": {"total": 100,"available": 95},"USD": {"total": 10000,"available": 9995},
   * "EUR": {"total": 1000,"available": 995},"LTC": {"total": 1000,"available": 995}},"orderId": 1}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/order/cancel")
  DSXCancelOrderReturn CancelOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("orderId") Long orderId) throws IOException;

  /**
   * This method provides cancelling all active order operation
   *
   * @return {"success": 1,"return": {"funds": {"BTC": {"total": 100,"available": 95},"USD": {"total": 10000,"available": 9995},"EUR":
   * {"total": 1000,"available": 995},"LTC": {"total": 1000,"available": 995}},"orderId": 1}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/order/cancel/all")
  DSXCancelAllOrdersReturn cancelAllOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * This method provides order status and related deals to order
   *
   * All parameters are obligatory (ie. none may be null)
   *
   * @param orderId Id of order to show status
   * @return {"success": 1,"return": {"pair": "btcusd","type": "buy","remainingVolume": 10,"volume": 10,"rate": 1000.000,"timestampCreated": 1496670,
   * "status": 0,"orderType": "limit","deals": [{"pair": "btcusd","type": "buy","amount": 1,"rate": 1000.000,"orderId": 1,"timestamp": 1496672724,
   * "commission": 0.001,"commissionCurrency": "btc"}]}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/order/status")
  DSXOrderStatusReturn getOrderStatus(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("orderId") Long orderId) throws IOException;

  /**
   * This method provides fees for current user
   *
   * @return {"success": 1,"return": {"progressiveCommissions": {"commissions": [{"tradingVolume": 0.03,"takerCommission": 0.03,"makerCommission": 0.03}],
   * "indexOfCurrentCommission": 0}}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/fees")
  DSXFeesReturn getFees(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  /**
   * This method provides trading volume for user
   *
   * @return {"success": 1,"return": {"tradingVolume": 0,"tradesCount": 0,"currency": "USD"}}
   * @throws IOException
   */
  @POST
  @Path("tapi/v2/volume")
  DSXVolumeReturn getVolume(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  /**
   * This method provides address for depositing cryptocurrency.
   *
   * @param currency Cryptocurrency which you want to deposit. Required.
   * @param newAddress Do you want to get new crypto address or you want to receive previous address.
   * 1 - generate new address. 0 - get old address. Default value is 0.
   * @return {"success": 1,"return": {"address": "crypto-address","currency": "BTC","new": 0}}
   * @throws IOException
   */
  @POST
  @Path("dwapi/v2/deposit/cryptoaddress")
  DSXCryptoDepositAddressReturn getCryptoDepositAddress(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @DefaultValue("0") @FormParam("newAddress") int newAddress)
      throws IOException;

  /**
   * This method provides preparing for submitting crypto withdraw
   *
   * All parameters are obligatory (ie. none may be null)
   *
   * @param currency Cryptocurrency name
   * @param address Crypto address
   * @param amount Amount of cryptocurrency you want to withdraw
   * @param commission Amount of cryptocurrency you want to spend as network fee. If not sent - default commission will be used
   * @return {"success": 1,"return": {"transactionId": 1}}
   * @throws IOException
   */
  @POST
  @Path("dwapi/v2/withdraw/crypto")
  DSXCryptoWithdrawReturn cryptoWithdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @FormParam("address") String address,
      @FormParam("amount") BigDecimal amount, @FormParam("commission") BigDecimal commission) throws IOException;

  /**
   * This method provides submitting withdraw by transactionId, which we got from crypto or fiat withdraw
   *
   * All parameters are obligatory (ie. none may be null)
   *
   * @param transactionId Id of transaction which you want to submit
   * @return {"success": 1,"return": {"id": 1,"timestamp": 1496673,"type": "Incoming","amount": 10,"currency": "btc","confirmationsCount": 6,
   * "address": "123123","status": 2,"commission": 0.01}}
   * @throws IOException
   */
  @POST
  @Path("dwapi/v2/withdraw/submit")
  DSXTransactionReturn submitWithdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("transactionId") Long transactionId) throws IOException;

  /**
   * This method provides preparing for submitting crypto withdraw
   *
   * All parameters are obligatory (ie. none may be null)
   * @param currency Fiat currency name
   * @param amount Amount of fiat you want to withdraw
   * @return {"success": 1,"return": {"transactionId": 1}}
   * @throws IOException
   */
  @POST
  @Path("dwapi/v2/withdraw/fiat")
  DSXFiatWithdrawReturn fiatWithdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount) throws IOException;

  /**
   * This method provides cancelling processing withdraw
   * <p>
   * All parameters are obligatory (ie. none may be null)
   *
   * @param transactionId Id of transaction which you want to cancel
   * @return {"success": 1,"return": {"id": 1,"timestamp": 1496673,"type": "Incoming","amount": 10,"currency": "btc","confirmationsCount": 6,
   * "address": "123123","status": 2,"commission": 0.01}}
   */
  @POST
  @Path("dwapi/v2/withdraw/cancel")
  DSXTransactionReturn cancelWithdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("transactionId") Long transactionId);

  /**
   * This method provides information about single user transaction.
   *
   * @param id Transaction ID. Required.
   * @return {"success": 1,"return": {"id": 1,"timestamp": 1496673,"type": "Incoming","amount": 10,"currency": "btc","confirmationsCount": 6,
   * "address": "123123","status": 2,"commission": 0.01}}
   * @throws IOException
   */
  @POST
  @Path("dwapi/v2/transaction/status")
  DSXTransactionStatusReturn getTransactionsStatus(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce")
      SynchronizedValueFactory<Long> nonce, @FormParam("id") Long id) throws IOException;

  enum SortOrder {
    ASC, DESC
  }
}
