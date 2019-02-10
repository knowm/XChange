package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinCoin;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinDealOrder;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinOrderBook;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Kucoin {

  /**
   * Retrieves a ticker.
   *
   * @param symbol the currency pair
   * @return
   * @throws IOException
   */
  @GET
  @Path("open/tick")
  KucoinResponse<KucoinTicker> tick(@QueryParam("symbol") String symbol)
      throws IOException, KucoinException;

  /**
   * Retrieves all tickers.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("open/tick")
  KucoinResponse<List<KucoinTicker>> tick() throws IOException, KucoinException;

  /**
   * Retrieves a list of all coins.
   *
   * @return
   * @throws IOException
   */
  @GET
  @Path("market/open/coins")
  KucoinResponse<List<KucoinCoin>> coins() throws IOException, KucoinException;

  /**
   * The call for order books
   *
   * @param symbol the currency pair
   * @param group ???
   * @param limit order book length limit
   * @return
   * @throws IOException
   */
  @GET
  @Path("open/orders")
  KucoinResponse<KucoinOrderBook> orders(
      @QueryParam("symbol") String symbol,
      @QueryParam("group") Integer group,
      @QueryParam("limit") Integer limit)
      throws IOException, KucoinException;

  /**
   * This is the call for recent trades.
   *
   * @param symbol the currency pair
   * @param limit limit list of trades to this length
   * @param since only retrieve trades since this datetime (couldnt get it to work tho)
   * @return
   * @throws IOException
   */
  @GET
  @Path("open/deal-orders")
  KucoinResponse<List<KucoinDealOrder>> dealOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("limit") Integer limit,
      @QueryParam("since") Long since)
      throws IOException, KucoinException;
}
