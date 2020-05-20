package org.knowm.xchange.bitfinex.v2;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitfinex.v2.dto.BitfinexExceptionV2;
import org.knowm.xchange.bitfinex.v2.dto.EmptyRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.LedgerEntry;
import org.knowm.xchange.bitfinex.v2.dto.account.Movement;
import org.knowm.xchange.bitfinex.v2.dto.trade.ActiveOrder;
import org.knowm.xchange.bitfinex.v2.dto.trade.OrderTrade;
import org.knowm.xchange.bitfinex.v2.dto.trade.Position;
import org.knowm.xchange.bitfinex.v2.dto.trade.Trade;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BitfinexAuthenticated extends Bitfinex {

  public static final String BFX_APIKEY = "bfx-apikey";
  public static final String BFX_SIGNATURE = "bfx-signature";
  public static final String BFX_NONCE = "bfx-nonce";

  /** https://docs.bitfinex.com/v2/reference#rest-auth-positions */
  @POST
  @Path("auth/r/positions")
  List<Position> activePositions(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  /**
   * https://docs.bitfinex.com/v2/reference#rest-auth-trades-hist
   *
   * <p>Two implementations: 1. returns trades of all symboles 2. returns trades of a specific
   * symbol
   *
   * <p>This is necessary because @Path doesn't seems to support optional parameters
   */
  @POST
  @Path("auth/r/trades/hist")
  List<Trade> getTrades(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Long limit,
      @QueryParam("sort") Long sort,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("auth/r/trades/{symbol}/hist")
  List<Trade> getTrades(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("symbol") String symbol,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Long limit,
      @QueryParam("sort") Long sort,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  /** https://docs.bitfinex.com/v2/reference#rest-auth-orders */
  @POST
  @Path("auth/r/orders/{symbol}")
  List<ActiveOrder> getActiveOrders(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("symbol") String symbol,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  /** https://docs.bitfinex.com/reference#rest-auth-ledgers */
  @POST
  @Path("auth/r/ledgers/hist")
  List<LedgerEntry> getLedgerEntries(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Long limit,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("auth/r/ledgers/{currency}/hist")
  List<LedgerEntry> getLedgerEntries(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("currency") String currency,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Long limit,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  /** https://docs.bitfinex.com/reference#rest-auth-order-trades * */
  @POST
  @Path("auth/r/order/{symbol}:{orderId}/trades")
  List<OrderTrade> getOrderTrades(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("symbol") String symbol,
      @PathParam("orderId") Long orderId,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("/auth/r/movements/{symbol}/hist")
  List<Movement> getMovementsHistory(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("symbol") String symbol,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Integer limit,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("/auth/r/movements/hist")
  List<Movement> getMovementsHistory(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Integer limit,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;
}
