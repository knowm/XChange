package org.knowm.xchange.bity;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.BityResponse;
import org.knowm.xchange.bity.dto.account.BityToken;
import org.knowm.xchange.bity.dto.marketdata.BityPairs;
import org.knowm.xchange.bity.dto.marketdata.BityTicker;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bity {

  @GET
  @Path("/api/v1/rate2")
  BityResponse<BityTicker> getRates() throws BityException;

  @GET
  @Path("/api/v2/pairs/")
  BityPairs getPairs() throws BityException;

  @POST
  @Path("/o/token/")
  BityToken createToken(
      @FormParam("client_id") String clientId,
      @FormParam("grant_type") String grantType,
      @FormParam("username") String username,
      @FormParam("password") String password)
      throws BityException;
}
