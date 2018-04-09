package org.knowm.xchange.coinbase.v2;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseException;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseCurrencyData;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseExchangeRateData;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbasePriceData;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseTimeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Coinbase {

  public static Logger LOG = LoggerFactory.getLogger(Coinbase.class.getPackage().getName());

  /**
   * All API calls should be made with a CB-VERSION header which guarantees that your call is using
   * the correct API version. <a
   * href="https://developers.coinbase.com/api/v2#versioning">developers.coinbase.com/api/v2#versioning</a>
   */
  final String CB_VERSION = "CB-VERSION";

  final String CB_VERSION_VALUE = "2018-04-08";

  @GET
  @Path("currencies")
  CoinbaseCurrencyData getCurrencies(@HeaderParam(CB_VERSION) String apiVersion)
      throws IOException, CoinbaseException;

  @GET
  @Path("exchange-rates")
  CoinbaseExchangeRateData getCurrencyExchangeRates(@HeaderParam(CB_VERSION) String apiVersion)
      throws IOException, CoinbaseException;

  @GET
  @Path("prices/{pair}/buy")
  CoinbasePriceData getBuyPrice(
      @HeaderParam(CB_VERSION) String apiVersion, @PathParam("pair") String pair)
      throws IOException, CoinbaseException;

  @GET
  @Path("prices/{pair}/sell")
  CoinbasePriceData getSellPrice(
      @HeaderParam(CB_VERSION) String apiVersion, @PathParam("pair") String pair)
      throws IOException, CoinbaseException;

  @GET
  @Path("prices/{pair}/spot")
  CoinbasePriceData getSpotRate(
      @HeaderParam(CB_VERSION) String apiVersion, @PathParam("pair") String pair)
      throws IOException, CoinbaseException;

  @GET
  @Path("prices/{pair}/spot")
  CoinbasePriceData getHistoricalSpotRate(
      @HeaderParam(CB_VERSION) String apiVersion,
      @PathParam("pair") String pair,
      @QueryParam("date") String date)
      throws IOException, CoinbaseException;

  @GET
  @Path("time")
  CoinbaseTimeData getTime(@HeaderParam(CB_VERSION) String apiVersion)
      throws IOException, CoinbaseException;
}
