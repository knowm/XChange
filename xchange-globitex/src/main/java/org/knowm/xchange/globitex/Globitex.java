package org.knowm.xchange.globitex;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexOrderBook;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexSymbols;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexTicker;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexTickers;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexTrades;

@Path("/api/1/")
@Produces(MediaType.APPLICATION_JSON)
public interface Globitex {

  @GET
  @Path("public/symbols")
  GlobitexSymbols getSymbols() throws IOException;

  @GET
  @Path("public/ticker/{symbol}")
  GlobitexTicker getTickerBySymbol(@PathParam("symbol") String globitexSymbol) throws IOException;

  @GET
  @Path("public/ticker")
  GlobitexTickers getTickers() throws IOException;

  @GET
  @Path("public/orderbook/{symbol}")
  GlobitexOrderBook getOrderBookBySymbol(@PathParam("symbol") String globitexSymbol)
      throws IOException;

  @GET
  @Path("public/trades/recent/{symbol}")
  GlobitexTrades getRecentTradesBySymbol(
      @PathParam("symbol") String globitexSymbol,
      @QueryParam("maxResults") int maxResults,
      @QueryParam("formatItem") String formatItem,
      @QueryParam("side") boolean side)
      throws IOException;
}
