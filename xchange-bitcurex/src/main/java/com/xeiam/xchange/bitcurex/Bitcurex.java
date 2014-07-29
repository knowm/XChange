package com.xeiam.xchange.bitcurex;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;

@Path("data")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcurex {

  @GET
  @Path("ticker.json")
  public BitcurexTicker getTicker() throws IOException;

  @GET
  @Path("orderbook.json")
  public BitcurexDepth getFullDepth() throws IOException;

  @GET
  @Path("trades.json")
  public BitcurexTrade[] getTrades() throws IOException;

}
