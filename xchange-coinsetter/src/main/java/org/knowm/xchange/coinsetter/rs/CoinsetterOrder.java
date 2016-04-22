package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.order.request.CoinsetterOrderRequest;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderList;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderResponse;

/**
 * RESTful/JSON API: Order.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinsetterOrder {

  @POST
  @Path("order")
  CoinsetterOrderResponse add(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId, CoinsetterOrderRequest request)
      throws CoinsetterException, IOException;

  @GET
  @Path("order/{orderUuid}")
  org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrder get(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId,
      @PathParam("orderUuid") UUID orderUuid) throws CoinsetterException, IOException;

  @GET
  @Path("customer/account/{accountUuid}/order")
  CoinsetterOrderList list(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId, @PathParam("accountUuid") UUID accountUuid,
      @QueryParam("view") String view) throws CoinsetterException, IOException;

  @DELETE
  @Path("order/{orderUuid}")
  CoinsetterOrderResponse cancel(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId, @PathParam("orderUuid") UUID orderUuid)
      throws CoinsetterException, IOException;

}
