/**
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
package com.xeiam.xchange.bitstamp;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;

/**
 * @author Matija Mazi See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Produces("application/json")
public interface Bitstamp {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("order_book/")
  @Produces("application/json")
  public BitstampOrderBook getOrderBook();

  @GET
  @Path("ticker/")
  @Produces("application/json")
  public BitstampTicker getTicker();

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions/")
  @Produces("application/json")
  public BitstampTransaction[] getTransactions();

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  @Produces("application/json")
  public Object cancelOrder(@FormParam("user") String user, @FormParam("password") String password, @FormParam("id") int orderId);

  @POST
  @Path("balance/")
  @Produces("application/json")
  public BitstampBalance getBalance(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("user") String user, @FormParam("password") String password, @FormParam("limit") long numberOfTransactions);
  @Deprecated
  @POST
  @Path("open_orders/")
  @Produces("application/json")
  public BitstampOrder[] getOpenOrders(@FormParam("user") String user, @FormParam("password") String password);
  /** buy limit order */
  @POST
  @Path("buy/")
  @Produces("application/json")
  public BitstampOrder buy(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price);

  /** sell limit order */
  @POST
  @Path("sell/")
  @Produces("application/json")
  public BitstampOrder sell(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price);

  @POST
  @Path("bitcoin_deposit_address/")
  @Produces("application/json")
  public String getBitcoinDepositAddress(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("bitcoin_withdrawal/")
  @Produces("application/json")
  public Object withdrawBitcoin(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") BigDecimal amount, @FormParam("address") String address);

  // TODO: bitstamp code handling, send to user
}
