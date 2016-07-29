package org.knowm.xchange.virtex.v2;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExDepthWrapper;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTickerWrapper;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTradesWrapper;

/**
 * @author veken0m
 */
@Path("api2")
@Produces(MediaType.APPLICATION_JSON)
public interface VirtEx {

  @GET
  @Path("ticker.json?currencypair={ident}{currency}")
  public VirtExTickerWrapper getTicker(@PathParam("ident") String tradableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("orderbook.json?currencypair={ident}{currency}")
  public VirtExDepthWrapper getFullDepth(@PathParam("ident") String tradableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("trades.json?currencypair={ident}{currency}")
  public VirtExTradesWrapper getTrades(@PathParam("ident") String tradableIdentifier, @PathParam("currency") String currency) throws IOException;

}
