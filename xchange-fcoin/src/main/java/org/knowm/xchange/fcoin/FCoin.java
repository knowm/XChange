package org.knowm.xchange.fcoin;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.fcoin.dto.trade.FCoinOrder;
import org.knowm.xchange.fcoin.dto.trade.FCoinOrderResult;
import si.mazi.rescu.ParamsDigest;

@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface FCoin {

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  FCoinOrderResult placeOrder(
      @HeaderParam("FC-ACCESS-KEY") String apiKey,
      @HeaderParam("FC-ACCESS-TIMESTAMP") Long timestamp,
      @HeaderParam("FC-ACCESS-SIGNATURE") ParamsDigest paramsDigest,
      FCoinOrder order);
}
