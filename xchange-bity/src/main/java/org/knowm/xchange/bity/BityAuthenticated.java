package org.knowm.xchange.bity;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.BityResponse;
import org.knowm.xchange.bity.dto.account.BityOrder;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BityAuthenticated extends Bity {

  @GET
  @Path("/api/v1/order")
  BityResponse<BityOrder> getOrders(
      @QueryParam("offset") Long offset,
      @QueryParam("limit") Integer limit,
      @QueryParam("order_by") String orderBy,
      @HeaderParam("Authorization") String authorization)
      throws BityException;
}
