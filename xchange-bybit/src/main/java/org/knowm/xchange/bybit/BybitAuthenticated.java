package org.knowm.xchange.bybit;

import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;
import org.knowm.xchange.bybit.dto.trade.BybitOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
    ) throws IOException;

    @GET
    @Path("/spot/v1/order")
    BybitResult<BybitOrder> getOrder(
            @QueryParam("api_key") String apiKey,
            @QueryParam("orderId") String orderId,
            @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
            @QueryParam("sign") ParamsDigest signature
    ) throws IOException;




}
