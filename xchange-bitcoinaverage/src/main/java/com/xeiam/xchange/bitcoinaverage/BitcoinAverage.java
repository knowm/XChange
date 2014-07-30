package com.xeiam.xchange.bitcoinaverage;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;

/**
 * @author veken0m
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitcoinAverage {

  @GET
  @Path("ticker/global/{currency}/")
  public BitcoinAverageTicker getTicker(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("ticker/global/all")
  public BitcoinAverageTickers getAllTickers() throws IOException;

}
