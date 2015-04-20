package com.xeiam.xchange.quoine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.quoine.dto.marketdata.QuoineTicker;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Quoine {

  @GET
  @Path("products/code/CASH/{currency_pair_code}")
  QuoineTicker getTicker(@PathParam("currency_pair_code") String currencyPairCode);

}
