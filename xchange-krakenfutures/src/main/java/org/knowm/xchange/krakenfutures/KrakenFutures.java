package org.knowm.xchange.krakenfutures;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.krakenfutures.dto.marketdata.KrakenFuturesInstruments;
import org.knowm.xchange.krakenfutures.dto.marketdata.KrakenFuturesOrderBook;
import org.knowm.xchange.krakenfutures.dto.marketdata.KrakenFuturesPublicFills;
import org.knowm.xchange.krakenfutures.dto.marketdata.KrakenFuturesTickers;

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
