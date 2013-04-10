/**
 * Copyright (C) 2012 - 2013 Matija Mazi
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.xeiam.xchange.btce.dto.account.BTCEAccountInfoReturn;
import com.xeiam.xchange.btce.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.dto.trade.BTCEPlaceOrderReturn;

import si.mazi.rescu.ParamsDigest;

/**
 * @author Matija Mazi
 */
@Path("tapi")
public interface BTCEAuthenticated {

  /**
   * @param from The ID of the transaction to start displaying with; default 0
   * @param count The number of transactions for displaying default 1,000
   * @param fromId The ID of the transaction to start displaying with default 0
   * @param endId The ID of the transaction to finish displaying with default ∞
   * @param order sorting ASC or DESC default DESC
   * @param since When to start displaying? UNIX time default 0
   * @param end When to finish displaying? UNIX time default ∞
   * @return {success=1, return={funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1, open_orders=0, server_time=1357678428}}
   */
  @POST
  @FormParam("method")
  BTCEAccountInfoReturn getInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("from") Long from,
      @FormParam("count") Long count, @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end);

  /**
   * None of the parameters are obligatory (ie. all are nullable).
   * 
   * @param from the number of the order to start displaying with (default: 0)
   * @param count The number of orders for displaying (default: 1000)
   * @param fromId id of the order to start displaying with (default: 0)
   * @param endId id of the ordeк to finish displaying (default: ∞)
   * @param order sorting (default: DESC)
   * @param since when to start displaying UNIX time (default: 0)
   * @param end when to finish displaying UNIX time (default: ∞)
   * @param pair the pair to display the orders eg. btc_usd (default: all pairs)
   * @param active is it displaying of active orders only? 1 or 0 (default: 1)
   */
  @POST
  @FormParam("method")
  BTCEOpenOrdersReturn OrderList(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("from") Long from,
      @FormParam("count") Long count, @FormParam("from_id") Long fromId, @FormParam("end_id") Long endId, @FormParam("order") SortOrder order, @FormParam("since") Long since,
      @FormParam("end") Long end, @FormParam("pair") String pair, @FormParam("active") int active);

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
      @FormParam("type") BTCEOrder.Type type, @FormParam("rate") BigDecimal rate, @FormParam("amount") BigDecimal amount);

  @POST
  @FormParam("method")
  BTCECancelOrderReturn CancelOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signer, @FormParam("nonce") int nonce, @FormParam("order_id") long orderId);

  enum SortOrder {
    ASC, DESC
  }
}
