package org.knowm.xchange.coinegg;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
