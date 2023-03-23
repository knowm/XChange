package org.knowm.xchange.bankera;

import java.io.IOException;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.BankeraToken;
import org.knowm.xchange.bankera.dto.marketdata.BankeraMarketInfo;
import org.knowm.xchange.bankera.dto.marketdata.BankeraOrderBook;
import org.knowm.xchange.bankera.dto.marketdata.BankeraTickerResponse;
import org.knowm.xchange.bankera.dto.marketdata.BankeraTradesResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bankera {

  @POST
  @Path("/oauth/token")
  BankeraToken getToken(
      @FormParam("grant_type") String grantType,
      @FormParam("client_id") String clientId,
      @FormParam("client_secret") String clientSecret)
      throws BankeraException;

  @GET
  @Path("/tickers/{market}")
  BankeraTickerResponse getMarketTicker(@PathParam("market") String market)
      throws BankeraException, IOException;

  @GET
  @Path("/orderbooks/{market}")
  BankeraOrderBook getOrderbook(@PathParam("market") String market)
      throws BankeraException, IOException;

  @GET
  @Path("/trades/{market}")
  BankeraTradesResponse getRecentTrades(@PathParam("market") String market)
      throws BankeraException, IOException;

  @GET
  @Path("/general/info")
  BankeraMarketInfo getMarketInfo() throws BankeraException, IOException;
}
