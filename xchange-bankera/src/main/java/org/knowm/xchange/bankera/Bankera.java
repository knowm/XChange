package org.knowm.xchange.bankera;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
