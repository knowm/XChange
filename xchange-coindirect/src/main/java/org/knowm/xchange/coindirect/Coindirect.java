package org.knowm.xchange.coindirect;

import org.knowm.xchange.coindirect.dto.marketdata.CoindirectMarket;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTicker;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTrades;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindirect {
    @GET
    @Path("api/v1/exchange/market/book")
    CoindirectOrderbook getExchangeOrderBook(@QueryParam("symbol") String symbol);

    @GET
    @Path("/api/v1/exchange/historical/trades/{market}/{history}")
    CoindirectTrades getHistoricalExchangeTrades(@PathParam("market") String market, @PathParam("history") String history);

    @GET
    @Path("/api/v1/exchange/historical/{market}/{history}/{grouping}")
    CoindirectTicker getHistoricalExchangeData(@PathParam("market") String market, @PathParam("history") String history, @PathParam("grouping") String grouping);

    @GET
    @Path("/api/v1/exchange/market")
    List<CoindirectMarket> listExchangeMarkets(@QueryParam("max") long max);
}
