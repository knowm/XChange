package org.knowm.xchange.wex.v3;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.wex.v3.dto.account.WexAccountInfoReturn;
import org.knowm.xchange.wex.v3.dto.account.WexWithDrawInfoReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexCancelOrderReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexOpenOrdersReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexOrder;
import org.knowm.xchange.wex.v3.dto.trade.WexOrderInfoReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexPlaceOrderReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeHistoryReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexTransHistoryReturn;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Matija Mazi */
@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface WexAuthenticated extends Wex {

  /**
   * @return {success=1, return={funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1,
   *     trade=1, withdraw=1}, transaction_count=0, open_orders=0, server_time=1357678428}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexAccountInfoReturn getInfo(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  /**
   * None of the parameters are obligatory (ie. all are nullable). Use this method instead of
   * OrderList, which is deprecated.
   *
   * @param pair the pair to display the orders eg. btc_usd (default: all pairs)
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexOpenOrdersReturn ActiveOrders(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("pair") String pair)
      throws IOException;

  /**
   * All parameters are obligatory (ie. none may be null).
   *
   * @param pair pair, eg. btc_usd
   * @param type The transaction type (buy or sell)
   * @param rate The price to buy/sell
   * @param amount The amount which is necessary to buy/sell
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexPlaceOrderReturn Trade(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("pair") String pair,
      @FormParam("type") WexOrder.Type type,
      @FormParam("rate") BigDecimal rate,
      @FormParam("amount") BigDecimal amount)
      throws IOException;

  /** @param orderId order ID to cancel */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexCancelOrderReturn CancelOrder(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("order_id") Long orderId)
      throws IOException;

  /**
   * All parameters are nullable
   *
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default +inf
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default +inf
   * @param pair The pair to show the transaction; example btc_usd; all pairs
   * @return {success=1, return={tradeId={pair=btc_usd, type=sell, amount=1, rate=1, orderId=1234,
   *     timestamp=1234}}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexTradeHistoryReturn TradeHistory(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("from") Long from,
      @FormParam("count") Long count,
      @FormParam("from_id") Long fromId,
      @FormParam("end_id") Long endId,
      @FormParam("order") SortOrder order,
      @FormParam("since") Long since,
      @FormParam("end") Long end,
      @FormParam("pair") String pair)
      throws IOException;

  /**
   * POST to retrieve transaction history from Wex exchange. All parameters are nullable
   *
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default +inf
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default +inf
   * @return JSON like {success=1, return={tradeId={type=sell, amount=1.00000000, currency="BTC",
   *     status=2, description="BTC Payment", timestamp=1234}}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexTransHistoryReturn TransHistory(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("from") Long from,
      @FormParam("count") Long count,
      @FormParam("from_id") Long fromId,
      @FormParam("end_id") Long endId,
      @FormParam("order") SortOrder order,
      @FormParam("since") Long since,
      @FormParam("end") Long end)
      throws IOException;

  /**
   * POST to retrieve order info from Wex exchange.
   *
   * @param orderId The ID of the order to display
   * @return JSON like {success=1, return={<code>orderId</code>
   *     ={pair="btc_usd",type="sell",start_amount=13.345, amount=12.345, rate=485,
   *     timestamp_created=1342448420, status=0}}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexOrderInfoReturn OrderInfo(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("order_id") Long orderId)
      throws IOException;

  /**
   * All parameters are obligatory (ie. none may be null)
   *
   * @param coinName Currency (e.g. BTC, LTC)
   * @param amount Amount of withdrawal
   * @param address Withdrawall address
   * @return
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  WexWithDrawInfoReturn WithdrawCoin(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("coinName") String coinName,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address);

  enum SortOrder {
    ASC,
    DESC
  }
}
