package org.knowm.xchange.btcchina;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetDepositsRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalsRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaRequestWithdrawalRequest;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetAccountInfoResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaRequestWithdrawalResponse;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaBuyIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaBuyStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaCancelIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaCancelStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetArchivedOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetIcebergOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetMarketDepthRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetStopOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaSellIcebergOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaSellStopOrderRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrderResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetIcebergOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetStopOrderResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetStopOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCChina {

  @GET
  @Path("data/ticker")
  BTCChinaTicker getTicker(@QueryParam("market") String market) throws IOException;

  /**
   * Returns all the open orders from the specified {@code market}.
   *
   * @param market the market could be {@code btccny}, {@code ltccny}, {@code ltcbtc}.
   * @return the order book.
   * @throws IOException indicates I/O exception.
   * @see #getOrderBook(String, int)
   */
  @GET
  @Path("data/orderbook")
  BTCChinaDepth getFullDepth(@QueryParam("market") String market) throws IOException;

  /**
   * Order book default contains all open ask and bid orders. Set 'limit' parameter to specify the number of records fetched per request.
   * <p>
   * Bid orders are {@code limit} orders with highest price while ask with lowest, and orders are descendingly sorted by price.
   * </p>
   *
   * @param market market could be {@code btccny}, {@code ltccny}, {@code ltcbtc}.
   * @param limit number of records fetched per request.
   * @return the order book.
   * @throws IOException indicates I/O exception.
   * @see #getFullDepth(String)
   */
  @GET
  @Path("data/orderbook")
  BTCChinaDepth getOrderBook(@QueryParam("market") String market, @QueryParam("limit") int limit) throws IOException;

  /**
   * Returns last 100 trade records.
   */
  @GET
  @Path("data/historydata")
  BTCChinaTrade[] getHistoryData(@QueryParam("market") String market) throws IOException;

  /**
   * Returns last {@code limit} trade records.
   *
   * @param market
   * @param limit the range of limit is [0,5000].
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  BTCChinaTrade[] getHistoryData(@QueryParam("market") String market, @QueryParam("limit") int limit) throws IOException;

  /**
   * Returns 100 trade records starting from id {@code since}.
   *
   * @param market
   * @param since the starting trade ID(exclusive).
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  BTCChinaTrade[] getHistoryData(@QueryParam("market") String market, @QueryParam("since") long since) throws IOException;

  /**
   * Returns {@code limit} trades starting from id {@code since}
   *
   * @param market
   * @param since the starting trade ID(exclusive).
   * @param limit the range of limit is [0,5000].
   * @throws IOException
   */
  @GET
  @Path("data/historydata")
  BTCChinaTrade[] getHistoryData(@QueryParam("market") String market, @QueryParam("since") long since,
      @QueryParam("limit") int limit) throws IOException;

  @GET
  @Path("data/historydata")
  BTCChinaTrade[] getHistoryData(@QueryParam("market") String market, @QueryParam("since") long since, @QueryParam("limit") int limit,
      @QueryParam("sincetype") @DefaultValue("id") String sincetype) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetAccountInfoResponse getAccountInfo(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce,
      BTCChinaGetAccountInfoRequest getAccountInfoRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetDepositsResponse getDeposits(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetDepositsRequest getDepositsRequest) throws IOException;

  /**
   * Get the complete market depth. Returns all open bid and ask orders.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetMarketDepthResponse getMarketDepth(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetMarketDepthRequest request) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetWithdrawalResponse getWithdrawal(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetWithdrawalRequest request) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetWithdrawalsResponse getWithdrawals(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetWithdrawalsRequest request) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaRequestWithdrawalResponse requestWithdrawal(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce,
      BTCChinaRequestWithdrawalRequest requestWithdrawalRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetOrderResponse getOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetOrderRequest getOrderRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetOrdersResponse getOrders(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetOrdersRequest getOrdersRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetOrdersResponse getArchivedOrders(@HeaderParam("Authorization") ParamsDigest authorization,
                                      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetArchivedOrdersRequest getArchivedOrdersRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaBooleanResponse cancelOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaCancelOrderRequest cancelOrderRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaIntegerResponse buyOrder2(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaBuyOrderRequest buyOrderRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaIntegerResponse sellOrder2(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaSellOrderRequest sellOrderRequest) throws IOException;

  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaTransactionsResponse getTransactions(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaTransactionsRequest transactionRequest) throws IOException;

  /**
   * Place a buy iceberg order. This method will return an iceberg order id.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaIntegerResponse buyIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaBuyIcebergOrderRequest request) throws IOException;

  /**
   * Place a sell iceberg order. This method will return an iceberg order id.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaIntegerResponse sellIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaSellIcebergOrderRequest request) throws IOException;

  /**
   * Get an iceberg order, including the orders placed.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetIcebergOrderResponse getIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetIcebergOrderRequest request) throws IOException;

  /**
   * Get iceberg orders, including the orders placed inside each iceberg order.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetIcebergOrdersResponse getIcebergOrders(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetIcebergOrdersRequest request) throws IOException;

  /**
   * Cancels an open iceberg order. Fails if iceberg order is already cancelled or closed. The related order with the iceberg order will also be
   * cancelled.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaBooleanResponse cancelIcebergOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaCancelIcebergOrderRequest request) throws IOException;

  /**
   * Place a buy stop order. This method will return a stop order id.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaIntegerResponse buyStopOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaBuyStopOrderRequest request) throws IOException;

  /**
   * Place a sell stop order. This method will return an stop order id.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaIntegerResponse sellStopOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaSellStopOrderRequest request) throws IOException;

  /**
   * Get a stop order.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetStopOrderResponse getStopOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetStopOrderRequest request) throws IOException;

  /**
   * Get stop orders.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaGetStopOrdersResponse getStopOrders(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaGetStopOrdersRequest request) throws IOException;

  /**
   * Cancels an open stop order. Fails if stop order is already cancelled or closed.
   */
  @POST
  @Path("api_trade_v1.php")
  @Consumes(MediaType.APPLICATION_JSON)
  BTCChinaBooleanResponse cancelStopOrder(@HeaderParam("Authorization") ParamsDigest authorization,
      @HeaderParam("Json-Rpc-Tonce") SynchronizedValueFactory<Long> jsonRpcTonce, BTCChinaCancelStopOrderRequest request) throws IOException;

}
