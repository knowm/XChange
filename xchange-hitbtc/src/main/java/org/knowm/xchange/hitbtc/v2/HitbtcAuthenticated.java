package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
  List<HitbtcBalance> getMainBalance() throws IOException, HitbtcException;

  @GET
  @Path("account/crypto/address/{currency}")
  HitbtcAddress getHitbtcDepositAddress(@PathParam("currency") String currency) throws IOException, HitbtcException;

  @GET
  @Path("account/transactions")
  List<HitbtcTransaction> transactions(@QueryParam("currency") String currency, @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset) throws HttpStatusIOException;

  @POST
  @Path("account/transfer")
  HitbtcInternalTransferResponse transferToTrading(@FormParam("amount") BigDecimal amount, @FormParam("currency") String currency,
      @FormParam("type") String type) throws IOException;

  @POST
  @Path("account/crypto/withdraw")
  Map payout(@FormParam("amount") BigDecimal amount, @FormParam("currency") String currency, @FormParam("address") String address,
      @FormParam("paymentId") String paymentId) throws HttpStatusIOException;

  /************************ Tradding & Order APIs ************************/

  //TODO add query params
  @GET
  @Path("order")
  List<HitbtcOrder> getHitbtcActiveOrders() throws IOException, HitbtcException;

  @POST
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcOrder postHitbtcNewOrder(@FormParam("clientOrderId") String clientOrderId, @FormParam("symbol") String symbol, @FormParam("side") String side,
      @FormParam("price") BigDecimal price, @FormParam("quantity") BigDecimal quantity, @FormParam("type") String type,
      @FormParam("timeInForce") String timeInForce) throws IOException, HitbtcException;

  @PATCH
  @Path("order/{clientOrderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcOrder updateHitbtcOrder(@PathParam("clientOrderId") String clientOrderId, @FormParam("quantity") BigDecimal quantity,
      @FormParam("requestClientId") String requestClientId, @FormParam("price") BigDecimal price) throws IOException, HitbtcException;

  @DELETE
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  List<HitbtcOrder> cancelAllOrders(@FormParam("symbol") String symbol) throws IOException, HitbtcException;

  @DELETE
  @Path("order/{clientOrderId}")
  HitbtcOrder cancelSingleOrder(@PathParam("clientOrderId") String clientOrderId) throws IOException, HitbtcException;

  @GET
  @Path("trading/balance")
  List<HitbtcBalance> getTradingBalance() throws IOException, HitbtcException;

  /********************* Trading History APIs ******************************/

  @GET
  @Path("history/trades")
  List<HitbtcOwnTrade> getHitbtcTrades(@QueryParam("symbol") String symbol, @QueryParam("sort") String sort, @QueryParam("by") String sortBy,
      @QueryParam("from") String from, @QueryParam("till") String till, @QueryParam("limit") long limit,
      @QueryParam("offset") long offset) throws IOException, HitbtcException;

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

  /**
   * Get an old order. The returning collection contains, at most, 1 element.
   *
   * @return
   * @throws IOException
   * @throws HitbtcException
   */
  @GET
  @Path("history/order")
  List<HitbtcOrder> getHitbtcOrder(@PathParam("symbol") String symbol,
      @PathParam("clientOrderId") String clientOrderId) throws IOException, HitbtcException;

  @GET
  @Path("/history/order/{id}/trades")
  List<HitbtcOwnTrade> getHistorialTradesByOrder(@PathParam("id") String orderId) throws IOException, HitbtcException;

}
