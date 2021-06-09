package org.knowm.xchange.okex.v5;

import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/v5")
public interface OkexAuthenticated extends Okex {
  String balancePath = "/account/balance";
  String currenciesPath = "/asset/currencies";

  Map<String, List<Integer>> privatePathRateLimits =
      new HashMap<String, List<Integer>>() {
        {
          put(balancePath, Arrays.asList(6, 1));
          put(currenciesPath, Arrays.asList(6, 1));
        }
      };

  @GET
  @Path(balancePath)
  OkexResponse<List<OkexWalletBalance>> getWalletBalances(
      @QueryParam("ccy") List<Currency> currencies,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase)
      throws IOException, OkexException;

  @GET
  @Path(currenciesPath)
  OkexResponse<List<OkexCurrency>> getCurrencies(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase)
      throws OkexException, IOException;
}
