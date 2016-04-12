package org.knowm.xchange.mexbt;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.mexbt.dto.marketdata.MeXBTOrderBook;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTicker;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface MeXBT {

  @GET
  @Path("order-book/{currencyPair}")
  MeXBTOrderBook getOrderBook(@PathParam("currencyPair") String currencyPair) throws IOException;

  @GET
  @Path("trades/{currencyPair}")
  MeXBTTrade[] getTrades(@PathParam("currencyPair") String currencyPair, @QueryParam("since") Long since) throws IOException;

  @GET
  @Path("ticker/{currencyPair}")
  MeXBTTicker getTicker(@PathParam("currencyPair") String currencyPair) throws IOException;

}
