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
package com.xeiam.xchange.btcchina;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaRequestWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetAccountInfoResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaRequestWithdrawalResponse;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;

@Path("/")
public interface BTCChina {

  @GET
  @Path("bc/ticker")
  @Produces("application/json")
  public BTCChinaTicker getTicker(@PathParam("currency") String currency);

  @GET
  @Path("bc/orderbook")
  @Produces("application/json")
  public BTCChinaDepth getFullDepth(@PathParam("currency") String currency);

  @GET
  @Path("bc/trades")
  @Produces("application/json")
  public BTCChinaTrade[] getTrades(@PathParam("currency") String currency);

  @POST
  @Path("api_trade_v1.php")
  @Consumes("application/json")
  @Produces("application/json")
  public BTCChinaGetAccountInfoResponse getAccountInfo(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaGetAccountInfoRequest getAccountInfoRequest);

  @POST
  @Path("api_trade_v1.php")
  @Consumes("application/json")
  @Produces("application/json")
  public BTCChinaRequestWithdrawalResponse requestWithdrawal(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaRequestWithdrawalRequest requestWithdrawalRequest);

  @POST
  @Path("api_trade_v1.php")
  @Consumes("application/json")
  @Produces("application/json")
  public BTCChinaGetOrdersResponse getOrders(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaGetOrdersRequest getOrdersRequest);

  @POST
  @Path("api_trade_v1.php")
  @Consumes("application/json")
  @Produces("application/json")
  public BTCChinaBooleanResponse cancelOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaCancelOrderRequest cancelOrderRequest);

  @POST
  @Path("api_trade_v1.php")
  @Consumes("application/json")
  @Produces("application/json")
  public BTCChinaBooleanResponse buyOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaBuyOrderRequest buyOrderRequest);

  @POST
  @Path("api_trade_v1.php")
  @Consumes("application/json")
  @Produces("application/json")
  public BTCChinaBooleanResponse sellOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaSellOrderRequest sellOrderRequest);

}
