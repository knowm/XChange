package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.kraken.dto.response.KrakenResult;
import info.bitrich.xchangestream.kraken.dto.response.KrakenWebsocketToken;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenAuthenticated {

  @POST
  @Path("0/private/GetWebSocketsToken")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  KrakenResult<KrakenWebsocketToken> getWebsocketToken(
      @HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Sign") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;
}
