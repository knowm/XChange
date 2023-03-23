package org.knowm.xchange.coindeal;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coindeal.dto.CoindealException;
import org.knowm.xchange.coindeal.dto.marketdata.CoindealOrderBook;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Coindeal {

  @GET
  @Path("/public/orderbook/{currencyPair}")
  CoindealOrderBook getOrderBook(@PathParam("currencyPair") String currencyPair)
      throws IOException, CoindealException;
}
