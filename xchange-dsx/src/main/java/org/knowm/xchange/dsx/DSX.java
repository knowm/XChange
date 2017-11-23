package org.knowm.xchange.dsx;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.dsx.dto.marketdata.DSXBarsWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTradesWrapper;

/**
 * @author Mikhail Wall
 */
@Path("/")
public interface DSX {

  @GET
  @Path("mapi/info")
  DSXExchangeInfo getInfo() throws IOException;

  @GET
  @Path("mapi/depth/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXOrderbookWrapper getOrderbook(@PathParam("pairs") String pairs,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid, @DefaultValue("LIVE") @QueryParam("mode") String mode)
      throws IOException;

  @GET
  @Path("mapi/ticker/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXTickerWrapper getTicker(@PathParam("pairs") String pairs,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid, @DefaultValue("LIVE") @QueryParam("mode") String mode)
      throws IOException;

  @GET
  @Path("mapi/trades/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXTradesWrapper getTrades(@PathParam("pairs") String pairs, @DefaultValue("150") @QueryParam("limit") int limit,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid, @DefaultValue("LIVE") @QueryParam("mode") String mode)
      throws IOException;

  @GET
  @Path("mapi/lastBars/{pairs}/{period}/{amount}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXBarsWrapper getBars(@PathParam("pairs") String pairs, @PathParam("period") String period, @PathParam("amount") String amount,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid, @DefaultValue("LIVE") @QueryParam("mode") String mode)
      throws IOException;

}
