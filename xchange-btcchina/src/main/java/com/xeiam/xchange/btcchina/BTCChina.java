/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.btcchina;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.ValueFactory;

import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetDepositsRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalsRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaRequestWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetAccountInfoResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaRequestWithdrawalResponse;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetMarketDepthRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellIcebergOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrderResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCChina {

  @GET
  @Path("data/ticker")
  public Map<String, BTCChinaTickerObject> getTickers(@QueryParam("market") String market) throws IOException;

  @GET
  @Path("data/ticker")
  public BTCChinaTicker getTicker(@QueryParam("market") String market) throws IOException;

  @GET
  @Path("data/orderbook")
  public BTCChinaDepth getFullDepth(@QueryParam("market") String market) throws IOException;

  /**
   * Returns last 100 trade records.
   *
   * @param market
   * @return
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  public List<BTCChinaTrade> getTrades(@QueryParam("market") String market) throws IOException;

  /**
   * Returns last {@code limit} trade records.
   *
   * @param market
   * @param limit the range of limit is [0,5000].
   * @return
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  public List<BTCChinaTrade> getTrades(@QueryParam("market") String market, @QueryParam("limit") int limit) throws IOException;

  /**
   * Returns 100 trade records starting from id {@code since}.
   *
   * @param market
   * @param since the starting trade ID(exclusive).
   * @return
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  public List<BTCChinaTrade> getTrades(@QueryParam("market") String market, @QueryParam("since") long since) throws IOException;

  /**
   * Returns {@code limit} trades starting from id {@code since}
   *
   * @param market
   * @param since the starting trade ID(exclusive).
   * @param limit the range of limit is [0,5000].
   * @return
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  public List<BTCChinaTrade> getTrades(@QueryParam("market") String market, @QueryParam("since") long since, @QueryParam("limit") int limit) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetAccountInfoResponse getAccountInfo(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaGetAccountInfoRequest getAccountInfoRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetDepositsResponse getDeposits(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaGetDepositsRequest getDepositsRequest) throws IOException;

  /**
   * Get the complete market depth. Returns all open bid and ask orders.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetMarketDepthResponse getMarketDepth(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") ValueFactory<Long> jsonRpcTonce,
      BTCChinaGetMarketDepthRequest request) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetWithdrawalResponse getWithdrawal(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaGetWithdrawalRequest request)
      throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetWithdrawalsResponse
      getWithdrawals(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaGetWithdrawalsRequest request) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaRequestWithdrawalResponse requestWithdrawal(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce,
      BTCChinaRequestWithdrawalRequest requestWithdrawalRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetOrderResponse getOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") long jsonRpcTonce, BTCChinaGetOrderRequest getOrderRequest)
      throws IOException;

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

  /**
   * Place a buy iceberg order. This method will return an iceberg order id.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaIntegerResponse buyIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") ValueFactory<Long> jsonRpcTonce,
      BTCChinaBuyIcebergOrderRequest request) throws IOException;

  /**
   * Place a sell iceberg order. This method will return an iceberg order id.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaIntegerResponse sellIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") ValueFactory<Long> jsonRpcTonce,
      BTCChinaSellIcebergOrderRequest request) throws IOException;

  /**
   * Get an iceberg order, including the orders placed.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetIcebergOrderResponse getIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") ValueFactory<Long> jsonRpcTonce,
      BTCChinaGetIcebergOrderRequest request) throws IOException;

  /**
   * Get iceberg orders, including the orders placed inside each iceberg order.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaGetIcebergOrdersResponse getIcebergOrders(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") ValueFactory<Long> jsonRpcTonce,
      BTCChinaGetIcebergOrdersRequest request) throws IOException;

  /**
   * Cancels an open iceberg order.
   * Fails if iceberg order is already cancelled or closed.
   * The related order with the iceberg order will also be cancelled.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  public BTCChinaBooleanResponse cancelIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization, @HeaderParam("Json-Rpc-Tonce") ValueFactory<Long> jsonRpcTonce,
      BTCChinaCancelIcebergOrderRequest request) throws IOException;

}
