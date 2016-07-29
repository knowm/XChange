package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;

/**
 * RESTful/JSON API: Ping.
 */
@Path("/ping")
@Produces(MediaType.TEXT_PLAIN)
public interface CoinsetterPing {

  @GET
  @Path("{text}")
  public String ping(@PathParam("text") String text) throws CoinsetterException, IOException;

}
