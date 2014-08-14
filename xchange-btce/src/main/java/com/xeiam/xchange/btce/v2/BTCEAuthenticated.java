package com.xeiam.xchange.btce.v2;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.btce.v2.dto.account.BTCEAccountInfoReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryReturn;

/**
 * @author Matija Mazi
 */
@Deprecated
@Path("tapi")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BTCEAuthenticated {

  /**
   * @param from The ID of the transaction to start displaying with; default 0
   * @param count The number of transactions for displaying default 1,000
   * @param fromId The ID of the transaction to start displaying with default 0
   * @param endId The ID of the transaction to finish displaying with default âˆž
   * @param order sorting ASC or DESC default DESC
   * @param since When to start displaying? UNIX time default 0
   * @param end When to finish displaying? UNIX time default âˆž
   * @return {success=1, return={funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1, open_orders=0, server_time=1357678428}}
   */
  @POST
  @FormParam("method")
  BTCEAccountInfoReturn getInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("from") Long from,
      @FormParam("count") Long count, @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end) throws IOException;

  /**
   * None of the parameters are obligatory (ie. all are nullable).
   * 
   * @param from the number of the order to start displaying with (default: 0)
   * @param count The number of orders for displaying (default: 1000)
   * @param fromId id of the order to start displaying with (default: 0)
   * @param endId id of the order to finish displaying (default: âˆž)
   * @param order sorting (default: DESC)
   * @param since when to start displaying UNIX time (default: 0)
   * @param end when to finish displaying UNIX time (default: âˆž)
   * @param pair the pair to display the orders eg. btc_usd (default: all pairs)
   * @param active is it displaying of active orders only? 1 or 0 (default: 1)
   */
  @Deprecated
  @POST
  @FormParam("method")
  BTCEOpenOrdersReturn OrderList(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("from") Long from,
      @FormParam("count") Long count, @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair, @FormParam("active") int active) throws IOException;

  /**
   * None of the parameters are obligatory (ie. all are nullable). Use this method instead of OrderList, which is deprecated.
   * 
   * @param pair the pair to display the orders eg. btc_usd (default: all pairs)
   */
  @POST
  @FormParam("method")
  BTCEOpenOrdersReturn ActiveOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("pair") String pair) throws IOException;

  /**
   * All parameters are obligatory (ie. none may be null).
   * 
   * @param pair pair, eg. btc_usd
   * @param type The transaction type (buy or sell)
   * @param rate The price to buy/sell
   * @param amount The amount which is necessary to buy/sell
   */
  @POST
  @FormParam("method")
  BTCEPlaceOrderReturn Trade(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("pair") String pair,
      @FormParam("type") BTCEOrder.Type type, @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount) throws IOException;

  @POST
  @FormParam("method")
  BTCECancelOrderReturn CancelOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("order_id") Long orderId) throws IOException;

  /**
   * All parameters are nullable
   * 
   * @param from The number of the transactions to start displaying with; default 0
   * @param count The number of transactions for displaying; default 1,000
   * @param fromId The ID of the transaction to start displaying with; default 0
   * @param endId The ID of the transaction to finish displaying with; default ∞
   * @param order sorting ASC or DESC; default DESC
   * @param since When to start displaying; UNIX time default 0
   * @param end When to finish displaying; UNIX time default ∞
   * @param pair The pair to show the transaction; example btc_usd; all pairs
   * @return {success=1, return={tradeId={pair=btc_usd, type=sell, amount=1, rate=1, orderId=1234, timestamp=1234}}}
   */
  @POST
  @FormParam("method")
  BTCETradeHistoryReturn TradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("from") Long from,
      @FormParam("count") Long count, @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair) throws IOException;

  enum SortOrder {
    ASC, DESC
  }
}
