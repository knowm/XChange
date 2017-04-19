package org.knowm.xchange.gemini.v1;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
  GeminiDepth getBook(@PathParam("symbol") String symbol, @QueryParam("limit_bids") int limit_bids,
      @QueryParam("limit_asks") int limit_asks) throws IOException, GeminiException;

  @GET
  @Path("book/{symbol}")
  GeminiDepth getBook(@PathParam("symbol") String symbol) throws IOException, GeminiException;

  @GET
  @Path("lendbook/{currency}")
  GeminiLendDepth getLendBook(@PathParam("currency") String currency, @QueryParam("limit_bids") int limit_bids,
      @QueryParam("limit_asks") int limit_asks) throws IOException, GeminiException;

  @GET
  @Path("trades/{symbol}")
  GeminiTrade[] getTrades(@PathParam("symbol") String symbol, @QueryParam("timestamp") long timestamp) throws IOException, GeminiException;

  @GET
  @Path("lends/{currency}")
  GeminiLend[] getLends(@PathParam("currency") String currency, @QueryParam("timestamp") long timestamp,
      @QueryParam("limit_trades") int limit_trades) throws IOException, GeminiException;

  @GET
  @Path("symbols")
  Set<String> getSymbols() throws IOException, GeminiException;

}
