package org.knowm.xchange.bitso;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bitso.dto.BitsoBaseResponse;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.trade.*;
import org.knowm.xchange.bitso.service.BitsoDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Bitso API v3/v4 authenticated endpoints
 *
 * @author Benedikt Bünz, Piotr Ładyżyński
 * @see <a href="https://docs.bitso.com/bitso-api/docs/api-overview">Bitso API v3 Documentation</a>
 */
@Path("/api/v3")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BitsoAuthenticated {

  @GET
  @Path("open_orders")
  BitsoBaseResponse<BitsoOrder[]> getOpenOrders(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("book") String book,
      @QueryParam("limit") Integer limit)
      throws BitsoException, IOException;

  @POST
  @Path("orders")
  BitsoBaseResponse<BitsoOrderResponse> placeOrder(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      BitsoOrderRequest orderRequest)
      throws BitsoException, IOException;

  /**
   * @return order IDs if orders have been canceled successfully.
   */
  @DELETE
  @Path("orders/{oid}")
  BitsoBaseResponse<String[]> cancelOrder(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("oid") String orderId)
      throws BitsoException, IOException;

  @DELETE
  @Path("orders")
  BitsoBaseResponse<String[]> cancelOrders(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("oids") String orderIds)
      throws BitsoException, IOException;

  @DELETE
  @Path("orders/all")
  BitsoBaseResponse<String[]> cancelAllOrders(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce)
      throws BitsoException, IOException;

  @GET
  @Path("order_trades/{oid}")
  BitsoBaseResponse<BitsoUserTransaction[]> getOrderTrades(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("oid") String orderId)
      throws BitsoException, IOException;

  @GET
  @Path("order_trades")
  BitsoBaseResponse<BitsoUserTransaction[]> getOrderTradesByOriginId(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("origin_id") String originId)
      throws BitsoException, IOException;

  @GET
  @Path("user_trades")
  BitsoBaseResponse<BitsoUserTransaction[]> getUserTrades(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("limit") Integer limit,
      @QueryParam("sort") String sort,
      @QueryParam("book") String book)
      throws BitsoException, IOException;

  @GET
  @Path("balance")
  BitsoBaseResponse<BitsoBalance> getBalance(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce)
      throws BitsoException, IOException;

  @GET
  @Path("orders/{oid}")
  BitsoBaseResponse<BitsoOrder[]> getOrder(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("oid") String orderId)
      throws BitsoException, IOException;

  @GET
  @Path("orders")
  BitsoBaseResponse<BitsoOrder[]> getOrders(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("oids") String orderIds)
      throws BitsoException, IOException;

  // ==========================================================================
  // Bitso API v4 Endpoints
  // ==========================================================================

  /**
   * Modify an existing order using Bitso API v4 - by order ID (path parameter)
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param orderId Bitso-supplied order ID
   * @param modifyRequest Modification parameters
   * @return Modified order response
   * @throws BitsoException API exception
   * @throws IOException Network exception
   * @see <a href="https://docs.bitso.com/bitso-api/docs/modify-an-order">Modify an Order</a>
   */
  @PATCH
  @Path("api/v4/orders/{oid}")
  BitsoBaseResponse<BitsoOrderResponse> modifyOrderById(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("oid") String orderId,
      BitsoModifyOrderRequest modifyRequest)
      throws BitsoException, IOException;

  /**
   * Modify an existing order using Bitso API v4 - by order ID (query parameter)
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param orderId Bitso-supplied order ID
   * @param modifyRequest Modification parameters
   * @return Modified order response
   * @throws BitsoException API exception
   * @throws IOException Network exception
   * @see <a href="https://docs.bitso.com/bitso-api/docs/modify-an-order">Modify an Order</a>
   */
  @PATCH
  @Path("api/v4/orders")
  BitsoBaseResponse<BitsoOrderResponse> modifyOrderByIdQuery(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("oid") String orderId,
      BitsoModifyOrderRequest modifyRequest)
      throws BitsoException, IOException;

  /**
   * Modify an existing order using Bitso API v4 - by origin ID (query parameter)
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param originId Client-supplied origin ID
   * @param modifyRequest Modification parameters
   * @return Modified order response
   * @throws BitsoException API exception
   * @throws IOException Network exception
   * @see <a href="https://docs.bitso.com/bitso-api/docs/modify-an-order">Modify an Order</a>
   */
  @PATCH
  @Path("api/v4/orders")
  BitsoBaseResponse<BitsoOrderResponse> modifyOrderByOriginId(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("origin_id") String originId,
      BitsoModifyOrderRequest modifyRequest)
      throws BitsoException, IOException;

  /**
   * Request a conversion quote using Bitso API v4
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param quoteRequest Quote request parameters
   * @return Conversion quote with rate and expiration
   * @throws BitsoException API exception
   * @throws IOException Network exception
   * @see <a href="https://docs.bitso.com/bitso-api/docs/request-a-conversion-quote-1">Request a
   *     Conversion Quote</a>
   */
  @POST
  @Path("api/v4/currency_conversions")
  BitsoBaseResponse<BitsoConversionQuoteResponse> requestConversionQuote(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      BitsoConversionQuoteRequest quoteRequest)
      throws BitsoException, IOException;

  /**
   * Execute a conversion quote using Bitso API v4
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param quoteId Quote ID from the request quote call
   * @return Conversion execution response with conversion ID
   * @throws BitsoException API exception
   * @throws IOException Network exception
   * @see <a href="https://docs.bitso.com/bitso-api/docs/execute-a-conversion-quote">Execute a
   *     Conversion Quote</a>
   */
  @PUT
  @Path("api/v4/currency_conversions/{quote_id}")
  BitsoBaseResponse<BitsoConversionExecutionResponse> executeConversionQuote(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("quote_id") String quoteId)
      throws BitsoException, IOException;

  /**
   * Get the status of a conversion using Bitso API v4 (with enhanced "queued" state)
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param conversionId Conversion ID from the execute quote call
   * @return Conversion status with detailed information
   * @throws BitsoException API exception
   * @throws IOException Network exception
   * @see <a href="https://docs.bitso.com/bitso-api/docs/get-a-conversion-status">Get a Conversion
   *     Status</a>
   */
  @GET
  @Path("api/v4/currency_conversions/{conversion_id}")
  BitsoBaseResponse<BitsoConversionStatusResponse> getConversionStatus(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("conversion_id") String conversionId)
      throws BitsoException, IOException;

  // Note: Deposit and withdrawal endpoints are now part of the funding API
  // and will be moved to a separate interface for funding operations
}
