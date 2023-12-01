package org.knowm.xchange.koinim;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.koinim.dto.marketdata.KoinimTicker;

/**
 * @author ahmetoz
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Koinim {

  @GET
  @Path("ticker")
  KoinimTicker getTicker() throws IOException;
}
