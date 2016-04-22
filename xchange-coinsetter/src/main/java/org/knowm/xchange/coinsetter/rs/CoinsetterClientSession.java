package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.CoinsetterResponse;
import org.knowm.xchange.coinsetter.dto.clientsession.request.CoinsetterLoginRequest;

/**
 * RESTful/JSON API: ClientSession.
 */
@Path("/clientSession")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinsetterClientSession {

  @POST
  public org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession login(CoinsetterLoginRequest request)
      throws CoinsetterException, IOException;

  @PUT
  @Path("{client-session-id}")
  public CoinsetterResponse action(@HeaderParam("coinsetter-client-session-id") @PathParam("client-session-id") UUID clientSessionId,
      @QueryParam("action") String action) throws CoinsetterException, IOException;

}
