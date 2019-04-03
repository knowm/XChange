package org.knowm.xchange.coindeal;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
