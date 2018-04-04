package org.knowm.xchange.wex.v3;

import java.io.IOException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepthWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexExchangeInfo;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTickerWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTradesWrapper;

/** @author timmolter */
@Path("/")
public interface Wex {

  @GET
  @Path("api/3/info")
  WexExchangeInfo getInfo() throws IOException;

  @GET
  @Path("api/3/ticker/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  WexTickerWrapper getTicker(
      @PathParam("pairs") String pairs,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid)
      throws IOException;

  @GET
  @Path("api/3/depth/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  WexDepthWrapper getDepth(
      @PathParam("pairs") String pairs,
      @DefaultValue("150") @QueryParam("limit") int limit,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid)
      throws IOException;

  @GET
  @Path("api/3/trades/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  WexTradesWrapper getTrades(
      @PathParam("pairs") String pairs,
      @DefaultValue("1") @QueryParam("limit") int limit,
      @DefaultValue("1") @QueryParam("ignore_invalid") int ignoreInvalid)
      throws IOException;
}
