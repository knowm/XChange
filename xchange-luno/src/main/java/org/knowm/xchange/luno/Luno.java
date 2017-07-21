package org.knowm.xchange.luno;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.luno.dto.LunoException;
import org.knowm.xchange.luno.dto.marketdata.LunoOrderBook;
import org.knowm.xchange.luno.dto.marketdata.LunoTicker;
import org.knowm.xchange.luno.dto.marketdata.LunoTickers;
import org.knowm.xchange.luno.dto.marketdata.LunoTrades;

/**
 * @see https://www.luno.com/en/api
 */
@Path("api/1")
@Produces(MediaType.APPLICATION_JSON)
public interface Luno {

    /**
     * Market data API calls can be accessed by anyone without authentication.
     * @param pair required - Currency pair e.g. XBTZAR
     * @return
     * @throws IOException
     * @throws LunoException
     */
    @GET
    @Path("ticker")
    LunoTicker ticker(@QueryParam("pair") String pair) throws IOException, LunoException;

    /**
     * Returns the latest ticker indicators from all active Luno exchanges.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    @GET
    @Path("tickers")
    LunoTickers tickers() throws IOException, LunoException;

    /**
     * Returns a list of bids and asks in the order book. Ask orders are sorted by price ascending. Bid orders are sorted by 
     * price descending. Note that multiple orders at the same price are not necessarily conflated.
     * @param pair required - Currency pair e.g. XBTZAR
     * @return
     * @throws IOException
     * @throws LunoException
     */
    @GET
    @Path("orderbook")
    LunoOrderBook orderbook(@QueryParam("pair") String pair) throws IOException, LunoException;
    
    /**
     * Returns a list of the most recent trades. At most 100 results are returned per call.
     * @param pair required - Currency pair e.g. XBTZAR
     * @param since optional - Fetch trades executed after this time, specified as a Unix timestamp in milliseconds.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    @GET
    @Path("trades")
    LunoTrades trades(@QueryParam("pair") String pair, @QueryParam("since") Long since) throws IOException, LunoException;

}
