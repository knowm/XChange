package org.xchange.kraken;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.xchange.kraken.dto.KrakenTicker;
import org.xchange.kraken.dto.marketdata.KrakenDepth;

/**
 * @author Benedikt Bünz
 */
@Path("0/public")
@Produces("application/json")
public interface Kraken {

    @GET
    @Path("Ticker")
    @Produces("application/json")
    KrakenTicker getTicker(@FormParam("pair") String currencyPair);

    @GET
    @Path("Depth")
    @Produces("application/json")
    KrakenDepth getFullDepth(@FormParam("pair") String currencyPair);
    @GET
    @Path("Depth")
    @Produces("application/json")
    KrakenDepth getPartialDepth(@FormParam("pair") String currencyPair,long count);

    @GET
    @Path("{ident}_{currency}/trades")
    KrakenTrades[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

}
