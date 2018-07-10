package org.knowm.xchange.coindirect;

import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTicker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindirect {
    @GET
    @Path("api/v1/exchange/market/book")
    CoindirectOrderbook getExchangeOrderBook(@QueryParam("symbol") String symbol);

    @GET
    @Path("/api/v1/exchange/historical/trades/{market}/{history}")
    CoindirectTicker getHistoricalExchangeTrades(@PathParam("market") String market, @PathParam("history") String history);
}
