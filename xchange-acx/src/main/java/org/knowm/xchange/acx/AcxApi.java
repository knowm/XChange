package org.knowm.xchange.acx;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.acx.dto.AcxTrade;
import org.knowm.xchange.acx.dto.account.AcxAccountInfo;
import org.knowm.xchange.acx.dto.marketdata.AcxMarket;
import org.knowm.xchange.acx.dto.marketdata.AcxOrder;
import org.knowm.xchange.acx.dto.marketdata.AcxOrderBook;
import si.mazi.rescu.ParamsDigest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface AcxApi {
  /**
   * Get ticker of specific market.
   *
   * @param market Unique market id. It's always in the form of xxxyyy, where xxx is the base
   *     currency code, yyy is the quote currency code, e.g. 'btcaud'. All available markets c an be
   *     found at /api/v2/markets.
   */
  @GET
  @Path("/tickers/{market}.json")
  AcxMarket getTicker(@PathParam("market") String market) throws IOException;

  /**
   * Get the order book of specified market.
   *
   * @param market Unique market id. It's always in the form of xxxyyy, where xxx is the base
   *     currency code, yyy is the quote currency code, e.g. 'btcaud'. All available markets c an be
   *     found at /api/v2/markets.
   * @param bidsLimit Limit the number of returned buy orders. Default to 20.
   * @param asksLimit Limit the number of returned sell orders. Default to 20.
   */
  @GET
  @Path("/order_book.json?market={market}&asks_limit={asks_limit}&bids_limit={bids_limit}")
  AcxOrderBook getOrderBook(
      @PathParam("market") String market,
      @PathParam("bids_limit") long bidsLimit,
      @PathParam("asks_limit") long asksLimit)
      throws IOException;

  /**
   * Get recent trades on market, each trade is included only once. Trades are sorted in reverse
   * creation order.
   *
   * @param market Unique market id. It's always in the form of xxxyyy, where xxx is the base
   *     currency code, yyy is the quote currency code, e.g. 'btcaud'. All available markets c an be
   *     found at /api/v2/markets.
   */
  @GET
  @Path("/trades.json?market={market}")
  List<AcxTrade> getTrades(@PathParam("market") String market) throws IOException;

  /**
   * Get recent trades on market, each trade is included only once. Trades are sorted in reverse
   * creation order.
   *
   * @param market Unique market id. It's always in the form of xxxyyy, where xxx is the base
   *     currency code, yyy is the quote currency code, e.g. 'btcaud'. All available markets c an be
   *     found at /api/v2/markets.
   */
  @GET
  @Path("/trades/my.json")
  List<AcxTrade> getMyTrades(
      @QueryParam("access_key") String accessKey,
      @QueryParam("tonce") long tonce,
      @QueryParam("signature") ParamsDigest signature,
      @QueryParam("market") String market,
      @QueryParam("limit") String limit,
      @QueryParam("order") String order,
      @QueryParam("from") String from,
      @QueryParam("to") String to,
      @QueryParam("timestamp") String timestamp)
      throws IOException;

  /**
   * Get your profile and accounts info.
   *
   * @param accessKey Access key.
   * @param tonce Tonce is an integer represents the milliseconds elapsed since Unix epoch.
   * @param signature The signature of your request payload, generated using your secret key.
   */
  @GET
  @Path("/members/me.json?access_key={access_key}&tonce={tonce}&signature={signature}")
  AcxAccountInfo getAccountInfo(
      @PathParam("access_key") String accessKey,
      @PathParam("tonce") long tonce,
      @PathParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Get your orders, results are paginated.
   *
   * @param accessKey Access key.
   * @param tonce Tonce is an integer represents the milliseconds elapsed since Unix epoch.
   * @param market Unique market id. It's always in the form of xxxyyy, where xxx is the base
   *     currency code, yyy is the quote currency code, e.g. 'btcaud'. All available markets c an be
   *     found at /api/v2/markets.
   * @param signature The signature of your request payload, generated using your secret key.
   */
  @GET
  @Path("/orders.json?access_key={access_key}&tonce={tonce}&market={market}&signature={signature}")
  List<AcxOrder> getOrders(
      @PathParam("access_key") String accessKey,
      @PathParam("tonce") long tonce,
      @PathParam("market") String market,
      @PathParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Get your orders, results are paginated.
   *
   * @param accessKey Access key.
   * @param tonce Tonce is an integer represents the milliseconds elapsed since Unix epoch.
   * @param id Order id
   * @param signature The signature of your request payload, generated using your secret key.
   */
  @GET
  @Path("/order.json?access_key={access_key}&tonce={tonce}&id={id}&signature={signature}")
  AcxOrder getOrder(
      @PathParam("access_key") String accessKey,
      @PathParam("tonce") long tonce,
      @PathParam("id") long id,
      @PathParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Create a Sell/Buy order.
   *
   * @param accessKey Access key.
   * @param tonce Tonce is an integer represents the milliseconds elapsed since Unix epoch.
   * @param market Unique market id. It's always in the form of xxxyyy, where xxx is the base
   *     currency code, yyy is the quote currency code, e.g. 'btcaud'. All available markets c an be
   *     found at /api/v2/markets.
   * @param side Either 'sell' or 'buy'.
   * @param volume The amount user want to sell/buy. An order could be partially executed, e.g. an
   *     order sell 5 btc can be matched with a buy 3 btc order, left 2 btc to be sold; in this case
   *     the order's volume would be '5.0', its remaining_volume would be '2.0', its executed volume
   *     is '3.0'.
   * @param price Price for each unit. e.g. If you want to sell/buy 1 btc at 3000 CNY, the price is
   *     '3000.0'
   * @param ordType no docs, perhaps limit or market
   * @param signature The signature of your request payload, generated using your secret key.
   */
  @POST
  @Path("/orders.json")
  AcxOrder createOrder(
      @FormParam("access_key") String accessKey,
      @FormParam("tonce") long tonce,
      @FormParam("market") String market,
      @FormParam("side") String side,
      @FormParam("volume") String volume,
      @FormParam("price") String price,
      @FormParam("ord_type") String ordType,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Cancel an order.
   *
   * @param accessKey Access key.
   * @param tonce Tonce is an integer represents the milliseconds elapsed since Unix epoch.
   * @param id Unique order id.
   * @param signature The signature of your request payload, generated using your secret key.
   */
  @POST
  @Path("/order/delete.json")
  AcxOrder cancelOrder(
      @FormParam("access_key") String accessKey,
      @FormParam("tonce") long tonce,
      @FormParam("id") String id,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;
}
