package org.knowm.xchange.coinbene;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneOrderBook;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTicker;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTrades;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Coinbene {
  /**
   * Retrieves a ticker.
   *
   * @param symbol the currency pair
   */
  @GET
  @Path("market/ticker")
  CoinbeneTicker.Container ticker(@QueryParam("symbol") String symbol)
      throws IOException, CoinbeneException;

  /**
   * The call for order book
   *
   * @param symbol the currency pair
   * @param size order book length limit
   */
  @GET
  @Path("market/orderbook")
  CoinbeneOrderBook.Container orderBook(
      @QueryParam("symbol") String symbol, @QueryParam("depth") Integer size)
      throws IOException, CoinbeneException;

  /**
   * The call for recent trades
   *
   * @param symbol the currency pair
   * @param size trades length limit
   */
  @GET
  @Path("market/trades")
  CoinbeneTrades trades(@QueryParam("symbol") String symbol, @QueryParam("size") Integer size)
      throws IOException, CoinbeneException;
}
