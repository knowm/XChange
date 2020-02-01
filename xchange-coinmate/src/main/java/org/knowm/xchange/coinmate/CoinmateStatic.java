package org.knowm.xchange.coinmate;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

/** @author Martin Stachon */
@Path("static/xchange")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinmateStatic {

  @GET
  @Path("coinmate.json")
  ExchangeMetaData getMetadata() throws IOException;
}
