package org.knowm.xchange.upbit;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.upbit.dto.UpbitException;
import org.knowm.xchange.upbit.dto.account.UpbitBalances;
import si.mazi.rescu.ParamsDigest;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UpbitAuthenticated extends Upbit {
  @GET
  @Path("accounts")
  UpbitBalances getWallet(@HeaderParam("Authorization") ParamsDigest signatureCreator)
      throws IOException, UpbitException;
}
