package com.xeiam.xchange.quoine;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface QuoineAuthenticated extends Quoine {

  @GET
  @Path("accounts")
  public QuoineAccountInfo getAccountInfo(@HeaderParam("X-Quoine-Device") String device, @HeaderParam("X-Quoine-User-Id") String userID,
      @HeaderParam("X-Quoine-User-Token") String userToken) throws IOException;

}
