package org.knowm.xchange.mexc;

import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Map;

@Path("/open/api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface MEXCAuthenticated {

  @GET
  @Path("/account/info")
  MEXCResult<Map<String, MEXCBalance>> getWalletBalances(
          @HeaderParam("ApiKey") String apiKey,
          @HeaderParam("Request-Time") SynchronizedValueFactory<Long> timestamp,
          @HeaderParam("Signature") ParamsDigest signature
  ) throws IOException;

  MEXCResult<String> placeOrder(String apiKey,
                                String symbol,
                                long price,
                                long qty,
                                String side,
                                String type,
                                SynchronizedValueFactory<Long> nonceFactory,
                                ParamsDigest signature);
}
