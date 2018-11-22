package org.knowm.xchange.kuna;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kuna.dto.KunaAccountDto;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Roman Dovgan */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface KunaAuthenticated extends Kuna {

  @POST
  @Path("members/me")
  KunaAccountDto getAccountInfo(
      @FormParam("tonce") SynchronizedValueFactory<Long> tonce,
      @FormParam("access_key") String accessKey,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;
}
