package org.knowm.xchange.krakenfutures;

import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesInstruments;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesOrderBook;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesPublicFills;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesTickers;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/** @author Jean-Christophe Laruelle */
@Path("/api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenFutures {

  @GET
  @Path("/tickers")
  KrakenFuturesTickers getTickers() throws IOException;

  @GET
  @Path("/orderbook")
  KrakenFuturesOrderBook getOrderBook(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("/instruments")
  KrakenFuturesInstruments getInstruments() throws IOException;

  @GET
  @Path("/history")
  KrakenFuturesPublicFills getHistory(@QueryParam("symbol") String symbol) throws IOException;
}
