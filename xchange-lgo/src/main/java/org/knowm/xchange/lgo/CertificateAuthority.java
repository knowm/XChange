package org.knowm.xchange.lgo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public interface CertificateAuthority {

  @Path("index.txt")
  @Produces(MediaType.TEXT_PLAIN)
  @GET
  String fetchIndex();

  @Path("{id}_public.pem")
  @Produces(MediaType.TEXT_PLAIN)
  @GET
  String fetchKey(@PathParam("id") String keyId);
}
