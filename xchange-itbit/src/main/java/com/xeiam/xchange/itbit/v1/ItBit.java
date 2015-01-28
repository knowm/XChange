package com.xeiam.xchange.itbit.v1;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTrade;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface ItBit {

  @GET
  @Path("/v2/markets/{ident}{currency}/orders")
  ItBitDepth getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("/v2/markets/{ident}{currency}/trades")
  ItBitTrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @QueryParam("since") long sinceId)
      throws IOException;
}
