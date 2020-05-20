package org.knowm.xchange.latoken;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.latoken.dto.LatokenException;
import org.knowm.xchange.latoken.dto.account.LatokenBalance;
import org.knowm.xchange.latoken.dto.trade.LatokenCancelledOrders;
import org.knowm.xchange.latoken.dto.trade.LatokenNewOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderSide;
import org.knowm.xchange.latoken.dto.trade.LatokenTestOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenUserTrades;
import org.knowm.xchange.latoken.dto.trade.OrderSubclass;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.ParamsDigest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface LatokenAuthenticated extends Latoken {

  /** Header's key for client's secret API key */
  public static final String API_KEY = "X-LA-KEY";

  /**
   * Header's key for request signature. <br>
   * The signature is created by using the secret {@link #API_KEY} to sign the {@code api-endpoint +
   * query-string} of the request. <br>
   * See Latoken documentation for further details.
   *
   * @see {@link LatokenHmacDigest}
   */
  public static final String SIGNATURE = "X-LA-SIGNATURE";

  /**
   * Header's key for hash function (Optional). Allowed values: {@link BaseParamsDigest#HMAC_SHA_256
   * HMAC-SHA256} (default), {@link BaseParamsDigest#HMAC_SHA_384 HMAC-SHA384}, {@link
   * BaseParamsDigest#HMAC_SHA_512 HMAC-SHA512}
   */
  public static final String HASHTYPE = "X-LA-HASHTYPE";

  @POST
  @Path("api/v1/Order/new")
  /**
   * Placing a new order
   *
   * @param symbol - Symbol of traded pair (example: LAETH)
   * @param clientOrderId - ID of order set by client (example: myNewOrder)
   * @param side - Order side (buy or sell)
   * @param price - Price of order
   * @param amount - Amount of order
   * @param type - Order type (only limit)
   * @param timestamp - Time of request in milliseconds (example: 1555515807369)
   * @param dummy - Mock string, used to force request's body to have value (e.g "hello")
   * @param apiKey - Client's secret API key
   * @param signature - The signature of the request
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenNewOrder newOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("cliOrdId") String clientOrderId,
      @QueryParam("side") LatokenOrderSide side,
      @QueryParam("price") BigDecimal price,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("orderType") OrderSubclass type,
      @QueryParam("timestamp") long timestamp,
      // Query sent with empty body results in error 411
      // Use dummy string to force request's body to have value
      @FormParam("dummy") String dummy,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @POST
  @Path("api/v1/Order/test-order")
  /**
   * Placing a new order
   *
   * @param symbol - Symbol of traded pair (example: LAETH)
   * @param clientOrderId - ID of order set by client (example: myNewOrder)
   * @param side - Order side (buy or sell)
   * @param price - Price of order
   * @param amount - Amount of order
   * @param type - Order type (only limit)
   * @param timestamp - Time of request in milliseconds (example: 1555515807369)
   * @param dummy - Mock string, used to force request's body to have value (e.g "hello")
   * @param apiKey - Client's secret API key
   * @param signature - The signature of the request
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenTestOrder testNewOrder(
      @QueryParam("symbol") String symbol,
      @QueryParam("cliOrdId") String clientOrderId,
      @QueryParam("side") LatokenOrderSide side,
      @QueryParam("price") BigDecimal price,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("orderType") OrderSubclass type,
      @QueryParam("timestamp") long timestamp,
      // Query sent with empty body results in error 411
      // Use dummy string to force request's body to have value
      @FormParam("dummy") String dummy,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @POST
  @Path("api/v1/Order/cancel")
  /**
   * Cancel desired order by ID
   *
   * @param orderId - Id of order to cancel (e.g. '1555418536.649142.126767@0371:1')
   * @param timestamp - Time of request in milliseconds (example: 1555515807369)
   * @param dummy - Mock string, used to force request's body to have value (e.g "hello")
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenOrder cancelOrder(
      @QueryParam("orderId") String orderId,
      @QueryParam("timestamp") long timestamp,
      // Query sent with empty body results in error 411
      // Use dummy string to force request's body to have value
      @FormParam("dummy") String dummy,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @POST
  @Path("api/v1/Order/cancel_all")
  /**
   * Cancel all orders on traded pair
   *
   * @param symbol - Symbol of traded pair (e.g. LAETH)
   * @param timestamp - Time of request in milliseconds (example: 1555515807369)
   * @param dummy - Mock string, used to force request's body to have value (e.g "hello")
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenCancelledOrders cancelAll(
      @QueryParam("symbol") String symbol,
      @QueryParam("timestamp") long timestamp,
      // Query sent with empty body results in error 411
      // Use dummy string to force request's body to have a value
      @FormParam("dummy") String dummy,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/Order/get_order")
  /**
   * Returns info about client order by order ID
   *
   * @param orderId - ID of order to fetch (e.g. '1555418536.649142.126767@0371:1')
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenOrder getOrder(
      @QueryParam("orderId") String orderId,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/Order/status")
  /**
   * Get all orders by trading symbol with requested status
   *
   * @param symbol - Symbol of traded pair (e.g. LAETH)
   * @param status - Status of order ['active', 'partiallyFilled', 'filled', 'cancelled'] (default:
   *     'active')
   * @param limit - Number of orders to fetch (default: 100)
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenOrder> getOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("status") String status,
      @QueryParam("limit") int limit,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/Order/active")
  /**
   * Get all active orders on traded pair
   *
   * @param symbol - Symbol of traded pair (e.g. LAETH)
   * @param timestamp - Time of request in milliseconds (e.g.: 1555515807369)
   * @param limit - Number of orders to fetch (default: 50)
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenOrder> getOpenOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("timestamp") long timestamp,
      @QueryParam("limit") int limit,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/Order/trades")
  /**
   * Users latest trades by pair
   *
   * @param symbol - Symbol of traded pair (e.g. LAETH)
   * @param timestamp - Time of request in milliseconds (e.g.: 1555515807369)
   * @param limit - Number of orders to fetch (default: 50)
   * @param apiKey
   * @param signature
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenUserTrades getUserTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("timestamp") long timestamp,
      @QueryParam("limit") int limit,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/Account/balances")
  /**
   * Get all balances
   *
   * @param timestamp - Request timestamp (in unix milliseconds)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenBalance> getBalances(
      @QueryParam("timestamp") Long timestamp,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/Account/balances/{currency}")
  /**
   * Get balance for desired currency
   *
   * @param currency - Symbol of currency ( e.g. LA)
   * @param timestamp - Request timestamp (in unix milliseconds)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenBalance getBalance(
      @PathParam("currency") String currency,
      @QueryParam("timestamp") Long timestamp,
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(SIGNATURE) ParamsDigest signature)
      throws IOException, LatokenException;
}
