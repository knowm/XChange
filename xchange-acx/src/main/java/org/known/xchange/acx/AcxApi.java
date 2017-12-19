package org.known.xchange.acx;

import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.dto.marketdata.AcxMarket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
     * @param market Unique market id. It's always in the form of xxxyyy,
     *               where xxx is the base currency code, yyy is the quote
     *               currency code, e.g. 'btcaud'. All available markets c
     *               an be found at /api/v2/markets.
     */
    @GET
    @Path("/order_book.json?market={market}")
    AcxOrderBook getOrderBook(@PathParam("market") String market) throws IOException;

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
}
