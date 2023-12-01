package org.knowm.xchange.paribu;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.paribu.dto.marketdata.ParibuTicker;

/**
 * @author semihunaldi
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Paribu {

  @GET
  @Path("ticker")
  ParibuTicker getTicker() throws IOException;
}
