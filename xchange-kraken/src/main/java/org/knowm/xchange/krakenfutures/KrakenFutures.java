package org.knowm.xchange.krakenfutures;

import org.knowm.xchange.kraken.dto.marketdata.results.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/** @author Benedikt Bünz */
@Path("/api/v3/")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenFutures {

  @GET
  @Path("tickers")
  KrakenTickerResult getTicker(@QueryParam("pair") String currencyPairs) throws IOException;

  @GET
  @Path("orderbook")
  KrakenOrderBookResult getOrderbook(@QueryParam("symbol") String currencyPair) throws IOException;

}
