package org.knowm.xchange.ascendex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.account.AscendexCashAccountBalanceDto;
import org.knowm.xchange.ascendex.dto.trade.AscendexOpenOrdersResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexOrderResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexPlaceOrderRequestPayload;
import si.mazi.rescu.ParamsDigest;

@Path("api/pro/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IAscendexAuthenticated extends IAscendex {

  @GET
  @Path("/cash/balance")
  AscendexResponse<List<AscendexCashAccountBalanceDto>> getCashAccountBalance(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("/{account-category}/order")
  AscendexResponse<AscendexOrderResponse> placeOrder(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      AscendexPlaceOrderRequestPayload payload)
      throws IOException;

  @DELETE
  @Path("/{account-category}/order")
  AscendexResponse<AscendexOrderResponse> cancelOrder(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("orderId") String orderId,
      @QueryParam("symbol") String symbol,
      @QueryParam("time") Long time)
      throws IOException;

  @DELETE
  @Path("/{account-category}/order/all")
  AscendexResponse<AscendexOrderResponse> cancelAllOrders(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("/{account-category}/order/open")
  AscendexResponse<List<AscendexOpenOrdersResponse>> getOpenOrders(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("/{account-category}/order/status")
  AscendexResponse<AscendexOpenOrdersResponse> getOrderById(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("orderId") String orderId)
      throws IOException;

  @GET
  @Path("/{account-category}/order/hist/current")
  AscendexResponse<List<AscendexOpenOrdersResponse>> getOrdersHistory(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("n") int numberOfRecords,
      @QueryParam("symbol") String symbol,
      @QueryParam("executedOnly") boolean executedOnly)
      throws IOException;
}
