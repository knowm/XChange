package org.knowm.xchange.bitmax;

import org.knowm.xchange.bitmax.dto.BitmaxResponse;
import org.knowm.xchange.bitmax.dto.account.BitmaxCashAccountBalanceDto;
import org.knowm.xchange.bitmax.dto.trade.BitmaxCancelOrderRequestPayload;
import org.knowm.xchange.bitmax.dto.trade.BitmaxOpenOrdersResponse;
import org.knowm.xchange.bitmax.dto.trade.BitmaxPlaceOrderRequestPayload;
import org.knowm.xchange.bitmax.dto.trade.BitmaxOrderResponse;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Path("api/pro/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IBitmaxAuthenticatedWithAccountGroup extends IBitmax{

    @GET
    @Path("/cash/balance")
    BitmaxResponse<List<BitmaxCashAccountBalanceDto>> getCashAccountBalance(
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature
    ) throws IOException;

    @POST
    @Path("/{account-category}/order")
    BitmaxResponse<BitmaxOrderResponse> placeOrder(
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature,
            @PathParam("account-category") String accountCategory,
//            @QueryParam("symbol") String symbol,
//            @QueryParam("time") Long time,
//            @QueryParam("orderQty") BigDecimal orderQty,
//            @QueryParam("orderType") String orderType,
//            @QueryParam("side") String side,
//            @QueryParam("orderPrice") BigDecimal orderPrice
            BitmaxPlaceOrderRequestPayload payload
    ) throws IOException;

    @DELETE
    @Path("/{account-category}/order")
    BitmaxResponse<BitmaxOrderResponse> cancelOrder(
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature,
            @PathParam("account-category") String accountCategory,
            BitmaxCancelOrderRequestPayload payload
    ) throws IOException;

    @GET
    @Path("/{account-category}/order/open")
    BitmaxResponse<List<BitmaxOpenOrdersResponse>> getOpenOrders(
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature,
            @PathParam("account-category") String accountCategory,
            @QueryParam("symbol") String symbol
    ) throws IOException;

    @GET
    @Path("/{account-category}/order/hist/current")
    BitmaxResponse<List<BitmaxOpenOrdersResponse>> getOrdersHistory(
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature,
            @PathParam("account-category") String accountCategory,
            @QueryParam("n") int numberOfRecords,
            @QueryParam("symbol") String symbol,
            @QueryParam("executedOnly") boolean executedOnly
    ) throws IOException;
}
