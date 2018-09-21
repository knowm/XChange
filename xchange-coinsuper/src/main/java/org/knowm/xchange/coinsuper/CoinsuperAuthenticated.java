package org.knowm.xchange.coinsuper;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.account.CoinsuperUserAssetInfo;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperOrderbook;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperPair;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperTicker;
import org.knowm.xchange.coinsuper.dto.trade.CoinsuperCancelOrder;
import org.knowm.xchange.coinsuper.dto.trade.CoinsuperOrder;
import org.knowm.xchange.coinsuper.dto.trade.OrderDetail;
import org.knowm.xchange.coinsuper.dto.trade.OrderList;

/**
 * @author kevingates
 *     <p>1. User Asset Queries
 *     <p>1.1 Personal Asset Information
 *     <p>2. Transactions
 *     <p>2.1 Place a Buy Order
 *     <p>2.2 Place a Sell Order
 *     <p>2.3 Cancel Order
 *     <p>2.4 Return Order List
 *     <p>2.5 Return Order Details
 *     <p>2.6 Pending Order List
 *     <p>3. Quotes
 *     <p>3.1 Detailed Market Quotes (Top 10%)
 *     <p>3.2 Detailed Market Quotes (Top 1-50)
 *     <p>3.3 Real Time Price Quotes
 *     <p>3.4 Real-Time Transaction Quotes kline
 *     <p>3.5 Supported Currency Pairs List
 */
@Path("api/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinsuperAuthenticated extends Coinsuper {

  @POST
  @Path("asset/userAssetInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * Obtain your own personal asset information.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<CoinsuperUserAssetInfo> getUserAssetInfo(Object parameters) throws IOException;

  @POST
  @Path("market/orderBook")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * orderBook to the Rest API.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<CoinsuperOrderbook> getOrderBooks(Object parameters) throws IOException;

  @POST
  @Path("market/kline")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * Tickers to the Rest API. /api/v1/market/kline
   *
   * @return
   * @throws IOException
   */
  Object getKlines(Object parameters) throws IOException;

  @POST
  @Path("order/buy")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * buy to the Rest API.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<CoinsuperOrder> createBuyOrder(Object parameters) throws IOException;

  @POST
  @Path("order/sell")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * sell to the Rest API.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<CoinsuperOrder> createSellOrder(Object parameters) throws IOException;

  @POST
  @Path("order/cancel")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * cancel order.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<CoinsuperCancelOrder> cancelOrder(Object parameters) throws IOException;

  @POST
  @Path("order/list")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * order list
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<List<OrderList>> orderList(Object parameters) throws IOException;

  @POST
  @Path("order/details")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * order details
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<List<OrderDetail>> orderDetails(Object parameters) throws IOException;

  @POST
  @Path("order/openList")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * order openList
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<List<String>> orderOpenList(Object parameters) throws IOException;

  @POST
  @Path("market/depth")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * Obtain top 10% bids and asks for a trade pair.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<CoinsuperOrderbook> marketDepth(Object parameters) throws IOException;

  @POST
  @Path("market/tickers")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * Return 50 of the latest transactions for a currency pair. /api/v1/market/tickers
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<List<CoinsuperTicker>> getTickers(Object parameters) throws IOException;

  @POST
  @Path("market/symbolList")
  @Consumes(MediaType.APPLICATION_JSON)
  /**
   * Returns a list of all supported currency pairs, as well as some currency pair specific trading
   * information.
   *
   * @return
   * @throws IOException
   */
  CoinsuperResponse<List<CoinsuperPair>> getSymbolList(Object parameters) throws IOException;
}
