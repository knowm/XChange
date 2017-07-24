package org.knowm.xchange.bitcoinde;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {

  @GET
  @Path("orders/compact")
  BitcoindeOrderbookWrapper getOrderBook(@HeaderParam("X-API-KEY") String apiKey, @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;

  @GET
  @Path("trades/history")
  BitcoindeTradesWrapper getTrades(@QueryParam("since_tid") Integer since, @HeaderParam("X-API-KEY") String apiKey, @HeaderParam("X-API-NONCE")
      SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;
}
