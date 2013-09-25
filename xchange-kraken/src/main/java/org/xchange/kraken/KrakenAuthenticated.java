package org.xchange.kraken;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.xchange.kraken.dto.account.KrakenBalanceResult;

import si.mazi.rescu.ParamsDigest;

@Path("0/private")
@Produces("application/json")
public interface KrakenAuthenticated {
  @POST
  @Path("Balance")
  public KrakenBalanceResult getBalance(@HeaderParam("API-Key") String apiKey,@HeaderParam("API-Sign") ParamsDigest signer,@FormParam("nonce") long nonce);
}
