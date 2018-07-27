package org.knowm.xchange.bitfinex.v2;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitfinex.common.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicFundingTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitfinex {

  @GET
  @Path("tickers?symbols={symbols}")
  BitfinexTicker[] getTickers(@PathParam("symbols") String symbols)
      throws IOException, BitfinexException;

  @GET
  @Path("/trades/{symbol}/hist")
  BitfinexPublicFundingTrade[] getPublicFundingTrades(@PathParam("symbol") String fundingSymbol, @QueryParam("limit") int limit, @QueryParam("start") long startTimestamp, @QueryParam("end") long endTimestamp, @QueryParam("sort") int sort )
      throws IOException, BitfinexException;

  @GET
  @Path("/trades/{symbol}/hist")
  BitfinexPublicTrade[] getPublicTrades(@PathParam("symbol") String fundingSymbol, @QueryParam("limit") int limit, @QueryParam("start") long startTimestamp, @QueryParam("end") long endTimestamp, @QueryParam("sort") int sort )
      throws IOException, BitfinexException;
}
