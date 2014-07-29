package com.xeiam.xchange.vircurex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.vircurex.dto.marketdata.VircurexDepth;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Vircurex {

  @GET
  @Path("orderbook.json")
  VircurexDepth getFullDepth(@QueryParam("base") String tradeableIdentifier, @QueryParam("alt") String currency);

}
