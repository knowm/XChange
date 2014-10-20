package com.xeiam.xchange.hitbtc;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

@Path("/api/1/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface HitbtcAuthenticated extends Hitbtc {

  @GET
  @Path("trading/orders/active")
  public HitbtcOrdersResponse getHitbtcActiveOrders(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory,
      @QueryParam("apikey") String apiKey
  /* @QueryParam("symbols") String symbols */) throws IOException, HitbtcException;

  @GET
  @Path("trading/orders/recent")
  HitbtcOrdersResponse getHitbtcRecentOrders(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @QueryParam("max_results") int max_results) throws IOException, HitbtcException;

  @POST
  @Path("trading/new_order")
  public HitbtcExecutionReportResponse postHitbtcNewOrder(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory,
      @QueryParam("apikey") String apiKey, @FormParam("clientOrderId") String clientOrderId, @FormParam("symbol") String symbol, @FormParam("side") String side, @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigInteger quantity, // 1 lot = 0.01 BTC
      @FormParam("type") String type, @FormParam("timeInForce") String timeInForce) throws IOException, HitbtcException;

  @POST
  @Path("trading/cancel_order")
  public HitbtcExecutionReportResponse postHitbtcCancelOrder(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory,
      @QueryParam("apikey") String apiKey, @FormParam("clientOrderId") String clientOrderId, @FormParam("cancelRequestClientOrderId") String cancelRequestClientOrderId,
      @FormParam("symbol") String symbol, @FormParam("side") String side) throws IOException, HitbtcException;

  @GET
  @Path("trading/trades")
  public HitbtcTradeResponse getHitbtcTrades(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey,
      @QueryParam("by") String by, @QueryParam("start_index") int start_index, @QueryParam("max_results") int max_results, @QueryParam("symbols") String symbols, @QueryParam("sort") String sort,
      @QueryParam("from") String from, @QueryParam("till") String till) throws IOException, HitbtcException;

  @GET
  @Path("trading/balance")
  public HitbtcBalanceResponse getHitbtcBalance(@HeaderParam("X-Signature") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory,
      @QueryParam("apikey") String apiKey) throws IOException, HitbtcException;

}
