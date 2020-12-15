package org.knowm.xchange.ftx;


import org.knowm.xchange.ftx.dto.marketdata.FtxMarketsResponse;
import org.knowm.xchange.ftx.dto.marketdata.FtxOrderbookResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface Ftx {

    @GET
    @Path("/markets")
    @Consumes(MediaType.APPLICATION_JSON)
    FtxMarketsResponse getMarkets() throws IOException, FtxException;

    @GET
    @Path("/markets/{market_name}/orderbook")
    FtxOrderbookResponse getOrderbook(
        @PathParam("market_name") String market,
        @QueryParam("depth") int depth
    ) throws IOException, FtxException;
}
