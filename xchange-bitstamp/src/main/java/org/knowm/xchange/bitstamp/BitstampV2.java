package org.knowm.xchange.bitstamp;

import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampPairInfo;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author Matija Mazi See https://www.bitstamp.net/api/ for up-to-date docs. */
@Path("api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampV2 {

  @GET
  @Path("order_book/{pair}/")
  BitstampOrderBook getOrderBook(@PathParam("pair") Pair pair)
      throws IOException, BitstampException;

  @GET
  @Path("ticker/{pair}/")
  BitstampTicker getTicker(@PathParam("pair") BitstampV2.Pair pair)
      throws IOException, BitstampException;

  @GET
  @Path("ticker_hour/{pair}/")
  BitstampTicker getTickerHour(@PathParam("pair") BitstampV2.Pair pair)
      throws IOException, BitstampException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/{pair}/")
  BitstampTransaction[] getTransactions(
      @PathParam("pair") Pair pair,
      @QueryParam("time") BitstampMarketDataServiceRaw.BitstampTime time)
      throws IOException, BitstampException;

  @GET
  @Path("trading-pairs-info/")
  BitstampPairInfo[] getTradingPairsInfo() throws IOException, BitstampException;

  class Pair {
    public final CurrencyPair pair;

    public Pair(CurrencyPair pair) {
      this.pair = pair;
    }

    public Pair(String pair) {
      this(CurrencyPairDeserializer.getCurrencyPairFromString(pair));
    }

    @Override
    public boolean equals(Object o) {
      return this == o
          || !(o == null || getClass() != o.getClass()) && Objects.equals(pair, ((Pair) o).pair);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pair);
    }

    @Override
    public String toString() {
      return pair == null
          ? ""
          : String.format(
              "%s%s",
              pair.base.getCurrencyCode().toLowerCase(),
              pair.counter.getCurrencyCode().toLowerCase());
    }
  }
}
