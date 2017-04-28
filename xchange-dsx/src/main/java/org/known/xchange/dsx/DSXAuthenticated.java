package org.known.xchange.dsx;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.known.xchange.dsx.dto.account.DSXAccountInfoReturn;

/**
 * @author Mikhail Wall
 */

@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface DSXAuthenticated extends DSX {

  @POST
  @Path("tapi")
  @FormParam("method")
  DSXAccountInfoReturn getInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") String signature)
}
