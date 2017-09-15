package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcAddress;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcException;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcInternalTransferResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;

import si.mazi.rescu.HttpStatusIOException;

/**
 * Version 2 of HitBtc API. See https://api.hitbtc.com/api/2/explore/
 */
@Path("/api/2/")
public interface HitbtcAuthenticated extends Hitbtc {

  /*************************** Account APIs ******************************/

  @GET
  @Path("account/balance")
  List<HitbtcBalance> getPaymentBalance() throws IOException, HitbtcException;

  @GET
  @Path("account/crypto/address/{currency}")
  HitbtcAddress getHitbtcDepositAddress(@PathParam("currency") String currency) throws IOException, HitbtcException;

  @GET
  @Path("account/transactions")
  List<HitbtcTransaction> transactions() throws HttpStatusIOException;

  @GET
  @Path("account/balance")
  List<HitbtcBalance> getNewBalance() throws IOException, HitbtcException;

  @POST
  @Path("account/transfer")
  HitbtcInternalTransferResponse transferToTrading(@FormParam("amount") BigDecimal amount, @FormParam("currency") String currency,
      @FormParam("type") String type) throws IOException;

  @POST
  @Path("account/crypto/withdraw")
  Map payout(@FormParam("amount") BigDecimal amount, @FormParam("currency") String currency, @FormParam("address") String address) throws HttpStatusIOException;

  /************************ Tradding & Order APIs ************************/

  //TODO add query params
  @GET
  @Path("order")
  List<HitbtcOrder> getHitbtcActiveOrders() throws IOException, HitbtcException;

  //TODO remove usage of HitbtcExecutionReportResponse
  @POST
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcExecutionReportResponse postHitbtcNewOrder(
      @FormParam("clientOrderId") String clientOrderId, @FormParam("symbol") String symbol, @FormParam("side") String side,
      @FormParam("price") BigDecimal price, @FormParam("quantity") BigDecimal quantity,
      @FormParam("type") String type, @FormParam("timeInForce") String timeInForce) throws IOException, HitbtcException;

  @DELETE
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  List<HitbtcOrder> cancelAllOrders(@FormParam("symbol") String symbol) throws IOException, HitbtcException;

  @DELETE
  @Path("order/{clientOrderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcExecutionReportResponse cancelSingleOrder(@PathParam("clientOrderId") String clientOrderId) throws IOException, HitbtcException;

  //TODO Add replace or update order via PATCH with upgrade to JSR311
  // PATCH /order/{clientOrderId}

  @GET
  @Path("trading/balance")
  List<HitbtcBalance> getTradingBalance() throws IOException, HitbtcException;

  /********************* Trading History APIs ******************************/

  //TODO add query params

  /**
   * Get historical trades. There can be one to many trades per order.
   *
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
   *
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

}