package org.xchange.coinegg;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xchange.coinegg.dto.accounts.CoinEggBalance;

import si.mazi.rescu.ParamsDigest;

@Path("api/v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CoinEggAuthenticated {

  @POST
  @Path("balance")
  CoinEggBalance getBalance(@FormParam("key") String apiKey, @FormParam("nonce") long nonce, @FormParam("signature") ParamsDigest signer) throws IOException;
  
  
}