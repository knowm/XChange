package org.knowm.xchange.dsx;

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
import org.knowm.xchange.dsx.dto.*;
import org.knowm.xchange.dsx.service.DsxOrderType;
import org.knowm.xchange.dsx.service.DsxTimeInForce;
import si.mazi.rescu.HttpStatusIOException;

/** Version 2 of Dsx API. See https://api.dsx.com/api/2/explore/ */
@Path("/api/2/")
public interface DsxAuthenticated extends Dsx {

  /** ************************* Account APIs ***************************** */
  @GET
  @Path("account/balance")
  List<DsxBalance> getMainBalance() throws IOException, DsxException;

  @GET
  @Path("account/crypto/address/{currency}")
  DsxAddress getDsxDepositAddress(@PathParam("currency") String currency)
      throws IOException, DsxException;

  @GET
  @Path("account/transactions")
  List<DsxTransaction> transactions(
      @QueryParam("currency") String currency,
      @QueryParam("sort") DsxSort sort,
      @QueryParam("by") String by,
      @QueryParam("from") Long from,
      @QueryParam("till") Long till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset)
      throws DsxException, HttpStatusIOException;

  @POST
  @Path("account/transfer")
  DsxInternalTransferResponse transferToTrading(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("type") String type)
      throws IOException, DsxException;

  @POST
  @Path("account/crypto/withdraw")
  Map payout(
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("address") String address,
      @FormParam("paymentId") String paymentId,
      @FormParam("includeFee") Boolean includeFee)
      throws DsxException, HttpStatusIOException;

  /** ********************** Tradding & Order APIs *********************** */

  // TODO add query params
  @GET
  @Path("order")
  List<DsxOrder> getDsxActiveOrders() throws IOException, DsxException;

  @POST
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  DsxOrder postDsxNewOrder(
      @FormParam("clientOrderId") String clientOrderId,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("type") DsxOrderType type,
      @FormParam("timeInForce") DsxTimeInForce timeInForce)
      throws IOException, DsxException;

  @PATCH
  @Path("order/{clientOrderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  DsxOrder updateDsxOrder(
      @PathParam("clientOrderId") String clientOrderId,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("requestClientId") String requestClientId,
      @FormParam("price") BigDecimal price)
      throws IOException, DsxException;

  @DELETE
  @Path("order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  List<DsxOrder> cancelAllOrders(@FormParam("symbol") String symbol)
      throws IOException, DsxException;

  @DELETE
  @Path("order/{clientOrderId}")
  DsxOrder cancelSingleOrder(@PathParam("clientOrderId") String clientOrderId)
      throws IOException, DsxException;

  @GET
  @Path("trading/balance")
  List<DsxBalance> getTradingBalance() throws IOException, DsxException;

  /** ******************* Trading History APIs ***************************** */
  @GET
  @Path("history/trades")
  List<DsxOwnTrade> getDsxTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("sort") String sort,
      @QueryParam("by") String sortBy,
      @QueryParam("from") String from,
      @QueryParam("till") String till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") long offset)
      throws IOException, DsxException;

  // TODO add query params

  /**
   * Get historical orders
   *
   * @return
   * @throws IOException
   * @throws DsxException
   */
  @GET
  @Path("history/order")
  List<DsxOrder> getDsxRecentOrders() throws IOException, DsxException;

  /**
   * Get an old order. The returning collection contains, at most, 1 element.
   *
   * @param symbol symbol
   * @param clientOrderId client order id
   * @return list of orders
   * @throws IOException throw in case IO problems
   * @throws DsxException throw in case internal DSX problems
   */
  @GET
  @Path("history/order")
  List<DsxOrder> getDsxOrder(
      @QueryParam("symbol") String symbol, @QueryParam("clientOrderId") String clientOrderId)
      throws IOException, DsxException;

  @GET
  @Path("/history/order/{id}/trades")
  List<DsxOwnTrade> getHistorialTradesByOrder(@PathParam("id") String orderId)
      throws IOException, DsxException;
}
