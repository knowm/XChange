package com.xeiam.xchange.bitcoinde;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;

/**
 * @author matthewdowney
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {

  @GET
  @Path("{key}/rate.json")
  public BitcoindeRate getRate(@PathParam("key") String key) throws IOException;

  @GET
  @Path("{key}/orderbook.json")
  public BitcoindeOrderBook getOrderBook(@PathParam("key") String key) throws IOException;

  @GET
  @Path("{key}/trades.json")
  public BitcoindeTrade[] getTrades(@PathParam("key") String key) throws IOException;
}
