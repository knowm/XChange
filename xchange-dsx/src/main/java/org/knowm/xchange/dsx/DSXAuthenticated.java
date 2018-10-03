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
import org.knowm.xchange.dsx.dto.account.DSXTransaction;
import org.knowm.xchange.dsx.dto.account.DSXTransactionReturn;
import org.knowm.xchange.dsx.dto.account.DSXTransactionStatusReturn;
import org.knowm.xchange.dsx.dto.trade.DSXActiveOrdersReturn;
import org.knowm.xchange.dsx.dto.trade.DSXCancelOrderReturn;
import org.knowm.xchange.dsx.dto.trade.DSXOrder;
import org.knowm.xchange.dsx.dto.trade.DSXOrderHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTradeReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryReturn;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Mikhail Wall */
@Deprecated
@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface DSXAuthenticated extends DSX {

  /**
   * @return {success: 1, return: {funds: {usd: 200, btc: 4.757, ltc: 0, eur: 58, rub: 6780},
   *     rights: {info: 1, trade: 1}, transaction_count: 10, open_orders: 5, server_time:
   *     142123698}}
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#getInfo(String, ParamsDigest,
   *     SynchronizedValueFactory)}
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXAccountInfoReturn getInfo(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  /**
   * @param pair the pair to display the orders eg. btcusd
   * @return
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#ActiveOrders(String, ParamsDigest,
   *     SynchronizedValueFactory, String)}
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXActiveOrdersReturn ActiveOrders(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("pair") String pair)
      throws IOException;

  /**
   * @param from ID of the first transaction of the selection
   * @param count Number of transactions to display. Default value is 1000
   * @param fromId ID of the first transaction of the selection
   * @param endId ID of the last transaction of the selection
   * @param order Order in which transactions shown. Possible values: «asc» — from first to last,
   *     «desc» — from last to first. Default value is «DESK»
   * @param since Time from which start selecting transaction by transaction time(UNIX time). If
   *     this value is not null order will become «ASK»
   * @param end Time to which start selecting transaction by transaction time(UNIX time). If this
   *     value is not null order will become «ASK»
   * @return {success: 1, return: {1000: {id:1000, type: 3, amount: 2.5, currency: USD, desc:
   *     Income, status: 2, timestamp: 142123698, commission:1.0, address: address string},}}
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#TransHistory(String, ParamsDigest,
   *     SynchronizedValueFactory, Long, Long, Long, DSXAuthenticatedV2.SortOrder, * Long, Long,
   *     String, Integer, String)} All parameters are nullable
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXTransHistoryReturn TransHistory(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("from") Long from,
      @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("from_id") Long fromId,
      @FormParam("end_id") Long endId,
      @FormParam("order") SortOrder order,
      @FormParam("since") Long since,
      @FormParam("end") Long end)
      throws IOException;

  /**
   * @param from ID of the first trade of the selection
   * @param count Number of trades to display
   * @param fromId ID of the first trade of the selection
   * @param endId ID of the last trade of the selection
   * @param order Order in which transactions shown. Possible values: «asc» — from first to last,
   *     «desc» — from last to first. Default value is «desc»
   * @param since Time from which start selecting trades by trade time(UNIX time). If this value is
   *     not null order will become «asc»
   * @param end Time to which start selecting trades by trade time(UNIX time). If this value is not
   *     null order will become «asc»
   * @param pair Currency pair
   * @return {success: 1, return: {1000: {pair: btcusd, type: buy, amount: 10, rate: 300, order_id:
   *     576, is_your_order: 1, timestamp: 142123698},}}
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#TradeHistory(String, ParamsDigest,
   *     SynchronizedValueFactory, Long, Long, Long, DSXAuthenticatedV2.SortOrder, * Long, Long,
   *     String)} All parameters are nullable
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXTradeHistoryReturn TradeHistory(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("from") Long from,
      @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("from_id") Long fromId,
      @FormParam("end_id") Long endId,
      @FormParam("order") SortOrder order,
      @FormParam("since") Long since,
      @FormParam("end") Long end,
      @FormParam("pair") String pair)
      throws IOException;

  /**
   * @param count Number of orders to display
   * @param fromId ID of the first order of the selection
   * @param endId ID of the last order of the selection
   * @param order Order in which orders shown. Possible values: «asc» — from first to last, «desc» —
   *     from last to first. Default value is «desc»
   * @param since Time from which start selecting orders by trade time(UNIX time). If this value is
   *     not null order will become «asc».
   * @param end Time to which start selecting orders by trade time(UNIX time). If this value is not
   *     null order will become «asc».
   * @param pair Currency pair.
   * @return
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#orderHistory(String, ParamsDigest,
   *     SynchronizedValueFactory, Long, Long, Long, DSXAuthenticatedV2.SortOrder, * Long, Long,
   *     String)}
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXOrderHistoryReturn OrderHistory(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("from") Long from,
      @DefaultValue("1000") @FormParam("count") Long count,
      @FormParam("from_id") Long fromId,
      @FormParam("end_id") Long endId,
      @FormParam("order") SortOrder order,
      @FormParam("since") Long since,
      @FormParam("end") Long end,
      @FormParam("pair") String pair)
      throws IOException;

  /**
   * All parameters are obligatory (ie. none may be null)
   *
   * @param type The transaction type (buy or sell)
   * @param rate The price to buy/sell
   * @param amount The amount which is necessary to buy/sell
   * @param pair pair, eg. btcusd
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#trade(String, ParamsDigest, SynchronizedValueFactory,
   *     DSXOrder.Type, BigDecimal, BigDecimal, String, DSXOrder.OrderType)} DSXOrder.OrderType,
   *     String)}
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXTradeReturn Trade(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("type") DSXOrder.Type type,
      @FormParam("rate") BigDecimal rate,
      @FormParam("amount") BigDecimal amount,
      @FormParam("pair") String pair)
      throws IOException;

  /**
   * @param orderId Id of order to cancel
   * @return
   * @throws IOException
   * @deprecated Use {@link DSXAuthenticatedV2#cancelOrder(String, ParamsDigest,
   *     SynchronizedValueFactory, Long)}
   */
  @Deprecated
  @POST
  @Path("tapi")
  @FormParam("method")
  DSXCancelOrderReturn CancelOrder(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("order_id") Long orderId)
      throws IOException;

  @Deprecated
  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXCryptoDepositAddressReturn getCryptoDepositAddress(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @DefaultValue("0") @FormParam("newAddress") int newAddress)
      throws IOException;

  @Deprecated
  @POST
  @Path("dwapi/cryptoWithdraw")
  @FormParam("method")
  DSXCryptoWithdrawReturn cryptoWithdraw(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @FormParam("address") String address,
      @FormParam("amount") BigDecimal amount,
      @FormParam("commission") BigDecimal commission)
      throws IOException;

  @Deprecated
  @POST
  @Path("dwapi/fiatWithdraw")
  @FormParam("method")
  DSXFiatWithdrawReturn fiatWithdraw(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount)
      throws IOException;

  @Deprecated
  @POST
  @Path("dwapi")
  @FormParam("method")
  DSXTransactionStatusReturn getTransactionsStatus(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") Long id)
      throws IOException;

  @Deprecated
  @POST
  @Path("dwapi/getTransactions")
  @FormParam("method")
  DSXTransactionReturn getTransactions(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("from") Long from,
      @FormParam("to") Long to,
      @FormParam("fromId") Long fromId,
      @FormParam("told") Long told,
      @FormParam("type") DSXTransaction.Type type,
      @FormParam("status") DSXTransaction.Status status,
      @FormParam("currency") String currency)
      throws IOException;

  enum SortOrder {
    ASC,
    DESC
  }
}
