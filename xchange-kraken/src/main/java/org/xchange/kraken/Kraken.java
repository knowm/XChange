package org.xchange.kraken;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.xchange.kraken.dto.marketdata.KrakenDepthResult;
import org.xchange.kraken.dto.marketdata.KrakenTickerResult;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;

/**
 * @author Benedikt Bünz
 */
@Path("0/public")
@Produces("application/json")
public interface Kraken {

    @GET
    @Path("Ticker")
    KrakenTickerResult getTicker(@FormParam("pair") String currencyPair);

    @GET
    @Path("Depth")
    KrakenDepthResult getFullDepth(@FormParam("pair") String currencyPair);
    @GET
    @Path("Depth")
    KrakenDepthResult getPartialDepth(@FormParam("pair") String currencyPair,long count);

    @GET
    @Path("Trades")
    KrakenTradesResult getTrades(@FormParam("pair") String currencyPair);

    @GET
    @Path("AssetPairs")
    Object getAssetPairs();
}
