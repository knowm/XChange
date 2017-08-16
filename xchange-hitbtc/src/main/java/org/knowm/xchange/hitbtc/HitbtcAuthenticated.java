package org.knowm.xchange.hitbtc;

import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.InternalTransferResponse;
import org.knowm.xchange.hitbtc.dto.TransactionsResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcDepositAddressResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcPaymentBalanceResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcMultiExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Path("/api/2/")
public interface HitbtcAuthenticated extends Hitbtc {


  // Account APIs

  @GET
  @Path("account/balance")
  HitbtcBalance[] getNewBalance() throws IOException, HitbtcException;


  //Trading APIs



  // Trading History APIs

  //TODO add query params
  /**
   * Get historical trades. There can be one to many trades per order.
   * @return
   * @throws IOException
   * @throws HitbtcException
   */
  @GET
  @Path("history/trades")
  List<HitbtcOwnTrade> getHitbtcTrades() throws IOException, HitbtcException;

  //TODO add query params
  /**
   * Get historical orders
   * @return
   * @throws IOException
   * @throws HitbtcException
   */
  @GET
  @Path("history/order")
  List<HitbtcOrder> getHitbtcRecentOrders() throws IOException, HitbtcException;


  @GET
  @Path("/history/order/{id}/trades")
  List<HitbtcOwnTrade> getHistorialTradesByOrder(@PathParam("id") String orderId) throws IOException, HitbtcException;












  //Old APIs

  @GET
  @Path("trading/orders/active")
  List<HitbtcOrder> getHitbtcActiveOrders(@HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("apikey") String apiKey
      ) throws IOException, HitbtcException;


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
