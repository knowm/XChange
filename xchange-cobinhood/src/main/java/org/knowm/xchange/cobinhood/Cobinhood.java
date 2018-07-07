package org.knowm.xchange.cobinhood;

import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.marketdata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Cobinhood {
  /**
   * Retrieves a ticker.
   *
   * @param symbol the currency pair
   * @return
   * @throws IOException
   */
  @GET
  @Path("market/tickers/{symbol}")
  CobinhoodResponse<CobinhoodTicker.Container> tick(@PathParam("symbol") String symbol)
      throws IOException, CobinhoodException;

  /**
   * Retrieves all tickers.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("market/tickers")
  CobinhoodResponse<CobinhoodTickers> tick() throws IOException, CobinhoodException;

  /**
   * Retrieves a list of all currencies.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("market/currencies")
  CobinhoodResponse<CobinhoodCurrencies> currencies() throws IOException, CobinhoodException;

  /**
   * The call for order books
   *
   * @param symbol the currency pair
   * @param limit order book length limit
   * @return
   * @throws IOException
   */
  @GET
  @Path("market/orderbooks/{symbol}")
  CobinhoodResponse<CobinhoodOrderBook.Container> orders(
      @PathParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, CobinhoodException;

  /**
   * The call for recent trades
   *
   * @param symbol the currency pair
   * @param limit trades length limit
   * @return
   * @throws IOException
   */
  @GET
  @Path("market/trades/{symbol}")
  CobinhoodResponse<CobinhoodTrades> trades(
      @PathParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, CobinhoodException;
}
