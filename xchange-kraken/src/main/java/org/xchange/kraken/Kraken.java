package org.xchange.kraken;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import org.xchange.kraken.dto.marketdata.KrakenDepthResult;
import org.xchange.kraken.dto.marketdata.KrakenTickerResult;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;

import com.xeiam.xchange.dto.marketdata.OrderBook;

/**
 * @author Benedikt Bünz
 */
@Path("0/public")
@Produces("application/json")
public interface Kraken {

    @GET
    @Path("Ticker")
    KrakenTickerResult getTicker(@FormParam("pair") String currencyPair);

    /**
     * 
     * @param currencyPair kraken currency pair
     * @param count can be null = full {@link OrderBook}
     * @return
     */
    @GET
    @Path("Depth")
    KrakenDepthResult getDepth(@FormParam("pair") String currencyPair,Long count);

    @GET
    @Path("Trades")
    KrakenTradesResult getTrades(@FormParam("pair") String currencyPair);

    @GET
    @Path("AssetPairs")
    KrakenAssetPairsResult getAssetPairs();
}
