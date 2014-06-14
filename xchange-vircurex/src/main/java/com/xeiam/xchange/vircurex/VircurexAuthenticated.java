/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.vircurex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.vircurex.dto.account.VircurexAccountInfoReturn;
import com.xeiam.xchange.vircurex.dto.trade.VircurexOpenOrdersReturn;
import com.xeiam.xchange.vircurex.dto.trade.VircurexPlaceOrderReturn;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface VircurexAuthenticated {

  @GET
  @Path("get_balances.json")
  VircurexAccountInfoReturn getInfo(@QueryParam("account") String aUserName, @QueryParam("id") String anId, @QueryParam("token") String aToken, @QueryParam("timestamp") String aTimestamp);

  @Path("create_order.json")
  @GET
  VircurexPlaceOrderReturn trade(@QueryParam("account") String aUserName, @QueryParam("id") String anId, @QueryParam("token") String aToken, @QueryParam("timestamp") String aTimestamp,
      @QueryParam("ordertype") String anOrderType, @QueryParam("amount") String anAmount, @QueryParam("currency1") String aCurrency, @QueryParam("unitprice") String aPrice,
      @QueryParam("currency2") String aTransactionCurrency);

  @Path("release_order.json")
  @GET
  VircurexPlaceOrderReturn release(@QueryParam("account") String apiKey, @QueryParam("id") String nonce, @QueryParam("token") String token, @QueryParam("timestamp") String timestamp,
      @QueryParam("orderid") String orderId);

  @Path("read_orders.json")
  @GET
  VircurexOpenOrdersReturn getOpenOrders(@QueryParam("account") String apiKey, @QueryParam("id") String nonce, @QueryParam("token") String token, @QueryParam("timestamp") String timestamp,
      @QueryParam("otype") int releaseStatus);
}
