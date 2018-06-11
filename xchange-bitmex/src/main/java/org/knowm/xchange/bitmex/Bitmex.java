package org.knowm.xchange.bitmex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitmex.dto.account.*;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexKline;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.bitmex.dto.marketdata.results.BitmexSymbolsAndPromptsResult;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitmex {

  @GET
  @Path("user")
  BitmexAccount getAccount(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException;

  @GET
  @Path("user/wallet")
  BitmexWallet getWallet(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest /*,
           @Nullable @QueryParam("currency") String currency*/)
      throws IOException;

  // Get a history of all of your wallet transactions (deposits, withdrawals, PNL)
  @GET
  @Path("user/walletHistory")
  List<BitmexWalletTransaction> getWalletHistory(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException;

  // Get a summary of all of your wallet transactions (deposits, withdrawals, PNL)
  @GET
  @Path("user/walletSummary")
  List<BitmexWalletTransaction> getWalletSummary(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException;

  @GET
  @Path("user/margin")
  BitmexMarginAccount getMarginAccountStatus(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency)
      throws IOException;

  @GET
  @Path("user/margin?currency=all")
  List<BitmexMarginAccount> getMarginAccountsStatus(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException;

  @GET
  @Path("trade")
  BitmexPublicTrade[] getTrades(
      @QueryParam("symbol") String currencyPair,
      @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Integer start)
      throws IOException;

  @GET
  @Path("trade/bucketed")
  List<BitmexKline> getBucketedTrades(
      @QueryParam("binSize") String binSize,
      @QueryParam("partial") Boolean partial,
      @QueryParam("symbol") String symbol,
      @QueryParam("count") Long count,
      @QueryParam("reverse") Boolean reverse)
      throws IOException;

  @GET
  @Path("orderBook/L2")
  BitmexPublicOrder[] getDepth(
      @QueryParam("symbol") String currencyPair, @QueryParam("depth") Double depth)
      throws IOException;

  @GET
  @Path("position")
  List<BitmexPosition> getPositions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest)
      throws IOException;

  @GET
  @Path("position")
  List<BitmexPosition> getPositions(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter)
      throws IOException;

  @GET
  @Path("instrument")
  List<BitmexTicker> getTickers(
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Integer start,
      @Nullable @QueryParam("reverse") Boolean reverse)
      throws IOException, BitmexException;

  @GET
  @Path("instrument")
  List<BitmexTicker> getTicker(@QueryParam("symbol") String symbol)
      throws IOException, BitmexException;

  @GET
  @Path("instrument/active")
  List<BitmexTicker> getActiveTickers() throws IOException, BitmexException;

  @GET
  @Path("instrument/activeIntervals")
  BitmexSymbolsAndPromptsResult getPromptsAndSymbols() throws IOException, BitmexException;

  @GET
  @Path("order")
  List<BitmexPrivateOrder> getOrders(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Integer start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime);

  /**
   * @param apiKey
   * @param nonce
   * @param paramsDigest
   * @param symbol
   * @param side Order side. Valid options: Buy, Sell. Defaults to 'Buy' unless orderQty or
   *     simpleOrderQty is negative.
   * @param orderQuantity Order quantity in units of the instrument (i.e. contracts).
   * @param simpleOrderQuantity Order quantity in units of the underlying instrument (i.e. Bitcoin).
   * @param price
   * @param stopPrice
   * @param orderType
   * @param executionInstructions
   * @return
   */
  @POST
  @Path("order")
  BitmexPrivateOrder placeOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("orderQty") Integer orderQuantity,
      @FormParam("simpleOrderQty") BigDecimal simpleOrderQuantity,
      @FormParam("price") BigDecimal price,
      @Nullable @FormParam("stopPx") BigDecimal stopPrice,
      @Nullable @FormParam("ordType") String orderType,
      @Nullable @FormParam("clOrdID") String clOrdID,
      @Nullable @FormParam("execInst") String executionInstructions);

  @PUT
  @Path("order")
  // for some reason underlying library doesn't add contenty type for PUT requests automatically
  @Consumes("application/x-www-form-urlencoded")
  BitmexPrivateOrder replaceOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("orderQty") int orderQuantity,
      @Nullable @FormParam("price") BigDecimal price,
      @Nullable @FormParam("stopPx") BigDecimal stopPrice,
      @Nullable @FormParam("ordType") String orderType,
      @Nullable @FormParam("orderID") String orderId,
      @Nullable @FormParam("clOrdID") String clOrdID,
      @Nullable @FormParam("origClOrdID") String origClOrdID);

  @DELETE
  @Path("order")
  List<BitmexPrivateOrder> cancelOrder(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @FormParam("orderID") String orderID,
      @Nullable @FormParam("clOrdID") String clOrdID);

  @DELETE
  @Path("order/all")
  List<BitmexPrivateOrder> cancelAllOrders(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @Nullable @FormParam("symbol") String symbol,
      @Nullable @FormParam("filter") String filter,
      @Nullable @FormParam("text") String text);

  @GET
  @Path("user/depositAddress")
  String getDepositAddress(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @QueryParam("currency") String currency);

  @POST
  @Path("user/requestWithdrawal")
  BitmexWalletTransaction withdrawFunds(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address);

  @POST
  @Path("position/leverage")
  BitmexPosition updateLeveragePosition(
      @HeaderParam("api-key") String apiKey,
      @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("api-signature") ParamsDigest paramsDigest,
      @FormParam("symbol") String symbol,
      @FormParam("leverage") BigDecimal leverage);
}
