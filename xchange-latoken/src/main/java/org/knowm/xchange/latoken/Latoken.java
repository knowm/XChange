package org.knowm.xchange.latoken;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.latoken.dto.LatokenException;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenCurrency;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenPair;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenRateLimits;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenTime;
import org.knowm.xchange.latoken.dto.marketdata.LatokenOrderbook;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTicker;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTrades;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Latoken {

  @GET
  @Path("api/v1/ExchangeInfo/time")
  /**
   * Returns current exchange time.
   *
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenTime getTime() throws IOException, LatokenException;

  @GET
  @Path("api/v1/ExchangeInfo/limits")
  /**
   * Information about request rate limits of API.
   *
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenRateLimits getRateLimits() throws IOException, LatokenException;

  @GET
  @Path("api/v1/ExchangeInfo/pairs")
  /**
   * Returns all trading pairs of Latoken exchange
   *
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenPair> getAllPairs() throws IOException, LatokenException;

  @GET
  @Path("api/v1/ExchangeInfo/pairs/{currency}")
  /**
   * Returns all trading pairs that contain desired currency
   *
   * @param currency - Currency target (e.g. LA)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenPair> getPairs(@PathParam("currency") String currency)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/ExchangeInfo/pair")
  /**
   * Gets info about desired trading pair.
   *
   * @param symbol - Symbol of traded pair (e.g. LAETH)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenPair getPair(@QueryParam("symbol") String symbol) throws IOException, LatokenException;

  @GET
  @Path("api/v1/ExchangeInfo/currencies")
  /**
   * Get all currencies available on Latoken exchange
   *
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenCurrency> getAllCurrencies() throws IOException, LatokenException;

  @GET
  @Path("api/v1/ExchangeInfo/currencies/{symbol}")
  /**
   * Get currency info by symbol or name
   *
   * @param symbol - Symbol or name of desired currency (e.g LA or Latoken)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenCurrency getCurrencies(@PathParam("symbol") String symbol)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/MarketData/tickers")
  /**
   * Get 24h ticker for all pairs
   *
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  List<LatokenTicker> getAllTickers() throws IOException, LatokenException;

  @GET
  @Path("api/v1/MarketData/ticker/{symbol}")
  /**
   * Get 24h ticker for desired pair
   *
   * @param symbol - Symbol of traded pair (example: LAETH)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenTicker getTicker(@PathParam("symbol") String symbol) throws IOException, LatokenException;

  @GET
  @Path("api/v1/MarketData/orderBook/{symbol}/{limit}")
  /**
   * Get OrderBook for desired pair
   *
   * @param symbol - Symbol of traded pair (example: LAETH)
   * @param limit - Number of price levels to fetch (Max value 100, Default value 10)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenOrderbook getOrderbook(@PathParam("symbol") String symbol, @PathParam("limit") int limit)
      throws IOException, LatokenException;

  @GET
  @Path("api/v1/MarketData/trades/{symbol}/{limit}")
  /**
   * Get trades history for desired pair
   *
   * @param symbol - Symbol of traded pair (example: LAETH)
   * @param limit - Number of trades to fetch (Max value 100, Default value 50)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  LatokenTrades getTrades(@PathParam("symbol") String symbol, @PathParam("limit") int limit)
      throws IOException, LatokenException;
}
