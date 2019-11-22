package org.knowm.xchange.bitfinex.v2;

import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexEmptyRequest;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexTradeResponse;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BitfinexAuthenticated extends Bitfinex {

    @POST
    @Path("/auth/r/trades/{symbol}/hist")
    BitfinexTradeResponse[] pastTrades(
            @HeaderParam("bfx-nonce") String nonce,
            @HeaderParam("bfx-apikey") String apiKey,
            @HeaderParam("bfx-signature") ParamsDigest signature,
            @PathParam("symbol") String symbol,
            @QueryParam("start") Long start,
            @QueryParam("end") Long end,
            @QueryParam("limit") Integer limit,
            @QueryParam("sort") Integer sort,
            BitfinexEmptyRequest emptyRequest)
            throws IOException, BitfinexException;

    @POST
    @Path("/auth/r/trades/hist")
    BitfinexTradeResponse[] pastTrades(
            @HeaderParam("bfx-nonce") String nonce,
            @HeaderParam("bfx-apikey") String apiKey,
            @HeaderParam("bfx-signature") ParamsDigest signature,
            @QueryParam("start") Long start,
            @QueryParam("end") Long end,
            @QueryParam("limit") Integer limit,
            @QueryParam("sort") Integer sort,
            BitfinexEmptyRequest emptyRequest)
            throws IOException, BitfinexException;

}
