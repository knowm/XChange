package org.knowm.xchange.coinegg;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinegg.dto.marketdata.CoinEggOrders;
import org.knowm.xchange.coinegg.dto.marketdata.CoinEggTicker;
import org.knowm.xchange.coinegg.dto.marketdata.CoinEggTrade;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinEgg {

  @GET
  @Path("ticker?coin={symbol}")
  CoinEggTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("orders?coin={symbol}")
  CoinEggTrade[] getTrades(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("depth/region/{region}?coin={symbol}")
  CoinEggOrders getOrders(@PathParam("region") String region, @PathParam("symbol") String symbol)
      throws IOException;
}
