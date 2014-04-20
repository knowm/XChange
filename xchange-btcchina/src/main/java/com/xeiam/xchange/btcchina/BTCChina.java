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
package com.xeiam.xchange.btcchina;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCChina {

  @GET
  @Path("data/ticker")
  public BTCChinaTicker getTicker() throws IOException;

  @GET
  @Path("data/orderbook")
  public BTCChinaDepth getFullDepth() throws IOException;

  // (return last 100 trade records.)
  @GET
  @Path("data/historydata")
  public BTCChinaTrade[] getTrades() throws IOException;

  // return 100 trade records starting from id $since.
  @GET
  @Path("data/historydata")
  public BTCChinaTrade[] getTrades(@QueryParam("since") int transactionID) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetAccountInfoResponse getAccountInfo(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaGetAccountInfoRequest getAccountInfoRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaRequestWithdrawalResponse requestWithdrawal(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaRequestWithdrawalRequest requestWithdrawalRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetOrdersResponse getOrders(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaGetOrdersRequest getOrdersRequest)
      throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaBooleanResponse cancelOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaCancelOrderRequest cancelOrderRequest)
      throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaIntegerResponse buyOrder2(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaBuyOrderRequest buyOrderRequest)
      throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaIntegerResponse sellOrder2(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaSellOrderRequest sellOrderRequest)
      throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaTransactionsResponse getTransactions(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaTransactionsRequest transactionRequest) throws IOException;

}
