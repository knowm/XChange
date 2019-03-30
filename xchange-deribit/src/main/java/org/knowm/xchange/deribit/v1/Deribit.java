package org.knowm.xchange.deribit.v1;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.deribit.v1.dto.marketdata.response.DeribitInstrumentResponse;

@Path("/api/v1/public")
@Produces(MediaType.APPLICATION_JSON)
public interface Deribit {

  @GET
  @Path("getinstruments")
  DeribitInstrumentResponse getInstruments() throws IOException;
}
