package org.knowm.xchange.bl3p;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bl3p.dto.marketdata.Bl3pOrderBook;
import org.knowm.xchange.bl3p.dto.marketdata.Bl3pTicker;
import org.knowm.xchange.bl3p.dto.marketdata.Bl3pTrades;

@Path("1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bl3p {

  @GET
  @Path("/{currencyPair}/ticker")
  Bl3pTicker getTicker(@PathParam("currencyPair") String currency) throws IOException;

  @GET
  @Path("/{currencyPair}/orderbook")
  Bl3pOrderBook getOrderBook(@PathParam("currencyPair") String currency) throws IOException;

  @GET
  @Path("/{currencyPair}/trades")
  Bl3pTrades getTrades(@PathParam("currencyPair") String currency) throws IOException;
}
