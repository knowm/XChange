package org.knowm.xchange.bity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.BityResponse;
import org.knowm.xchange.bity.dto.account.BityToken;
import org.knowm.xchange.bity.dto.marketdata.BityPair;
import org.knowm.xchange.bity.dto.marketdata.BityTicker;

@Produces(MediaType.APPLICATION_JSON)
public interface Bity {

  @GET
  @Path("/api/v1/rate2")
  BityResponse<BityTicker> getRates() throws BityException;

  @GET
  @Path("/api/v2/pairs/")
  BityResponse<BityPair> getPairs()  throws BityException;

  @Path("/o/token")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BityToken createToken(
      @FormParam("client_id") String clientId,
      @FormParam("grant_type") String grantType,
      @FormParam("username") String username,
      @FormParam("password") String password) throws BityException;
}
