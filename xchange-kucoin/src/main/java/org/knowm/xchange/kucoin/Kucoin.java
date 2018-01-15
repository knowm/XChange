package org.knowm.xchange.kucoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.kucoin.dto.marketdata.KucoinTickResponse;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Kucoin {

  @GET
  @Path("open/tick")
  KucoinTickResponse getTicker(@QueryParam("symbol") String market) throws IOException;

}
