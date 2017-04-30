package org.known.xchange.dsx;

import org.known.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.known.xchange.dsx.dto.marketdata.DSXPairInfo;
import org.known.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.known.xchange.dsx.dto.marketdata.DSXTradesWrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author Mikhail Wall
 */
@Path("/")
public interface DSX {

  @GET
  @Path("mapi/info")
  DSXPairInfo getInfo() throws IOException;

  @GET
  @Path("mapi/depth/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXOrderbookWrapper getOrderbook(@PathParam("pairs") String pairs,
                                   @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

  @GET
  @Path("mapi/ticker/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXTickerWrapper getTicker(@PathParam("pairs") String pairs,
                             @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

  @GET
  @Path("mapi/trades/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  DSXTradesWrapper getTrades(@PathParam("pairs") String pairs, @DefaultValue("1") @QueryParam("limit") int limit,
                             @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid) throws IOException;

}
