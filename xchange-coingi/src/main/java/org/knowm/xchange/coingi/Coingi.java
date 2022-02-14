package org.knowm.xchange.coingi;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderBook;
import org.knowm.xchange.coingi.dto.marketdata.CoingiRollingAggregation;
import org.knowm.xchange.coingi.dto.marketdata.CoingiTicker;
import org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction;

@Path("current")
@Produces(MediaType.APPLICATION_JSON)
public interface Coingi {
  @GET
  @Path("ticker/{currencyPair}/{aggregationIntervalSize}/{maxCount}")
  List<CoingiTicker> getTicker(
      @PathParam("currencyPair") String currencyPair,
      @PathParam("aggregationIntervalSize")
          Integer aggregationIntervalSize, // 0 (minute), 1 (hour), 2 (day), 3 (week)
      @PathParam("maxCount") Integer maxCount)
      throws CoingiException, IOException;

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
