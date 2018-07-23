package org.knowm.xchange.bity;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.account.BityOrder;
import org.knowm.xchange.bity.dto.BityResponse;

@Produces(MediaType.APPLICATION_JSON)
public interface BityAuthenticated extends Bity {

/*
  var options = {
      url: 'https://bity.com/api/v1/order/?limit=50&offset=0&order_by=-timestamp_created',
  headers: {
    'Authorization': 'Bearer ' + json.access_token
  }
};
*/

  @GET
  @Path("/api/v1/order")
  BityResponse<BityOrder> getOrders(
      @QueryParam("offset") Long offset,
      @QueryParam("limit") Integer limit,
      @QueryParam("order_by") String orderBy,
      @HeaderParam("Authorization") String authorization)
      throws BityException;
}
