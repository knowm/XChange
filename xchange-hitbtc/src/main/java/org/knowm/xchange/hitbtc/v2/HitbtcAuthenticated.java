package org.knowm.xchange.hitbtc.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcAddress;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcException;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcInternalTransferResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.hitbtc.v2.service.HitbtcOrderType;
import org.knowm.xchange.hitbtc.v2.service.HitbtcTimeInForce;
import si.mazi.rescu.HttpStatusIOException;

/** Version 2 of HitBtc API. See https://api.hitbtc.com/api/2/explore/ */
@Path("/api/2/")
public interface HitbtcAuthenticated extends Hitbtc {

  /** ************************* Account APIs ***************************** */
  @GET
  @Path("account/balance")
  List<HitbtcBalance> getMainBalance() throws IOException, HitbtcException;

  @GET
  @Path("account/crypto/address/{currency}")
  HitbtcAddress getHitbtcDepositAddress(@PathParam("currency") String currency)
      throws IOException, HitbtcException;

  @GET
  @Path("account/transactions")
  List<HitbtcTransaction> transactions(
      @QueryParam("currency") String currency,
      @QueryParam("sort") String sort,
      @QueryParam("by") String by,
      @QueryParam("from") String from,
      @QueryParam("till") String till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset)
      throws HitbtcException, HttpStatusIOException;

  @POST
  @Path("account/transfer")
  HitbtcInternalTransferResponse transferToTrading(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("type") String type)
      throws IOException, HitbtcException;

  @POST
  @Path("account/crypto/withdraw")
  Map payout(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("address") String address,
      @FormParam("paymentId") String paymentId,
      @FormParam("includeFee") Boolean includeFee)
      throws HitbtcException, HttpStatusIOException;

  /** ********************** Tradding & Order APIs *********************** */

  // TODO add query params
  @GET
  @Path("order")
  List<HitbtcOrder> getHitbtcActiveOrders() throws IOException, HitbtcException;

  @POST
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcOrder postHitbtcNewOrder(
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("type") HitbtcOrderType type,
      @FormParam("timeInForce") HitbtcTimeInForce timeInForce)
      throws IOException, HitbtcException;

  @PATCH
  @Path("order/{clientOrderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  HitbtcOrder updateHitbtcOrder(
      @PathParam("clientOrderId") String clientOrderId,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("requestClientId") String requestClientId,
      @FormParam("price") BigDecimal price)
      throws IOException, HitbtcException;

  @DELETE
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  List<HitbtcOrder> cancelAllOrders(@FormParam("symbol") String symbol)
      throws IOException, HitbtcException;

  @DELETE
  @Path("order/{clientOrderId}")
  HitbtcOrder cancelSingleOrder(@PathParam("clientOrderId") String clientOrderId)
      throws IOException, HitbtcException;

  @GET
  @Path("trading/balance")
  List<HitbtcBalance> getTradingBalance() throws IOException, HitbtcException;

  /** ******************* Trading History APIs ***************************** */
  @GET
  @Path("history/trades")
  List<HitbtcOwnTrade> getHitbtcTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("sort") String sort,
      @QueryParam("by") String sortBy,
      @QueryParam("from") String from,
      @QueryParam("till") String till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") long offset)
      throws IOException, HitbtcException;

  // TODO add query params

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
   * @param symbol symbol
   * @param clientOrderId client order id
   * @return list of orders
   * @throws IOException throw in case IO problems
   * @throws HitbtcException throw in case internal HITBTC problems
   */
  @GET
  @Path("history/order")
  List<HitbtcOrder> getHitbtcOrder(
      @QueryParam("symbol") String symbol, @QueryParam("clientOrderId") String clientOrderId)
      throws IOException, HitbtcException;

  @GET
  @Path("/history/order/{id}/trades")
  List<HitbtcOwnTrade> getHistorialTradesByOrder(@PathParam("id") String orderId)
      throws IOException, HitbtcException;
}