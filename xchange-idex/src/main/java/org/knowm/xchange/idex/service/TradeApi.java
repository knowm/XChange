package org.knowm.xchange.idex.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.dto.TradeReq;
import org.knowm.xchange.idex.dto.TradeResponse;

@Path("/trade")
@Api(description = "the trade API")
@Consumes("application/json")
@Produces("application/json")
public interface TradeApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  TradeResponse trade(TradeReq tradeReq);
}
