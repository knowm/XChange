package org.knowm.xchange.bitcoinaverage;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;

/** @author veken0m */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitcoinAverage {

  @GET
  @Path("indices/global/ticker/{symbol}")
  BitcoinAverageTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("indices/global/ticker/short?crypto={crypto}")
  BitcoinAverageTickers getShortTickers(@PathParam("crypto") String crypto) throws IOException;
}
