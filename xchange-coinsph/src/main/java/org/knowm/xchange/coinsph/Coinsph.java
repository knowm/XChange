package org.knowm.xchange.coinsph;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBook;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphPublicTrade;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker;
import org.knowm.xchange.coinsph.dto.meta.CoinsphExchangeInfo;
import org.knowm.xchange.coinsph.dto.meta.CoinsphTime;

@Path("/openapi/v1") // Base path for v1 of Coins.ph API
@Produces(MediaType.APPLICATION_JSON)
public interface Coinsph {

  /**
   * Test connectivity to the Rest API.
   *
   * @return Empty JSON body {}
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("ping")
  Object ping() throws IOException, CoinsphException;

  /**
   * Test connectivity to the Rest API and get the current server time.
   *
   * @return Server time
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("time")
  CoinsphTime time() throws IOException, CoinsphException;

  /**
   * Current exchange trading rules and symbol information.
   *
   * @return Exchange information
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("exchangeInfo")
  CoinsphExchangeInfo exchangeInfo() throws IOException, CoinsphException;

  /**
   * Get 24hr ticker price change statistics for a single symbol.
   *
   * @param symbol Trading symbol (e.g., BTCPHP)
   * @return Ticker information
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("ticker/24hr")
  CoinsphTicker getTicker24hr(@QueryParam("symbol") String symbol)
      throws IOException, CoinsphException;

  /**
   * Get 24hr ticker price change statistics for all symbols. If no symbol is provided, tickers for
   * all symbols will be returned in an array.
   *
   * @return List of ticker information, or single ticker if symbol is provided (API behavior might
   *     vary)
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("ticker/24hr")
  List<CoinsphTicker> getTicker24hr()
      throws IOException, CoinsphException; // For all symbols (no symbol query param)

  /**
   * Get the order book for a specific symbol.
   *
   * @param symbol Trading symbol (e.g., BTCPHP)
   * @param limit Optional. Default 100; max 5000. Valid limits: [5, 10, 20, 50, 100, 500, 1000,
   *     5000]
   * @return Order book
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("depth")
  CoinsphOrderBook getOrderBook(
      @QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, CoinsphException;

  /**
   * Get recent trades (up to last 500).
   *
   * @param symbol Trading symbol (e.g., BTCPHP)
   * @param limit Optional. Default 500; max 1000.
   * @return List of public trades
   * @throws IOException
   * @throws CoinsphException
   */
  @GET
  @Path("trades")
  List<CoinsphPublicTrade> getTrades(
      @QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, CoinsphException;
}
