package org.knowm.xchange.koinim;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.koinim.dto.marketdata.KoinimTicker;

/** @author ahmetoz */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Koinim {

  @GET
  @Path("ticker")
  KoinimTicker getTicker() throws IOException;
}
