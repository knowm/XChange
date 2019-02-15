package org.knowm.xchange.coinmarketcap.pro.v1;

import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapCurrencyInfoResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapCurrencyMapResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapTickerResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinMarketCapAuthenticated {
  String API_KEY_HEADER = "X-CMC_PRO_API_KEY";

  @GET
  @Path("cryptocurrency/info")
  CoinMarketCapCurrencyInfoResponse getCurrencyInfo(
      @HeaderParam(API_KEY_HEADER) String apiKey, @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("cryptocurrency/map")
  CoinMarketCapCurrencyMapResponse getCurrencyMap(
          @HeaderParam(API_KEY_HEADER) String apiKey,
          @QueryParam("listing_status") String isActive,
          @QueryParam("start") int start,
          @QueryParam("limit") int limit)
          throws IOException;

  @GET
  @Path("cryptocurrency/quotes/latest")
  CoinMarketCapTickerResponse getLatestQuote(
          @HeaderParam(API_KEY_HEADER) String apiKey,
          @QueryParam("symbol") String baseCurrency,
          @QueryParam("convert") String counterCurrency)
          throws IOException;

}
