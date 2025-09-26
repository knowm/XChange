package org.knowm.xchange.binance;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureCommissionRate;
import org.knowm.xchange.binance.dto.account.futures.BinancePosition;
import org.knowm.xchange.binance.dto.trade.BinanceCancelledOrder;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.MarginType;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.binance.dto.trade.futures.BinanceChangeStatus;
import org.knowm.xchange.binance.dto.trade.futures.BinanceFutureNewOrder;
import org.knowm.xchange.binance.dto.trade.futures.BinanceSetLeverage;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceFuturesAuthenticated extends BinanceFutures {

  String SIGNATURE = "signature";
  String X_MBX_APIKEY = "X-MBX-APIKEY";

  @GET
  @Path("fapi/v1/klines")
  List<Object[]> klines(
      @QueryParam("symbol") String symbol,
      @QueryParam("interval") String interval,
      @QueryParam("limit") Integer limit,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime)
      throws IOException, BinanceException;

  /**
   * Get current futures account information.
   *
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v2/account")
  BinanceFutureAccountInformation futuresAccount(
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Account Information V3(USER_DATA)
   *
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v3/account")
  BinanceFutureAccountInformation futuresV3Account(
          @QueryParam("recvWindow") Long recvWindow,
          @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
          @HeaderParam(X_MBX_APIKEY) String apiKey,
          @QueryParam(SIGNATURE) ParamsDigest signature)
          throws IOException, BinanceException;

  /**
   * Send in a new futures order
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param price optional, must be provided for limit orders only
   * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
   *     sent.
   * @param stopPrice optional, used with stop orders
   * @param newOrderRespType optional, MARKET and LIMIT order types default to FULL, all other
   *     orders default to ACK
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">New order - Spot
   *     API docs -</a>
   */
  @POST
  @Path("fapi/v1/order")
  BinanceFutureNewOrder newOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("reduceOnly") Boolean reduceOnly,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("closePosition") boolean closePosition,
      @FormParam("activationPrice") BigDecimal activationPrice,
      @FormParam("callbackRate") BigDecimal callbackRate,
      @FormParam("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Send in a new inverse futures order
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param price optional, must be provided for limit orders only
   * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
   *     sent.
   * @param stopPrice optional, used with stop orders
   * @param newOrderRespType optional, MARKET and LIMIT order types default to FULL, all other
   *     orders default to ACK
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">New order - Spot
   *     API docs -</a>
   */
  @POST
  @Path("dapi/v1/order")
  BinanceFutureNewOrder newInverseOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("reduceOnly") boolean reduceOnly,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("stopPrice") BigDecimal stopPrice,
      @FormParam("closePosition") boolean closePosition,
      @FormParam("activationPrice") BigDecimal activationPrice,
      @FormParam("callbackRate") BigDecimal callbackRate,
      @FormParam("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Send in a new USD based (linear) futures order to portfolio margin engine
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param price optional, must be provided for limit orders only
   * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
   *     sent.
   * @param newOrderRespType optional, MARKET and LIMIT order types default to FULL, all other
   *     orders default to ACK
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">New order - Spot
   *     API docs -</a>
   */
  @POST
  @Path("papi/v1/um/order")
  BinanceFutureNewOrder newPortfolioMarginLinearOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("reduceOnly") boolean reduceOnly,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Send in a new coin based (inverse) futures order to portfolio margin engine
   *
   * @param symbol
   * @param side
   * @param type
   * @param timeInForce
   * @param quantity
   * @param price optional, must be provided for limit orders only
   * @param newClientOrderId optional, a unique id for the order. Automatically generated if not
   *     sent.
   * @param newOrderRespType optional, MARKET and LIMIT order types default to FULL, all other
   *     orders default to ACK
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">New order - Spot
   *     API docs -</a>
   */
  @POST
  @Path("papi/v1/cm/order")
  BinanceFutureNewOrder newPortfolioMarginInverseOrder(
      @FormParam("symbol") String symbol,
      @FormParam("side") OrderSide side,
      @FormParam("type") OrderType type,
      @FormParam("timeInForce") TimeInForce timeInForce,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("reduceOnly") boolean reduceOnly,
      @FormParam("price") BigDecimal price,
      @FormParam("newClientOrderId") String newClientOrderId,
      @FormParam("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancel an active futures order.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional generated by default.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("fapi/v1/order")
  BinanceCancelledOrder cancelFutureOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancel an active inverse futures order.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional generated by default.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("dapi/v1/order")
  BinanceCancelledOrder cancelInverseFutureOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancel an active inverse portfolio margin futures order.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional generated by default.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("papi/v1/cm/order")
  BinanceCancelledOrder cancelPortfolioMarginInverseFutureOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancel an active portfolio margin futures order.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional generated by default.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("papi/v1/um/order")
  BinanceCancelledOrder cancelPortfolioMarginFutureOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get future open orders on a symbol.
   *
   * @param symbol optional
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v1/openOrders")
  List<BinanceOrder> futureOpenOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;


  @GET
  @Path("fapi/v1/allOrders")
  List<BinanceOrder> futureAllOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get future open orders on a symbol.
   *
   * @param symbol optional
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("dapi/v1/openOrders")
  List<BinanceOrder> futureOpenInverseOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get future open portfolio margin orders on a symbol.
   *
   * @param symbol optional
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/papi/v1/um/openOrders ")
  List<BinanceOrder> futureOpenPortfolioMarginOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get future open portfolio margin orders on a symbol.
   *
   * @param symbol optional
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/papi/v1/cm/openOrders")
  List<BinanceOrder> futureOpenPortfolioMarginInverseOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol
   * @param orderId optional
   * @param startTime optional
   * @param endTime optional
   * @param fromId optional, tradeId to fetch from. Default gets most recent trades.
   * @param limit optional, default 500; max 1000.
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v1/userTrades")
  List<BinanceTrade> myFutureTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("fromId") Long fromId,
      @QueryParam("limit") Integer limit,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Check an order's status.<br>
   * Either orderId or origClientOrderId must be sent.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v1/order")
  BinanceOrder futureOrderStatus(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Check an inverse order's status.<br>
   * Either orderId or origClientOrderId must be sent.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("dapi/v1/order")
  BinanceOrder futureInverseOrderStatus(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Check a portfolio margin order's status.<br>
   * Either orderId or origClientOrderId must be sent.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/papi/v1/um/order")
  BinanceOrder futurePortfolioMarginOrderStatus(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Check a portfolio margin inverse/coin margin order's status.<br>
   * Either orderId or origClientOrderId must be sent.
   *
   * @param symbol
   * @param orderId optional
   * @param origClientOrderId optional
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/papi/v1/cm/order")
  BinanceOrder futurePortfolioMarginInverseOrderStatus(
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Cancels all active orders on a symbol. This includes OCO orders.
   *
   * @param symbol
   * @param recvWindow optional
   * @param timestamp
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @DELETE
  @Path("fapi/v1/allOpenOrders")
  BinanceChangeStatus cancelAllFutureOpenOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Get User Commission Rate for a Future symbol.
   *
   * @param symbol
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v1/commissionRate")
  BinanceFutureCommissionRate getFutureCommissionRate(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Order modify function, currently only LIMIT order modification is supported,
   * modified orders will be reordered in the match queue
   *
   * @param orderId optional
   * @param origClientOrderId optional
   * @param symbol
   * @param side
   * @param quantity
   * @param price
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @PUT
  @Path("fapi/v1/order")
  BinanceFutureNewOrder modifyOrder(
      @QueryParam("orderId") Long orderId,
      @QueryParam("origClientOrderId") String origClientOrderId,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") OrderSide side,
      @QueryParam("quantity") BigDecimal quantity,
      @QueryParam("price") BigDecimal price,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * All Orders (USER_DATA)
   * @param symbol
   * @param orderId
   * @param startTime
   * @param endTime
   * @param limit
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("fapi/v1/allOrders")
    List<BinanceOrder> getAllFutureOrders(
        @QueryParam("symbol") String symbol,
        @QueryParam("orderId") Long orderId,
        @QueryParam("startTime") Long startTime,
        @QueryParam("endTime") Long endTime,
        @QueryParam("limit") Integer limit,
        @QueryParam("recvWindow") Long recvWindow,
        @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
        @HeaderParam(X_MBX_APIKEY) String apiKey,
        @QueryParam(SIGNATURE) ParamsDigest signature)
        throws IOException, BinanceException;

  /**
   * Change symbol level margin type
   * @param symbol
   * @param marginType
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @POST
  @Path("/fapi/v1/marginType")
  BinanceChangeStatus setMarginType(
      @FormParam("symbol") String symbol,
      @FormParam("marginType") MarginType marginType,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Change user's position mode (Hedge Mode or One-way Mode ) on EVERY symbol
   * @param dualSidePosition "true": Hedge Mode; "false": One-way Mode
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @POST
  @Path("/fapi/v1/positionSide/dual")
    BinanceChangeStatus setDualSidePosition(
        @FormParam("dualSidePosition") boolean dualSidePosition,
        @FormParam("recvWindow") Long recvWindow,
        @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
        @HeaderParam(X_MBX_APIKEY) String apiKey,
        @QueryParam(SIGNATURE) ParamsDigest signature)
        throws IOException, BinanceException;

  /**
   *
   * @param symbol
   * @param leverage
   * @param recvWindow optional
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @POST
  @Path("/fapi/v1/leverage")
  BinanceSetLeverage setLeverage(
      @FormParam("symbol") String symbol,
      @FormParam("leverage") int leverage,
      @FormParam("recvWindow") Long recvWindow,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Position Information V2 (USER_DATA)
   * @param symbol
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v2/positionRisk")
  List<BinancePosition> getFuturesPositionRisk(
      @QueryParam("symbol") String symbol,
      @QueryParam("recvWindow") Long recvWindow,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam(X_MBX_APIKEY) String apiKey,
      @QueryParam(SIGNATURE) ParamsDigest signature)
      throws IOException, BinanceException;

  /**
   * Position Information V3 (USER_DATA)
   * @param symbol
   * @param recvWindow
   * @param timestamp
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  @GET
  @Path("/fapi/v3/positionRisk")
  List<BinancePosition> getFuturesV3PositionRisk(
          @QueryParam("symbol") String symbol,
          @QueryParam("recvWindow") Long recvWindow,
          @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
          @HeaderParam(X_MBX_APIKEY) String apiKey,
          @QueryParam(SIGNATURE) ParamsDigest signature)
          throws IOException, BinanceException;

}
