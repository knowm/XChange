package com.knowm.xchange.serum;

import com.fasterxml.jackson.databind.JsonNode;
import com.knowm.xchange.serum.dto.SolanaResponse;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Serum {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  SolanaResponse getAccountInfo(final JsonNode body) throws IOException;
}
