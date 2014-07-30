package com.xeiam.xchange.coinbase;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.coinbase.dto.account.CoinbaseToken;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;

/**
 * @author jamespedwards42
 */
@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Coinbase {

  @GET
  @Path("currencies")
  List<CoinbaseCurrency> getCurrencies() throws IOException;

  @GET
  @Path("currencies/exchange_rates")
  Map<String, BigDecimal> getCurrencyExchangeRates() throws IOException;

  @GET
  @Path("prices/buy")
  CoinbasePrice getBuyPrice(@QueryParam("qty") BigDecimal quantity, @QueryParam("currency") String currency) throws IOException;

  @GET
  @Path("prices/sell")
  CoinbasePrice getSellPrice(@QueryParam("qty") BigDecimal quantity, @QueryParam("currency") String currency) throws IOException;

  @GET
  @Path("prices/spot_rate")
  CoinbaseMoney getSpotRate(@QueryParam("currency") String currency) throws IOException;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("prices/historical")
  String getHistoricalSpotRates(@QueryParam("page") Integer page) throws IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("users")
  CoinbaseUser createUser(CoinbaseUser user) throws IOException;

  @POST
  @Path("tokens")
  CoinbaseToken createToken() throws IOException;

}
