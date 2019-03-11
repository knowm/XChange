package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCMarkets {

  @GET
  @Path("/market/{instrument}/{currency}/tick")
  BTCMarketsTicker getTicker(
      @PathParam("instrument") String instrument, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("/market/{instrument}/{currency}/orderbook")
  BTCMarketsOrderBook getOrderBook(
      @PathParam("instrument") String instrument, @PathParam("currency") String currency)
      throws IOException;
}
