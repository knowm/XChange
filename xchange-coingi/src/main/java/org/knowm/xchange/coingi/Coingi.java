package org.knowm.xchange.coingi;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderBook;
import org.knowm.xchange.coingi.dto.marketdata.CoingiRollingAggregation;
import org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction;

@Path("current")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface Coingi {
  @GET
  @Path("order-book/{currencyPair}/{maxAskCount}/{maxBidCount}/{maxDepthRangeCount}")
  CoingiOrderBook getOrderBook(
      @PathParam("currencyPair") String currencyPair,
      @PathParam("maxAskCount") Integer maxAskCount,
      @PathParam("maxBidCount") Integer maxBidCount,
      @PathParam("maxDepthRangeCount") Integer maxDepthRangeCount)
      throws CoingiException, IOException;

  @GET
  @Path("transactions/{currencyPair}/{maxCount}")
  List<CoingiTransaction> getTransaction(
      @PathParam("currencyPair") String currencyPair, @PathParam("maxCount") Integer maxCount)
      throws CoingiException, IOException;

  @GET
  @Path("24hours-rolling-aggregation")
  List<CoingiRollingAggregation> getTradeAggregations() throws CoingiException, IOException;
}
