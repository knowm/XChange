package org.knowm.xchange.koineks;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.koineks.dto.marketdata.KoineksTicker;

/** Created by semihunaldi on 05/12/2017 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Koineks {
  @GET
  @Path("ticker")
  KoineksTicker getTicker() throws IOException;
}