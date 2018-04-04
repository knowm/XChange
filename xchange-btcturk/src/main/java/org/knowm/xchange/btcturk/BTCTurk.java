package org.knowm.xchange.btcturk;

import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOHLC;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author semihunaldi */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCTurk {

  @GET
  @Path("ticker/")
  BTCTurkTicker getTicker(@QueryParam("pairSymbol") Pair pairSymbol) throws IOException;

  @GET
  @Path("orderbook/")
  BTCTurkOrderBook getOrderBook(@QueryParam("pairSymbol") Pair pairSymbol) throws IOException;

  @GET
  @Path("trades/")
  BTCTurkTrade[] getTrades(
      @QueryParam("pairSymbol") Pair pairSymbol, @QueryParam("last") Integer last)
      throws IOException;

  @GET
  @Path("ohlcdata/")
  BTCTurkOHLC[] getOHLC(@QueryParam("pairSymbol") Pair pairSymbol, @QueryParam("last") Integer last)
      throws IOException;

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
          : String.format("%s%s", pair.base.getCurrencyCode(), pair.counter.getCurrencyCode());
    }
  }
}
