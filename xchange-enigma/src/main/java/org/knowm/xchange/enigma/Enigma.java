package org.knowm.xchange.enigma;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.enigma.dto.account.EnigmaCredentials;
import org.knowm.xchange.enigma.dto.account.EnigmaLoginResponse;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Enigma {

  @POST
  @Path("user/login")
  @Consumes(MediaType.APPLICATION_JSON)
  EnigmaLoginResponse login(EnigmaCredentials loginRequest) throws IOException;
}
