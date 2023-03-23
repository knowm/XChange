package org.knowm.xchange.bybit;

import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;
import org.knowm.xchange.bybit.service.BybitException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/spot/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface BybitAuthenticated {

    @GET
    @Path("/account")
    BybitResult<BybitBalances> getWalletBalances(
            @QueryParam("api_key") String apiKey,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @QueryParam("sign") ParamsDigest signature
    ) throws IOException, BybitException;

    @GET
    @Path("/order")
    BybitResult<BybitOrderDetails> getOrder(
            @QueryParam("api_key") String apiKey,
            @QueryParam("orderId") String orderId,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @QueryParam("sign") ParamsDigest signature
    ) throws IOException, BybitException;

    @POST
    @Path("/order")
    BybitResult<BybitOrderRequest> placeOrder(
            @FormParam("api_key") String apiKey,
            @FormParam("symbol") String symbol,
            @FormParam("qty") long qty,
            @FormParam("side") String side,
            @FormParam("type") String type,
            @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @FormParam("sign") ParamsDigest signature
    ) throws IOException, BybitException;


}
