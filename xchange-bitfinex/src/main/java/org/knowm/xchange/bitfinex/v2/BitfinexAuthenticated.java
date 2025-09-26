package org.knowm.xchange.bitfinex.v2;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitfinex.v2.dto.BitfinexExceptionV2;
import org.knowm.xchange.bitfinex.v2.dto.EmptyRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.BitfinexLedgerEntry;
import org.knowm.xchange.bitfinex.v2.dto.account.BitfinexMovement;
import org.knowm.xchange.bitfinex.v2.dto.account.BitfinexWallet;
import org.knowm.xchange.bitfinex.v2.dto.account.LedgerRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.TransferBetweenWalletsRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.TransferBetweenWalletsResponse;
import org.knowm.xchange.bitfinex.v2.dto.account.UpdateCollateralDerivativePositionRequest;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexOpenOrdersRequest;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexOrderDetails;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexOrdersHistoryRequest;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexPosition;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexTrade;
import org.knowm.xchange.bitfinex.v2.dto.trade.OrderTrade;
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
  List<BitfinexPosition> activePositions(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  /** https://docs.bitfinex.com/reference#rest-auth-wallets */
  @POST
  @Path("auth/r/wallets")
  List<BitfinexWallet> getWallets(
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
  List<BitfinexTrade> getTrades(
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
  List<BitfinexTrade> getTrades(
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

  @POST
  @Path("auth/r/orders/{symbol}")
  List<BitfinexOrderDetails> getActiveOrdersBySymbol(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("symbol") String symbol,
      BitfinexOpenOrdersRequest request)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("auth/r/orders")
  List<BitfinexOrderDetails> getActiveOrders(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      BitfinexOpenOrdersRequest request)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("auth/r/orders/{symbol}/hist")
  List<BitfinexOrderDetails> getOrdersHistoryBySymbol(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("symbol") String symbol,
      BitfinexOrdersHistoryRequest request)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("auth/r/orders/hist")
  List<BitfinexOrderDetails> getOrdersHistory(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      BitfinexOrdersHistoryRequest request)
      throws IOException, BitfinexExceptionV2;

  /** https://docs.bitfinex.com/reference#rest-auth-ledgers */
  @POST
  @Path("auth/r/ledgers/hist")
  List<BitfinexLedgerEntry> getLedgerEntries(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Integer limit,
      LedgerRequest req)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("auth/r/ledgers/{currency}/hist")
  List<BitfinexLedgerEntry> getLedgerEntries(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @PathParam("currency") String currency,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Integer limit,
      LedgerRequest req)
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
  List<BitfinexMovement> getMovementsHistory(
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
  List<BitfinexMovement> getMovementsHistory(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      @QueryParam("start") Long startTimeMillis,
      @QueryParam("end") Long endTimeMillis,
      @QueryParam("limit") Integer limit,
      EmptyRequest empty)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("/auth/w/transfer")
  TransferBetweenWalletsResponse transferBetweenWallets(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      TransferBetweenWalletsRequest req)
      throws IOException, BitfinexExceptionV2;

  @POST
  @Path("/auth/w/deriv/collateral/set")
  List<List<Integer>> updateCollateralDerivativePosition(
      @HeaderParam(BFX_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(BFX_APIKEY) String apiKey,
      @HeaderParam(BFX_SIGNATURE) ParamsDigest signature,
      UpdateCollateralDerivativePositionRequest req)
      throws IOException, BitfinexExceptionV2;
}
