package org.knowm.xchange.krakenfutures;

import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesInstruments;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesOrderBook;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesPublicFills;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesTickers;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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