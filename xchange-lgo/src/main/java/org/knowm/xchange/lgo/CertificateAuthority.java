package org.knowm.xchange.lgo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
