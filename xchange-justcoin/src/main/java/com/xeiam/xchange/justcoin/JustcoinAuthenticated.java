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
package com.xeiam.xchange.justcoin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.justcoin.dto.account.JustcoinDepositAddress;
import com.xeiam.xchange.justcoin.dto.account.WithdrawResponse;
import com.xeiam.xchange.justcoin.dto.trade.JustcoinOrder;
import com.xeiam.xchange.justcoin.dto.trade.JustcoinTrade;

/**
 * @author jamespedwards42
 */
@Path("api/v1")
public interface JustcoinAuthenticated {

  @GET
  @Path("balances")
  public JustcoinBalance[] getBalances(final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @GET
  @Path("{currency}/address")
  public JustcoinDepositAddress getDepositAddress(final @PathParam("currency") String currency, final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey)
      throws IOException;

  @POST
  @Path("{currency}/out")
  public WithdrawResponse withdraw(final @PathParam("currency") String currency, final @FormParam("address") String address, final @FormParam("amount") BigDecimal amount,
      final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @GET
  @Path("orders")
  public JustcoinOrder[] getOrders(final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;
  
  @GET
  @Path("orders/history")
  public JustcoinTrade[] getOrderHistory(final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;
  
  
  @POST
  @Path("orders")
  public String createMarketOrder(final @FormParam("market") String market, final @FormParam("type") String orderType, 
      final @FormParam("amount") BigDecimal amount, final @FormParam("aon") boolean allOrNothing, 
      final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @POST
  @Path("orders")
  public String createLimitOrder(final @FormParam("market") String market, final @FormParam("type") String orderType, 
      final @FormParam("price") BigDecimal limitPrice, final @FormParam("amount") BigDecimal amount, final @FormParam("aon") boolean allOrNothing,
      final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @DELETE
  @Path("orders/{orderId}")
  public String cancelOrder(final @PathParam("orderId") String orderId, final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;
}
