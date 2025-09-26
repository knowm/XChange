package org.knowm.xchange.coinsph;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.account.*;
import org.knowm.xchange.coinsph.dto.trade.*;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/openapi") // Base path for all endpoints of Coins.ph API
@Produces(MediaType.APPLICATION_JSON)
public interface CoinsphAuthenticated extends Coinsph {

  String X_COINS_APIKEY = "X-COINS-APIKEY"; // Header name for API key

  // timestamp and signature are query/body params, not headers for signing purposes.

  // API key is in header X-COINS-APIKEY
  // timestamp, signature, recvWindow are query parameters for GET/DELETE.
  // For POST, they are part of the payload (query string or request body).

  /**
   * Get current account information.
   *
   * @param apiKey API Key (Header)
   * @param timestamp Timestamp in ms (Query Param)
   * @param signature Signature (Query Param)
   * @param recvWindow optional, The value cannot be greater than 60000 (Query Param)
   * @return
   * @throws IOException
   * @throws org.knowm.xchange.coinsph.dto.CoinsphException
   */
  @GET
  @Path("/v1/account")
  CoinsphAccount getAccount(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * Get asset trade fees.
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Optional. Trading symbol (e.g., BTCPHP). If not sent, fees for all symbols are
   *     returned.
   * @param recvWindow Optional.
   * @return List of trade fees
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("/v1/asset/tradeFee")
  List<CoinsphTradeFee> getTradeFee(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * Send in a new order.
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Trading symbol (e.g., BTCPHP)
   * @param side BUY or SELL
   * @param type LIMIT, MARKET, STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT,
   *     LIMIT_MAKER
   * @param timeInForce Optional. GTC, IOC, FOK
   * @param quantity Order quantity
   * @param quoteOrderQty Optional. For MARKET orders, the amount of quote asset to spend/receive
   * @param price Optional. Order price, required for LIMIT orders
   * @param newClientOrderId Optional. A unique id for the order. Automatically generated if not
   *     sent.
   * @param stopPrice Optional. Used with STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, and
   *     TAKE_PROFIT_LIMIT orders.
   * @param recvWindow Optional. The value cannot be greater than 60000
   * @return
   * @throws IOException
   * @throws CoinsphException
   */
  @POST
  @Path("/v1/order")
  // @Consumes(MediaType.APPLICATION_JSON) // Removed, body will be empty
  CoinsphOrder newOrder(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") CoinsphOrderSide side,
      @QueryParam("type") CoinsphOrderType type,
      @QueryParam("timeInForce") CoinsphTimeInForce timeInForce, // Optional
      @QueryParam("quantity") BigDecimal quantity, // Optional (one of quantity or quoteOrderQty)
      @QueryParam("quoteOrderQty") BigDecimal quoteOrderQty, // Optional
      @QueryParam("price") BigDecimal price, // Optional (for LIMIT orders)
      @QueryParam("newClientOrderId") String newClientOrderId, // Optional
      @QueryParam("stopPrice") BigDecimal stopPrice, // Optional
      // newOrderRespType is not used by Coins.ph from what I see, default is FULL for MARKET/LIMIT
      @QueryParam("recvWindow") Long recvWindow, // Optional
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature)
      throws IOException, CoinsphException;

  /**
   * Check an order's status. Either orderId or origClientOrderId must be sent.
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Trading symbol
   * @param orderId Optional. Order ID
   * @param origClientOrderId Optional. Client order ID
   * @param recvWindow Optional.
   * @return
   * @throws IOException
   * @throws org.knowm.xchange.coinsph.dto.CoinsphException
   */
  @GET
  @Path("/v1/order")
  CoinsphOrder getOrderStatus(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("symbol")
          String
              symbol, // This is actually not in the API spec for GET /order, but often included for
      // consistency
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * Cancel an active order.
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Trading symbol
   * @param orderId Optional. Order ID
   * @param origClientOrderId Optional. Client order ID
   * @param recvWindow Optional.
   * @return
   * @throws IOException
   * @throws CoinsphException
   */
  @DELETE
  @Path("/v1/order")
  CoinsphOrder cancelOrder(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("symbol") String symbol, // API docs for DELETE /order list symbol
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  // User Data Stream
  // =================================================================================================

  /**
   * Start a new user data stream. The stream will close after 60 minutes unless a keepalive is
   * sent.
   *
   * @param apiKey API key
   * @return
   * @throws IOException
   * @throws CoinsphException
   */
  @POST
  @Path("/v1/userDataStream")
  CoinsphListenKey createListenKey(@HeaderParam(X_COINS_APIKEY) String apiKey)
      throws IOException, CoinsphException;

  /**
   * Keepalive a user data stream to prevent it from closing. User data streams will close after 60
   * minutes. It's recommended to send a ping about every 30 minutes.
   *
   * @param apiKey API key
   * @param listenKey Listen key
   * @return
   * @throws IOException
   * @throws CoinsphException
   */
  @PUT
  @Path("/v1/userDataStream")
  Void keepAliveListenKey(
      @HeaderParam(X_COINS_APIKEY) String apiKey, @QueryParam("listenKey") String listenKey)
      throws IOException, CoinsphException;

  /**
   * Close a user data stream.
   *
   * @param apiKey API key
   * @param listenKey Listen key
   * @return
   * @throws IOException
   * @throws CoinsphException
   */
  @DELETE
  @Path("/v1/userDataStream")
  Void closeListenKey(
      @HeaderParam(X_COINS_APIKEY) String apiKey, @QueryParam("listenKey") String listenKey)
      throws IOException, CoinsphException;

  /**
   * Get all open orders on a symbol or all symbols.
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Optional. If not sent, orders for all symbols will be returned.
   * @param recvWindow Optional.
   * @return
   * @throws IOException
   * @throws org.knowm.xchange.coinsph.dto.CoinsphException
   */
  @GET
  @Path("/v1/openOrders")
  List<CoinsphOrder> getOpenOrders(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * Get all account orders; active, canceled, or filled. This seems to map to
   * /openapi/v1/historyOrders in Coins.ph docs
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Trading symbol
   * @param startTime Optional. Timestamp in ms
   * @param endTime Optional. Timestamp in ms
   * @param limit Optional. Default 500; max 1000.
   * @param recvWindow Optional.
   * @return
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("/v1/historyOrders") // Changed from allOrders to match Coins.ph docs
  List<CoinsphOrder> getHistoryOrders(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("symbol") String symbol, // API docs for historyOrders require symbol
      // @QueryParam("orderId") Long orderId, // Coins.ph uses startTime/endTime for history
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * Get trades for a specific account and symbol.
   *
   * @param apiKey
   * @param timestamp
   * @param signature
   * @param symbol Trading symbol
   * @param orderId Optional. This is not a direct filter in Coins.ph, but can be used to filter
   *     results post-fetch if needed.
   * @param startTime Optional. Timestamp in ms
   * @param endTime Optional. Timestamp in ms
   * @param fromTradeId Optional. Trade Id to fetch from. Default gets most recent trades.
   * @param limit Optional. Default 500; max 1000.
   * @param recvWindow Optional.
   * @return
   * @throws IOException
   * @throws org.knowm.xchange.coinsph.dto.CoinsphException
   */
  @GET
  @Path("/v1/myTrades")
  List<CoinsphUserTrade> getMyTrades(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("symbol") String symbol, // API docs for myTrades require symbol
      @QueryParam("orderId")
          Long orderId, // Not a direct filter in Coins.ph, but can be used by client
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("fromId") Long fromTradeId, // API doc uses fromId, not fromTradeId
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  // Withdraw API
  @POST
  @Path("/wallet/v1/withdraw/apply")
  CoinsphWithdrawal withdraw(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("coin") String coin,
      @QueryParam("network") String network,
      @QueryParam("address") String address,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("addressTag") String addressTag,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  // Deposit Address API
  @GET
  @Path("/wallet/v1/deposit/address")
  CoinsphDepositAddress getDepositAddress(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("coin") String coin,
      @QueryParam("network") String network,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  // Deposit History API
  @GET
  @Path("/wallet/v1/deposit/history")
  List<CoinsphDepositRecord> getDepositHistory(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("coin") String coin,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  // Withdrawal History API
  @GET
  @Path("/wallet/v1/withdraw/history")
  List<CoinsphWithdrawalRecord> getWithdrawalHistory(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("coin") String coin,
      @QueryParam("withdrawOrderId") String withdrawOrderId,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  // Fiat API
  // =================================================================================================

  /**
   * Get supported fiat channels for cash out operations.
   *
   * @param apiKey API Key (Header)
   * @param timestamp Timestamp in ms (Query Param)
   * @param signature Signature (Query Param)
   * @param currency The currency for which to get supported channels
   * @param transactionType Transaction type (-1 for cash out)
   * @param recvWindow Optional. The value cannot be greater than 60000
   * @return List of supported fiat channels
   * @throws IOException
   * @throws CoinsphException
   */
  @POST
  @Path("/fiat/v1/support-channel")
  CoinsphFiatResponse<List<CoinsphFiatChannel>> getSupportedFiatChannels(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("currency") String currency,
      @QueryParam("transactionType") int transactionType,
      @QueryParam("transactionChannel") String transactionChannel,
      @QueryParam("transactionSubject") String transactionSubject,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * Create a cash out request.
   *
   * @param apiKey API Key (Header)
   * @param timestamp Timestamp in ms (Query Param)
   * @param signature Signature (Query Param)
   * @param request Cash out request details
   * @param recvWindow Optional. The value cannot be greater than 60000
   * @return Cash out response
   * @throws IOException
   * @throws CoinsphException
   */
  @POST
  @Path("/fiat/v1/cash-out")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinsphFiatResponse<CoinsphCashOutResponse> cashOut(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      CoinsphCashOutRequest request,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;

  /**
   * List fiat history for fiat transactions.
   *
   * @param apiKey API Key (Header)
   * @param timestamp Timestamp in ms (Query Param)
   * @param signature Signature (Query Param)
   * @param recvWindow Optional. The value cannot be greater than 60000
   * @return Fiat history response containing a list of fiat transactions
   * @throws IOException
   * @throws CoinsphException
   */
  @POST
  @Path("/fiat/v2/history")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinsphFiatResponse<List<CoinsphFiatHistory>> fiatHistory(
      @HeaderParam(X_COINS_APIKEY) String apiKey,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("signature") ParamsDigest signature,
      CoinsphFiatHistoryRequest fiatHistoryRequest,
      @QueryParam("recvWindow") Long recvWindow)
      throws IOException, CoinsphException;
}
