/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinbase;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.coinbase.dto.CoinbaseToken;
import com.xeiam.xchange.coinbase.dto.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;

/**
 * @author jamespedwards42
 */
@Path("api/v1")
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
  CoinbaseAmount getSpotRate(@QueryParam("currency") String currency) throws IOException;
  
  @GET
  @Path("prices/historical")
  CoinbaseSpotPriceHistory getHistoricalSpotRates(@QueryParam("page") Integer page) throws IOException;
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("users")
  CoinbaseUser createUser(CoinbaseUser user) throws IOException;
  
  @POST
  @Path("tokens")
  CoinbaseToken createToken() throws IOException;
  
}
