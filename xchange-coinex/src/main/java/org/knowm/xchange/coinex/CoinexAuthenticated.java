package org.knowm.xchange.coinex;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinex.dto.account.CoinexBalances;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinexAuthenticated {

  String HEADER_AUTHORIZATION = "authorization";
  String HEADER_USER_AGENT = "User-Agent";

  @GET
  @Path("balance/info")
  CoinexBalances getWallet(
      //            @HeaderParam(HEADER_USER_AGENT) String user_agent_info,
      @HeaderParam(HEADER_AUTHORIZATION) ParamsDigest sign,
      @QueryParam("access_id") String access_id,
      @QueryParam("tonce") SynchronizedValueFactory<Long> tonce)
      throws IOException;
}
