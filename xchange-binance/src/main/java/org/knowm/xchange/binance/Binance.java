package org.knowm.xchange.binance;

import static org.knowm.xchange.binance.BinanceResilience.*;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinancePrice;
import org.knowm.xchange.binance.dto.marketdata.BinancePriceQuantity;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.dto.meta.BinanceTime;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.client.resilience.Decorator;
import org.knowm.xchange.client.resilience.RateLimiter;
import org.knowm.xchange.client.resilience.Retry;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Binance {

  @GET
  @Path("api/v1/ping")
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER))
  /**
   * Test connectivity to the Rest API.
   *
   * @return
   * @throws IOException
   */
  Object ping() throws IOException;

  @GET
  @Path("api/v1/time")
  @Decorator(retry = @Retry(name = "time"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER))
  /**
   * Test connectivity to the Rest API and get the current server time.
   *
   * @return
   * @throws IOException
   */
  BinanceTime time() throws IOException;

  @GET
  @Path("api/v1/exchangeInfo")
  @Decorator(retry = @Retry(name = "exchangeInfo"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER))
  /**
   * Current exchange trading rules and symbol information.
   *
   * @return
   * @throws IOException
   */
  BinanceExchangeInfo exchangeInfo() throws IOException;

  @GET
  @Path("api/v1/depth")
  @Decorator(retry = @Retry(name = "depth"))
  @Decorator(
      rateLimiter =
          @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER, weightMethodName = "depthWeight"))
  /**
   * @param symbol
   * @param limit optional, default 100; max 100.
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  BinanceOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, BinanceException;

  public static int depthWeight(String symbol, Integer limit) {
    if (limit <= 100) {
      return 1;
    } else if (limit <= 500) {
      return 5;
    } else if (limit <= 1000) {
      return 10;
    }
    return 50;
  }

  @GET
  @Path("api/v1/aggTrades")
  @Decorator(retry = @Retry(name = "aggTrades"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER))
  /**
   * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with the
   * same price will have the quantity aggregated.<br>
   * If both startTime and endTime are sent, limit should not be sent AND the distance between
   * startTime and endTime must be less than 24 hours.<br>
   * If frondId, startTime, and endTime are not sent, the most recent aggregate trades will be
   * returned.
   *
   * @param symbol
   * @param fromId optional, ID to get aggregate trades from INCLUSIVE.
   * @param startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   * @param endTime optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   * @param limit optional, Default 500; max 500.
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinanceAggTrades> aggTrades(
      @QueryParam("symbol") String symbol,
      @QueryParam("fromId") Long fromId,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, BinanceException;

  @GET
  @Path("api/v1/klines")
  @Decorator(retry = @Retry(name = "klines"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER))
  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.<br>
   * If startTime and endTime are not sent, the most recent klines are returned.
   *
   * @param symbol
   * @param interval
   * @param limit optional, default 500; max 500.
   * @param startTime optional
   * @param endTime optional
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<Object[]> klines(
      @QueryParam("symbol") String symbol,
      @QueryParam("interval") String interval,
      @QueryParam("limit") Integer limit,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime)
      throws IOException, BinanceException;

  @GET
  @Path("api/v1/ticker/24hr")
  @Decorator(retry = @Retry(name = "ticker24h"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER, weight = 5))
  /**
   * 24 hour price change statistics for all symbols. - bee carreful this api call have a big
   * weight, only about 4 call per minut can be without ban.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinanceTicker24h> ticker24h() throws IOException, BinanceException;

  @GET
  @Path("api/v1/ticker/24hr")
  @Decorator(retry = @Retry(name = "ticker24h"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER))
  /**
   * 24 hour price change statistics.
   *
   * @param symbol
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  BinanceTicker24h ticker24h(@QueryParam("symbol") String symbol)
      throws IOException, BinanceException;

  @GET
  @Path("api/v1/ticker/allPrices")
  @Decorator(retry = @Retry(name = "tickerAllPrices"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER, weight = 2))
  /**
   * Latest price for all symbols.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinancePrice> tickerAllPrices() throws IOException, BinanceException;

  @GET
  @Path("api/v3/ticker/bookTicker")
  @Decorator(retry = @Retry(name = "tickerAllBookTickers"))
  @Decorator(rateLimiter = @RateLimiter(name = REQUEST_WEIGHT_RATE_LIMITER, weight = 2))
  /**
   * Best price/qty on the order book for all symbols.
   *
   * @return
   * @throws IOException
   * @throws BinanceException
   */
  List<BinancePriceQuantity> tickerAllBookTickers() throws IOException, BinanceException;
}
