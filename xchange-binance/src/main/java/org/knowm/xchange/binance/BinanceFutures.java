package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceFundingRate;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.dto.meta.BinanceSystemStatus;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceFutures {

    /**
     * Current exchange trading rules and symbol information.
     *
     * @return
     * @throws IOException
     */
    @GET
    @Path("/fapi/v1/exchangeInfo")
    BinanceExchangeInfo exchangeInfo() throws IOException;

    /**
     * Fetch system status which is normal or system maintenance.
     *
     * @throws IOException
     */
    @GET
    @Path("sapi/v1/system/status")
    BinanceSystemStatus systemStatus() throws IOException;

    /**
     * @param symbol
     * @param limit optional, default 100 max 5000. Valid limits: [5, 10, 20, 50, 100, 500, 1000,
     *     5000]
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/depth")
    BinanceOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
            throws IOException, BinanceException;

    /**
     * 24 hour price change statistics.
     *
     * @param symbol
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/ticker/24hr")
    BinanceTicker24h ticker24h(@QueryParam("symbol") String symbol)
            throws IOException, BinanceException;

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
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/aggTrades")
    List<BinanceAggTrades> aggTrades(
            @QueryParam("symbol") String symbol,
            @QueryParam("fromId") Long fromId,
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("limit") Integer limit)
            throws IOException, BinanceException;

    /**
     *
     * @return List<BinanceFundingRate>
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/premiumIndex")
    List<BinanceFundingRate> fundingRates()
            throws IOException, BinanceException;

    /**
     * @param symbol the instrument to get the funding rate for
     *
     * @return BinanceFundingRate
     * @throws IOException
     * @throws BinanceException
     */
    @GET
    @Path("fapi/v1/premiumIndex")
    BinanceFundingRate fundingRate(
            @QueryParam("symbol") String symbol)
            throws IOException, BinanceException;
}
