package com.xeiam.xchange.huobi;

import com.xeiam.xchange.huobi.dto.marketdata.HuobiFullTrade;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("market")
@Produces(MediaType.APPLICATION_JSON)

public interface HuobiMarketTrade {

    @GET
    @Path("huobi.php")
    HuobiFullTrade[] getLastTrades(@QueryParam("a") String request) throws IOException;

    @GET
    @Path("huobi.php")
    HuobiFullTrade[] getTradesSince(@QueryParam("a") String request, @QueryParam("since") Long since) throws IOException;
}