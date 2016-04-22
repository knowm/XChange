package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccountList;

/**
 * RESTful/JSON API: Customer Account.
 */
@Path("/customer/account")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinsetterAccount {

  @GET
  @Path("{accountUuid}")
  org.knowm.xchange.coinsetter.dto.account.CoinsetterAccount get(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId,
      @PathParam("accountUuid") UUID accountUuid) throws CoinsetterException, IOException;

  @GET
  CoinsetterAccountList list(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId) throws CoinsetterException, IOException;

}
