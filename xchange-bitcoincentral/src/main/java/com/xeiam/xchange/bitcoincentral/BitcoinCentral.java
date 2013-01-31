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
package com.xeiam.xchange.bitcoincentral;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcoincentral.dto.account.BitcoinCentralAccountInfo;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralDepth;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTicker;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTrade;
import com.xeiam.xchange.bitcoincentral.dto.trade.BitcoinCentralMyOrder;
import com.xeiam.xchange.bitcoincentral.dto.trade.TradeOrderRequestWrapper;
import com.xeiam.xchange.rest.BasicAuthCredentials;

/**
 * @author Matija Mazi
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
public interface BitcoinCentral {

  @GET
  @Path("account")
  BitcoinCentralAccountInfo getAccountInfo(@HeaderParam("Authorization") BasicAuthCredentials credentials);

  @GET
  @Path("account/transfers")
  Object getAccountTransfers(@HeaderParam("Authorization") BasicAuthCredentials credentials);

  /**
   * @param credentials username and password
   * @param page the page to fetch (1-based)
   * @param pageSize page size (between 5 and 100 inclusive)
   * @return
   */
  @GET
  @Path("account/trade_orders")
  BitcoinCentralMyOrder[] getAccountTradeOrders(@HeaderParam("Authorization") BasicAuthCredentials credentials, @QueryParam("page") int page, @QueryParam("per_page") int pageSize);

  /**
   * @param credentials username and password
   * @param order the order to place
   * @return
   */
  @POST
  @Path("account/trade_orders")
  BitcoinCentralMyOrder placeLimitOrder(@HeaderParam("Authorization") BasicAuthCredentials credentials, TradeOrderRequestWrapper order);

  @DELETE
  @Path("/account/trade_orders/{id}")
  String cancelOrder(@HeaderParam("Authorization") BasicAuthCredentials credentials, @PathParam("id") String orderId);

  @GET
  @Path("ticker")
  BitcoinCentralTicker getTicker(@QueryParam("currency") String currency);

  @GET
  @Path("order_book")
  BitcoinCentralDepth getOrderBook(@QueryParam("currency") String currency);

  @GET
  @Path("trades")
  BitcoinCentralTrade[] getTrades(@QueryParam("currency") String currency, @QueryParam("per_page") int perPage);
}
