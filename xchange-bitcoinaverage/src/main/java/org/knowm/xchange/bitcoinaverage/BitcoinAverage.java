package org.knowm.xchange.bitcoinaverage;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;

/**
 * @author veken0m
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitcoinAverage {

  @GET
  @Path("ticker/global/{currency}/")
  BitcoinAverageTicker getTicker(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("ticker/global/all")
  BitcoinAverageTickers getAllTickers() throws IOException;

}
