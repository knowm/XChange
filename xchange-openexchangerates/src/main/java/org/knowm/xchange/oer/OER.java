package org.knowm.xchange.oer;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.oer.dto.marketdata.OERTickers;

/** @author timmolter */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface OER {

  @GET
  @Path("latest.json")
  OERTickers getTickers(
      @QueryParam("app_id") String appId,
      @QueryParam("base") String base,
      @QueryParam("symbols") String symbols)
      throws IOException;
}