package org.knowm.xchange.btce.v3;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.btce.v3.dto.account.BTCEAccountInfoReturn;
import org.knowm.xchange.btce.v3.dto.account.BTCEWithDrawInfoReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrder;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrderInfoReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCETransHistoryReturn;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BTCEAuthenticated extends BTCE {

  /**
   * @return {success=1, return={funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=0,
   * open_orders=0, server_time=1357678428}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCEAccountInfoReturn getInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  /**
   * None of the parameters are obligatory (ie. all are nullable). Use this method instead of OrderList, which is deprecated.
   *
   * @param pair the pair to display the orders eg. btc_usd (default: all pairs)
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCEOpenOrdersReturn ActiveOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("pair") String pair) throws IOException;

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
  BTCEPlaceOrderReturn Trade(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("pair") String pair, @FormParam("type") BTCEOrder.Type type,
      @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount) throws IOException;

  /**
   * @param orderId order ID to cancel
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCECancelOrderReturn CancelOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("order_id") Long orderId) throws IOException;

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
   * @return {success=1, return={tradeId={pair=btc_usd, type=sell, amount=1, rate=1, orderId=1234, timestamp=1234}}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCETradeHistoryReturn TradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from, @FormParam("count") Long count,
      @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair) throws IOException;

  /**
   * POST to retrieve transaction history from BTCE exchange. All parameters are nullable
   *
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default +inf
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default +inf
   * @return JSON like {success=1, return={tradeId={type=sell, amount=1.00000000, currency="BTC", status=2, description="BTC Payment",
   * timestamp=1234}}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCETransHistoryReturn TransHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from, @FormParam("count") Long count,
      @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end) throws IOException;

  enum SortOrder {
    ASC, DESC
  }
  
  /**
   * POST to retrieve order info from BTCE exchange.
   *
   * @param orderId The ID of the order to display
   * @return JSON like {success=1, return={<code>orderId</code>={pair="btc_usd",type="sell",start_amount=13.345, amount=12.345,
   * rate=485, timestamp_created=1342448420, status=0}}}
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCEOrderInfoReturn OrderInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("order_id") Long orderId) throws IOException;

  /**
   * All parameters are obligatory (ie. none may be null)
   *
   * @param coinName Currency	(e.g. BTC, LTC)
   * @param amount Amount of withdrawal
   * @param address Withdrawall address
   * @return
   */
  @POST
  @Path("tapi")
  @FormParam("method")
  BTCEWithDrawInfoReturn WithdrawCoin(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("coinName") String coinName, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address);

}
