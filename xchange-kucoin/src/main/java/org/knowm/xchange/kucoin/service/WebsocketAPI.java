package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.WebsocketResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface WebsocketAPI {
  /** Get connection details (URL + token) for subscribing to public websocket feeds */
  @POST
  @Path("/bullet-public")
  KucoinResponse<WebsocketResponse> getPublicWebsocketDetails() throws IOException;

  /** Get connection details (URL + token) for subscribing to private websocket feeds */
  @POST
  @Path("/bullet-private")
  KucoinResponse<WebsocketResponse> getPrivateWebsocketDetails(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase)
      throws IOException;
}