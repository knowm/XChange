package org.knowm.xchange.coindeal;

import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.dto.Order;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("api/v1/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface CoindealAuthenticated {

    String HEADER_AUTH = "authorization";
    @GET
    @Path("history/trades")
    CoindealTradeHistory[] getTradeHistory(
            @HeaderParam(HEADER_AUTH)ParamsDigest credentials,
            @QueryParam("symbol") String currencyPair,
            @QueryParam("limit") int limit);
    @POST
    @Path("order")
    CoindealOrder placeOrder(
            @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
            @FormParam("symbol") String symbol,
            @FormParam("side") String side,
            @FormParam("type") String type,
            @FormParam("timeInForce") String timeInForce,
            @FormParam("quantity") double quantity,
            @FormParam("price") double price);

    @DELETE
    @Path("order")
    CoindealOrder[] deleteOrders(
            @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
            @FormParam("symbol") String symbol);


    @DELETE
    @Path("order/{clientOrderId}")
    CoindealOrder deleteOrderById(
            @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
            @PathParam("clientOrderId") String cliendOrderId
    );
}
