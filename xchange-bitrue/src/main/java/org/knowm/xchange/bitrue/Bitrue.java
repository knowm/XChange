package org.knowm.xchange.bitrue;


import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.bitrue.dto.marketdata.*;
import org.knowm.xchange.bitrue.dto.meta.BitrueTime;
import org.knowm.xchange.bitrue.dto.meta.exchangeinfo.BitrueExchangeInfo;
import org.knowm.xchange.bitrue.dto.trade.BitrueTrade;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitrue {
    /**
     * Test connectivity to the Rest API.
     *
     * @return
     * @throws IOException
     */
    @GET
    @Path("api/v1/ping")
    Object ping() throws IOException;

    /**
     * Test connectivity to the Rest API and get the current server time.
     *
     * @return
     * @throws IOException
     */
    @GET
    @Path("api/v1/time")
    BitrueTime time() throws IOException;

    /**
     * Current exchange trading rules and symbol information.
     *
     * @return
     * @throws IOException
     */
    @GET
    @Path("api/v1/exchangeInfo")
    BitrueExchangeInfo exchangeInfo() throws IOException;

    /**
     * @param symbol
     * @param limit optional, default 100 max 5000. Valid limits: [5, 10, 20, 50, 100, 500, 1000,
     *     5000]
     * @return
     * @throws IOException
     * @throws BitrueException
     */
    @GET
    @Path("api/v1/depth")
    BitrueOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
            throws IOException, BitrueException;

    /**
     * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with the
     * same price will have the quantity aggregated.<br>
     * If both startTime and endTime are sent, limit should not be sent AND the distance between
     * startTime and endTime must be less than 24 hours.<br>
     * If frondId, startTime, and endTime are not sent, the most recent aggregate trades will be
     * returned.
     *
     * @param symbol
     * @param fromId optional, ID to get aggregate trades from INCLUSIVE.
     * @param startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
     * @param endTime optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
     * @param limit optional, Default 500; max 500.
     * @return
     * @throws IOException
     * @throws BitrueException
     */
    @GET
    @Path("api/v1/aggTrades")
    List<BitrueAggTrades> aggTrades(
            @QueryParam("symbol") String symbol,
            @QueryParam("fromId") Long fromId,
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("limit") Integer limit)
            throws IOException, BitrueException;

    @GET
    @Path("api/v1/historicalTrades")
    List<BitrueTrade> historicalTrades(
            @QueryParam("symbol") String symbol,
            @QueryParam("limit") Integer limit,
            @QueryParam("fromId") Long fromId
    ) throws IOException, BitrueException;

    /**
     * 24 hour price change statistics.
     *
     * @param symbol
     * @return
     * @throws IOException
     * @throws BitrueException
     */
    @GET
    @Path("api/v1/ticker/24hr")
    List<BitrueTicker24h> ticker24h(@QueryParam("symbol") String symbol)
            throws IOException, BitrueException;

    /**
     * Latest price for a symbol.
     *
     * @return
     * @throws IOException
     * @throws BitrueException
     */
    @GET
    @Path("api/v1/ticker/price")
    BitruePrice tickerPrice(@QueryParam("symbol") String symbol)
            throws IOException, BitrueException;


    /**
     * Best price/qty on the order book for a symbols.
     *
     * @return
     * @throws IOException
     * @throws BitrueException
     */
    @GET
    @Path("api/v1/ticker/bookTicker")
    List<BitruePriceQuantity> tickerBookTicker(@QueryParam("symbol") String symbol) throws IOException, BitrueException;
}
