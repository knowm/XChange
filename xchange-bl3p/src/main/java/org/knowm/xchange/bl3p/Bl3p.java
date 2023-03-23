package org.knowm.xchange.bl3p;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
