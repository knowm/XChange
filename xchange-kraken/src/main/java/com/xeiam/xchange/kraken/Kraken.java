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
