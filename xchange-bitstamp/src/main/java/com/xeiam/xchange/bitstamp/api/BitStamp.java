/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitstamp.api;

import com.xeiam.xchange.bitstamp.api.model.*;

import javax.ws.rs.*;
import java.math.BigDecimal;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 5:53 PM See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Produces("application/json")
public interface BitStamp {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("order_book/")
  @Produces("application/json")
  public OrderBook getOrderBook();

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
  public Transaction[] getTransactions();

  @GET
  @Path("transactions/")
  @Produces("application/json")
  public Transaction[] getTransactions(@QueryParam("timedelta") long timedeltaSec);

  /** @return true if order has been found and canceled. */
  @POST
  @Path("cancel_order/")
  @Produces("application/json")
  public Object cancelOrder(@FormParam("user") String user, @FormParam("password") String password, @FormParam("id") int orderId);

  @POST
  @Path("balance/")
  @Produces("application/json")
  public Balance getBalance(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public UserTransaction[] getUserTransactions(@FormParam("user") String user, @FormParam("password") String password, @QueryParam("timedelta") long timedeltaSec);

  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public UserTransaction[] getUserTransactions(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("open_orders/")
  @Produces("application/json")
  public Order[] getOpenOrders(@FormParam("user") String user, @FormParam("password") String password);

  /** buy limit order */
  @POST
  @Path("buy/")
  @Produces("application/json")
  public Order buy(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") double amount, @FormParam("price") double price);

  /** sell limit order */
  @POST
  @Path("sell/")
  @Produces("application/json")
  public Order sell(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") double amount, @FormParam("price") double price);

  @POST
  @Path("bitcoin_deposit_address/")
  @Produces("application/json")
  public String getBitcoinDepositAddress(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("bitcoin_withdrawal/")
  @Produces("application/json")
  public Object withdrawBitcoin(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") BigDecimal amount, @FormParam("address") String address);

  // todo: bitstamp code handling, send to user
}
