package org.xchange.kraken;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import org.xchange.kraken.dto.marketdata.KrakenDepthResult;
import org.xchange.kraken.dto.marketdata.KrakenTickerResult;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;

import com.xeiam.xchange.dto.marketdata.OrderBook;

/**
 * @author Benedikt Bünz
 */
@Path("0/public")
@Produces("application/json")
public interface Kraken {

  @GET
  @Path("Ticker")
  KrakenTickerResult getTicker(@QueryParam("pair") String currencyPair);

  /**
   * @param currencyPair kraken currency pair
   * @param count can be null = full {@link OrderBook}
   * @return
   */
  @GET
  @Path("Depth")
  KrakenDepthResult getDepth(@QueryParam("pair") String currencyPair, @QueryParam("count") Long count);

  @GET
  @Path("Trades")
  KrakenTradesResult getTrades(@QueryParam("pair") String currencyPair);

  @GET
  @Path("AssetPairs")
  KrakenAssetPairsResult getAssetPairs();
}
