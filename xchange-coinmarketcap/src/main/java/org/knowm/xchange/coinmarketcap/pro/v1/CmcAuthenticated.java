package org.knowm.xchange.coinmarketcap.pro.v1;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcCurrencyInfoResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcCurrencyMapResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcTickerListResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcTickerResponse;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CmcAuthenticated {
  String API_KEY_HEADER = "X-CMC_PRO_API_KEY";

  @GET
  @Path("cryptocurrency/info")
  CmcCurrencyInfoResponse getCurrencyInfo(
      @HeaderParam(API_KEY_HEADER) String apiKey, @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("cryptocurrency/map")
  CmcCurrencyMapResponse getCurrencyMap(
      @HeaderParam(API_KEY_HEADER) String apiKey,
      @QueryParam("listing_status") String listingStatus,
      @QueryParam("start") int start,
      @QueryParam("limit") int limit,
      @QueryParam("sort") String sort)
      throws IOException;

  @GET
  @Path("cryptocurrency/listings/latest")
  CmcTickerListResponse getLatestListing(
      @HeaderParam(API_KEY_HEADER) String apiKey,
      @QueryParam("start") int start,
      @QueryParam("limit") int limit,
      @QueryParam("convert") String convert,
      @QueryParam("sort") String sort,
      @QueryParam("sort_dir") String sortDirection,
      @QueryParam("cryptocurrency_type") String cryptocurrencyType)
      throws IOException;

  @GET
  @Path("cryptocurrency/quotes/latest")
  CmcTickerResponse getLatestQuotes(
      @HeaderParam(API_KEY_HEADER) String apiKey,
      @QueryParam("symbol") String symbol,
      @QueryParam("convert") String convert)
      throws IOException;
}