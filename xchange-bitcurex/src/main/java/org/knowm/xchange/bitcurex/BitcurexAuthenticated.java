package org.knowm.xchange.bitcurex;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexFunds;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/0")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface BitcurexAuthenticated {

  @POST
  @Path("getFunds")
  BitcurexFunds getFunds(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
}
