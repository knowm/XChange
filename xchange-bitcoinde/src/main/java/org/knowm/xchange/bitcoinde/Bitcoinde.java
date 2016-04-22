package org.knowm.xchange.bitcoinde;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;

/**
 * @author matthewdowney
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {

  @GET
  @Path("rate.json")
  public BitcoindeRate getRate() throws IOException;

  @GET
  @Path("orderbook.json")
  public BitcoindeOrderBook getOrderBook() throws IOException;

  @GET
  @Path("trades.json")
  public BitcoindeTrade[] getTrades() throws IOException;
}
