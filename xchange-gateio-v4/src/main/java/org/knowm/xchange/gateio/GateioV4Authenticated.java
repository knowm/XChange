package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v4")
public interface GateioV4Authenticated {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("wallet/withdraw_status")
  List<GateioWithdrawStatus> getWithdrawStatus(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

}
