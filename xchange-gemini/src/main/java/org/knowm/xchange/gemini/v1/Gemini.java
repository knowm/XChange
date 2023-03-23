package org.knowm.xchange.gemini.v1;

import java.io.IOException;
import java.util.Set;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLend;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLendDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTicker;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Gemini {

  @GET
  @Path("pubticker/{symbol}")
  GeminiTicker getTicker(@PathParam("symbol") String symbol) throws IOException, GeminiException;

  @GET
  @Path("book/{symbol}")
  GeminiDepth getBook(
      @PathParam("symbol") String symbol,
      @QueryParam("limit_bids") int limit_bids,
      @QueryParam("limit_asks") int limit_asks)
      throws IOException, GeminiException;

  @GET
  @Path("book/{symbol}")
  GeminiDepth getBook(@PathParam("symbol") String symbol) throws IOException, GeminiException;

  @GET
  @Path("lendbook/{currency}")
  GeminiLendDepth getLendBook(
      @PathParam("currency") String currency,
      @QueryParam("limit_bids") int limit_bids,
      @QueryParam("limit_asks") int limit_asks)
      throws IOException, GeminiException;

  @GET
  @Path("trades/{symbol}")
  GeminiTrade[] getTrades(
      @PathParam("symbol") String symbol,
      @QueryParam("since") long timestamp,
      @QueryParam("limit_trades") int limit_trades)
      throws IOException, GeminiException;

  @GET
  @Path("lends/{currency}")
  GeminiLend[] getLends(
      @PathParam("currency") String currency,
      @QueryParam("timestamp") long timestamp,
      @QueryParam("limit_trades") int limit_trades)
      throws IOException, GeminiException;

  @GET
  @Path("symbols")
  Set<String> getSymbols() throws IOException, GeminiException;
}
