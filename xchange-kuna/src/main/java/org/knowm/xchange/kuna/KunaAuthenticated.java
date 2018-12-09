package org.knowm.xchange.kuna;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kuna.dto.KunaAccountDto;
import org.knowm.xchange.kuna.dto.KunaTradesHistoryDto;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Roman Dovgan */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface KunaAuthenticated extends Kuna {

  @GET
  @Path("/members/me")
  KunaAccountDto getAccountInfo(
      @QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
      @QueryParam("access_key") String accessKey,
      @QueryParam("signature") String signature)
      throws IOException;

  @GET
  @Path("/trades/my")
  KunaTradesHistoryDto getTradesHistory(
      @QueryParam("tonce") String tonce,
      @QueryParam("access_key") String accessKey,
      @QueryParam("signature") String signature,
      @QueryParam("market") String market);
}
