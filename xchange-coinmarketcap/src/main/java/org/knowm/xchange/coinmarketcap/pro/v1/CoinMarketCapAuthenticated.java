package org.knowm.xchange.coinmarketcap.pro.v1;

import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CoinMarketCapCurrencyResponse;
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
  CoinMarketCapCurrencyResponse getCurrencyInfo(
      @HeaderParam(API_KEY_HEADER) String apiKey, @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("cryptocurrency/quotes/latest")
  CoinMarketCapTickerResponse getLatestQuote(
          @HeaderParam(API_KEY_HEADER) String apiKey,
          @QueryParam("symbol") String baseCurrency,
          @QueryParam("convert") String counterCurrency)
          throws IOException;

}
