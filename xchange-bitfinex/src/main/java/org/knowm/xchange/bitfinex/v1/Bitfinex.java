package org.knowm.xchange.bitfinex.v1;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitfinex.v1.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLend;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLendDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitfinex {

  @GET
  @Path("pubticker/{symbol}")
  BitfinexTicker getTicker(@PathParam("symbol") String symbol) throws IOException, BitfinexException;

  @GET
  @Path("book/{symbol}")
  BitfinexDepth getBook(@PathParam("symbol") String symbol, @QueryParam("limit_bids") int limit_bids,
      @QueryParam("limit_asks") int limit_asks) throws IOException, BitfinexException;

  @GET
  @Path("book/{symbol}")
  BitfinexDepth getBook(@PathParam("symbol") String symbol) throws IOException, BitfinexException;

  @GET
  @Path("lendbook/{currency}")
  BitfinexLendDepth getLendBook(@PathParam("currency") String currency, @QueryParam("limit_bids") int limit_bids,
      @QueryParam("limit_asks") int limit_asks) throws IOException, BitfinexException;

  @GET
  @Path("trades/{symbol}")
  BitfinexTrade[] getTrades(@PathParam("symbol") String symbol, @QueryParam("timestamp") long timestamp) throws IOException, BitfinexException;

  @GET
  @Path("lends/{currency}")
  BitfinexLend[] getLends(@PathParam("currency") String currency, @QueryParam("timestamp") long timestamp,
      @QueryParam("limit_trades") int limit_trades) throws IOException, BitfinexException;

  @GET
  @Path("symbols")
  Set<String> getSymbols() throws IOException, BitfinexException;

}
