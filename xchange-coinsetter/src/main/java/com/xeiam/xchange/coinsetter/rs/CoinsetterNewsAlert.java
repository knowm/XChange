package com.xeiam.xchange.coinsetter.rs;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.newsalert.response.CoinsetterNewsAlertList;

/**
 * RESTful/JSON API: News Alert.
 */
@Path("/newsalert")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinsetterNewsAlert {

  @GET
  CoinsetterNewsAlertList list(@HeaderParam("coinsetter-client-session-id") UUID clientSessionId) throws CoinsetterException, IOException;

}
