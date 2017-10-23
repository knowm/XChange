package org.knowm.xchange.hitbtc;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.InternalTransferResponse;
import org.knowm.xchange.hitbtc.dto.TransactionsResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcDepositAddressResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcPaymentBalanceResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcMultiExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/1/")
public interface HitbtcAuthenticated extends Hitbtc {

  @GET
  @Path("trading/orders/active")
  HitbtcOrdersResponse getHitbtcActiveOrders(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey
  /* @QueryParam("symbols") String symbols */) throws IOException, HitbtcException;

  @GET
  @Path("trading/orders/recent")
  HitbtcOrdersResponse getHitbtcRecentOrders(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @QueryParam("max_results") int max_results) throws IOException, HitbtcException;

  @POST
  @Path("trading/new_order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcExecutionReportResponse postHitbtcNewOrder(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @FormParam("clientOrderId") String clientOrderId, @FormParam("symbol") String symbol, @FormParam("side") String side,
      @FormParam("price") BigDecimal price, @FormParam("quantity") BigInteger quantity, // 1 lot = 0.01 BTC
      @FormParam("type") String type, @FormParam("timeInForce") String timeInForce) throws IOException, HitbtcException;

  @POST
  @Path("trading/cancel_orders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcMultiExecutionReportResponse postHitbtcCancelOrders(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @FormParam("symbol") String symbol, @FormParam("side") String side) throws IOException, HitbtcException;

  @POST
  @Path("trading/cancel_order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcExecutionReportResponse postHitbtcCancelOrder(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @FormParam("clientOrderId") String clientOrderId, @FormParam("cancelRequestClientOrderId") String cancelRequestClientOrderId,
      @FormParam("symbol") String symbol, @FormParam("side") String side) throws IOException, HitbtcException;

  @GET
  @Path("trading/trades")
  HitbtcTradeResponse getHitbtcTrades(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey, @QueryParam("by") String by,
      @QueryParam("start_index") int start_index, @QueryParam("max_results") int max_results, @QueryParam("symbols") String symbols,
      @QueryParam("sort") String sort, @QueryParam("from") String from, @QueryParam("till") String till) throws IOException, HitbtcException;

  @GET
  @Path("trading/balance")
  HitbtcBalanceResponse getTradingBalance(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey) throws IOException, HitbtcException;

  @GET
  @Path("payment/balance")
  HitbtcPaymentBalanceResponse getPaymentBalance(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey) throws IOException, HitbtcException;

  @GET
  @Path("payment/address/{currency}")
  HitbtcDepositAddressResponse getHitbtcDepositAddress(@PathParam("currency") String currency, @HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey) throws IOException, HitbtcException;

  @POST
  @Path("payment/payout")
  Map payout(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @FormParam("amount") BigDecimal amount, @FormParam("currency_code") String currency, @FormParam("address") String address, @FormParam("extra_id") String extraId, @FormParam("recommended_fee") boolean recommendedFee
  ) throws HttpStatusIOException;

  @GET
  @Path("payment/transactions")
  TransactionsResponse transactions(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @QueryParam("offset") Long offset, @QueryParam("limit") long limit, @QueryParam("dir") String direction) throws HttpStatusIOException;

  @POST
  @Path("payment/transfer_to_trading")
  InternalTransferResponse transferToTrading(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @FormParam("amount") BigDecimal amount, @FormParam("currency_code") String currency) throws HttpStatusIOException;

  @POST
  @Path("payment/transfer_to_main")
  InternalTransferResponse transferToMain(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @FormParam("amount") BigDecimal amount, @FormParam("currency_code") String currency) throws HttpStatusIOException;
}
