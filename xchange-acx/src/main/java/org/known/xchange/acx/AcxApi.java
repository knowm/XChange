package org.known.xchange.acx;

import org.knowm.xchange.dto.Order;
import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.account.AcxAccountInfo;
import org.known.xchange.acx.dto.marketdata.AcxOrder;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.dto.marketdata.AcxMarket;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface AcxApi {
    /**
     * Get ticker of specific market.
     *
     * @param market Unique market id. It's always in the form of xxxyyy,
     *               where xxx is the base currency code, yyy is the quote
     *               currency code, e.g. 'btcaud'. All available markets c
     *               an be found at /api/v2/markets.
     */
    @GET
    @Path("/tickers/{market}.json")
    AcxMarket getTicker(@PathParam("market") String market) throws IOException;

    /**
     * Get the order book of specified market.
     *
     * @param market    Unique market id. It's always in the form of xxxyyy,
     *                  where xxx is the base currency code, yyy is the quote
     *                  currency code, e.g. 'btcaud'. All available markets c
     *                  an be found at /api/v2/markets.
     * @param bidsLimit Limit the number of returned buy orders. Default to 20.
     * @param asksLimit Limit the number of returned sell orders. Default to 20.
     */
    @GET
    @Path("/order_book.json?market={market}&asks_limit={asks_limit}&bids_limit={bids_limit}")
    AcxOrderBook getOrderBook(@PathParam("market") String market,
                              @PathParam("bids_limit") long bidsLimit,
                              @PathParam("asks_limit") long asksLimit) throws IOException;

    /**
     * Get recent trades on market, each trade is included only once. Trades are sorted in reverse creation order.
     *
     * @param market Unique market id. It's always in the form of xxxyyy,
     *               where xxx is the base currency code, yyy is the quote
     *               currency code, e.g. 'btcaud'. All available markets c
     *               an be found at /api/v2/markets.
     */
    @GET
    @Path("/trades.json?market={market}")
    List<AcxTrade> getTrades(@PathParam("market") String market) throws IOException;

    /**
     * Get your profile and accounts info.
     *
     * @param accessKey Access key.
     * @param tonce     Tonce is an integer represents the milliseconds elapsed since Unix epoch.
     * @param signature The signature of your request payload, generated using your secret key.
     */
    @GET
    @Path("/members/me.json?access_key={access_key}&tonce={tonce}&signature={signature}")
    AcxAccountInfo getAccountInfo(@PathParam("access_key") String accessKey,
                                  @PathParam("tonce") long tonce,
                                  @PathParam("signature") ParamsDigest signature) throws IOException;

    /**
     * Get your orders, results are paginated.
     *
     * @param accessKey Access key.
     * @param tonce     Tonce is an integer represents the milliseconds elapsed since Unix epoch.
     * @param market    Unique market id. It's always in the form of xxxyyy,
     *                  where xxx is the base currency code, yyy is the quote
     *                  currency code, e.g. 'btcaud'. All available markets c
     *                  an be found at /api/v2/markets.
     * @param signature The signature of your request payload, generated using your secret key.
     */
    @GET
    @Path("/orders.json?access_key={access_key}&tonce={tonce}&market={market}&signature={signature}")
    List<AcxOrder> getOrders(@PathParam("access_key") String accessKey,
                             @PathParam("tonce") long tonce,
                             @PathParam("market") String market,
                             @PathParam("signature") ParamsDigest signature) throws IOException;


    /**
     * Cancel an order.
     *
     * @param accessKey Access key.
     * @param tonce     Tonce is an integer represents the milliseconds elapsed since Unix epoch.
     * @param id        Unique order id.
     * @param signature The signature of your request payload, generated using your secret key.
     */
    @POST
    @Path("/order/delete.json?access_key={access_key}&tonce={tonce}&id={id}&signature={signature}")
    AcxOrder cancelOrder(@PathParam("access_key") String accessKey,
                         @PathParam("tonce") long tonce,
                         @PathParam("id") String id,
                         @PathParam("signature") ParamsDigest signature) throws IOException;


}
