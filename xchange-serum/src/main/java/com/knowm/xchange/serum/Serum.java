package com.knowm.xchange.serum;

import com.fasterxml.jackson.databind.JsonNode;
import com.knowm.xchange.serum.dto.SolanaResponse;
import java.io.IOException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Serum {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  SolanaResponse getAccountInfo(final JsonNode body) throws IOException;
}
