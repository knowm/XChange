package org.knowm.xchange.bittrex;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bittrex.dto.account.BittrexAccountVolume;
import si.mazi.rescu.ParamsDigest;

@Path("v3")
@Produces(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticatedV3 {

  @GET
  @Path("account/volume")
  BittrexAccountVolume getAccountVolume(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException;
}
