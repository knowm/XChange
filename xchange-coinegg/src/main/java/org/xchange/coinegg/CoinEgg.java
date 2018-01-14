package org.xchange.coinegg;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xchange.coinegg.dto.marketdata.CoinEggOrder;
import org.xchange.coinegg.dto.marketdata.CoinEggTicker;
import org.xchange.coinegg.dto.marketdata.CoinEggTrades;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinEgg {

  @GET
  @Path("ticker?coin={symbol}")
  CoinEggTicker getTicker(@PathParam("symbol") String symbol) throws IOException;
  
  @GET
  @Path("depth?coin={symbol}")
  CoinEggTrades getTrades(@PathParam("symbol") String symbol) throws IOException;
  
  @GET
  @Path("orders?coin={symbol}")
  CoinEggOrder[] getOrders(@PathParam("symbol") String symbol) throws IOException;
  
}