package org.knowm.xchange.coinjar;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import org.knowm.xchange.coinjar.dto.data.CoinjarOrderBook;
import org.knowm.xchange.coinjar.dto.data.CoinjarTicker;

@Produces({"application/json"})
@Path("/")
public interface CoinjarData {

  @GET
  @Path("/products/{product}/ticker")
  CoinjarTicker getTicker(@PathParam("product") String product)
      throws CoinjarException, IOException;

  /** Level 1: Only the best bid and ask Level 2: Top 20 bids/asks Level 3: Full order book */
  @GET
  @Path("/products/{product}/book")
  CoinjarOrderBook getOrderBook(
      @PathParam("product") String product, @QueryParam("level") int level)
      throws CoinjarException, IOException;
  ;
}
