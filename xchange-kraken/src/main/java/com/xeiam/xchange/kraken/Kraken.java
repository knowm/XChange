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
package com.xeiam.xchange.kraken;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenSpreadsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenTickerResult;

/**
 * @author Benedikt Bünz
 */
@Path("0")
@Produces(MediaType.APPLICATION_JSON)
public interface Kraken {

  @GET
  @Path("public/Ticker")
  KrakenTickerResult getTicker(@QueryParam("pair") String currencyPairs);

  @GET
  @Path("public/Depth")
  KrakenDepthResult getDepth(@QueryParam("pair") String currencyPair, @QueryParam("count") long count) throws IOException;

  @GET
  @Path("public/Trades")
  KrakenPublicTradesResult getTrades(@QueryParam("pair") String currencyPair) throws IOException;

  @GET
  @Path("public/Trades")
  KrakenPublicTradesResult getTrades(@QueryParam("pair") String currencyPair, @QueryParam("since") Long since) throws IOException;

  @GET
  @Path("public/Spread")
  KrakenSpreadsResult getSpread(@QueryParam("pair") String currencyPair, @QueryParam("since") Long since);

  @GET
  @Path("public/Assets")
  KrakenAssetsResult getAssets(@FormParam("aclass") String assetClass, @FormParam("asset") String assets) throws IOException;

  @GET
  @Path("public/AssetPairs")
  KrakenAssetPairsResult getAssetPairs(@FormParam("pair") String assetPairs) throws IOException;

  @GET
  @Path("public/Time")
  KrakenServerTimeResult getServerTime() throws IOException;
}
