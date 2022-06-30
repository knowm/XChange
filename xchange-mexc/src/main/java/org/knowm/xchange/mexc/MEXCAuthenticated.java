package org.knowm.xchange.mexc;

import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.account.MEXCBalances;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/open/api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface MEXCAuthenticated {

  @GET
  @Path("/account/info")
  MEXCResult<MEXCBalances> getWalletBalances(
          @QueryParam("api_key") String apiKey,
          @QueryParam("req_time") SynchronizedValueFactory<Long> timestamp,
          @QueryParam("sign") ParamsDigest signature
  ) throws IOException;

}
