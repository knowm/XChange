package org.knowm.xchange.vircurex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.vircurex.dto.marketdata.VircurexDepth;
import org.knowm.xchange.vircurex.dto.marketdata.VircurexLastTrade;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Vircurex {

  @GET
  @Path("orderbook.json")
  VircurexDepth getFullDepth(
      @QueryParam("base") String tradeableIdentifier, @QueryParam("alt") String currency);

  @GET
  @Path("get_last_trade.json")
  VircurexLastTrade getLastTrade(
      @QueryParam("base") String tradeableIdentifier, @QueryParam("alt") String currency);
}
