package com.xeiam.xchange.virtex.v1;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;

/**
 * @author timmolter
 */
@Deprecated
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface VirtEx {

  @GET
  @Path("{currency}/ticker.json")
  public VirtExTicker getTicker(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("{currency}/orderbook.json")
  public VirtExDepth getFullDepth(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("{currency}/trades.json")
  public VirtExTrade[] getTrades(@PathParam("currency") String currency) throws IOException;

}
