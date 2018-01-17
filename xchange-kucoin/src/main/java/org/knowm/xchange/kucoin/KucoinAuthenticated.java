package org.knowm.xchange.kucoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface KucoinAuthenticated extends Kucoin {

  static final String HEADER_APIKEY = "KC-API-KEY";
  static final String HEADER_NONCE = "KC-API-NONCE";
  static final String HEADER_SIGNATURE = "KC-API-SIGNATURE";

  @GET
  @Path("user/info")
  KucoinUserInfoResponse userInfo(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature
  ) throws IOException;

}
