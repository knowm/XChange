package org.knowm.xchange.therock;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.marketdata.TheRockOrderBook;
import org.knowm.xchange.therock.dto.marketdata.TheRockTicker;
import org.knowm.xchange.therock.dto.marketdata.TheRockTrades;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

// see https://www.therocktrading.com/pages/api
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface TheRock {

  // TODO review - inconsistent https://www.therocktrading.com/pages/api
  @GET
  @Path("funds/{id}/ticker")
  TheRockTicker getTicker(@PathParam("id") Pair currencyPair) throws TheRockException, IOException;

  // TODO review - inconsistent https://www.therocktrading.com/pages/api
  @GET
  @Path("funds/{id}/orderbook")
  TheRockOrderBook getOrderbook(@PathParam("id") Pair currencyPair)
      throws TheRockException, IOException;

  @GET
  @Path("funds/{id}/trades")
  TheRockTrades getTrades(@PathParam("id") Pair currencyPair, @QueryParam("after") Date after)
      throws IOException;

  class Pair {
    public final CurrencyPair pair;

    public Pair(CurrencyPair pair) {
      if (pair == null) {
        throw new IllegalArgumentException("Currency pair required.");
      }
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
      return String.format("%s%s", pair.base.getCurrencyCode(), pair.counter.getCurrencyCode());
    }
  }
}
