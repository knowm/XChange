package org.knowm.xchange.bitcoinaverage;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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