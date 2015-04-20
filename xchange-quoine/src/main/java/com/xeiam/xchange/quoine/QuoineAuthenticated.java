package com.xeiam.xchange.quoine;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface QuoineAuthenticated extends Quoine {

  //  @POST
  //  @Path("private/Balance")
  //  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  //  public KrakenBalanceResult balance(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Sign") ParamsDigest signer,
  //      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

}
