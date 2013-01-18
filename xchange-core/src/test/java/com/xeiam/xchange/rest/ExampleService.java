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
package com.xeiam.xchange.rest;

import java.math.BigDecimal;

import javax.ws.rs.*;

import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * @author Matija Mazi
 */
@Path("api/2")
public interface ExampleService {

  @POST
  @Path("buy/")
  @Produces("application/json")
  Order buy(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price);

  @POST
  @Path("bitcoin_withdrawal/{user}")
  @Produces("application/json")
  Object withdrawBitcoin(@PathParam("user") String user, @FormParam("password") String password, @QueryParam("amount") BigDecimal amount, @QueryParam("address") String address);

  @GET
  @Path("{ident}_{currency}/ticker")
  Ticker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @POST
  @FormParam("method")
  Object getInfo(Long from, Long count);

  @GET
  @Path("auth")
  Object testBasicAuth(@HeaderParam("Authorization") BasicAuthCredentials credentials, @QueryParam("param") Integer value);

  /*
   * @GET
   * @Path("order_book/")
   * @Produces("application/json") public OrderBook getOrderBook();
   * @GET
   * @Path("ticker/")
   * @Produces("application/json") public Ticker getTicker();
   * @GET
   * @Path("transactions/")
   * @Produces("application/json") public Trade[] getTransactions();
   * @POST
   * @Path("cancel_order/")
   * @Produces("application/json") public Object cancelOrder(@FormParam("user") String user, @FormParam("password") String password, @FormParam("id") int orderId);
   * @POST
   * @Path("open_orders/")
   * @Produces("application/json") public Order[] getOpenOrders(@FormParam("user") String user, @FormParam("password") String password);
   * @POST
   * @Path("sell/")
   * @Produces("application/json") public Order sell(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal
   * price);
   * @POST
   * @Path("bitcoin_deposit_address/")
   * @Produces("application/json") public String getBitcoinDepositAddress(@FormParam("user") String user, @FormParam("password") String password);
   */
}
