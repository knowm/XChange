package org.knowm.xchange.lakebtc;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author kpysniak */
@Path("api_v2")
@Produces(MediaType.APPLICATION_JSON)
public interface LakeBTC {
  /**
   * @return LakeBTC ticker
   * @throws IOException
   */
  @GET
  @Path("ticker")
  Map<String, LakeBTCTicker> getLakeBTCTickers() throws IOException;

  @GET
  @Path("bcorderbook?symbol={pair}")
  LakeBTCOrderBook getLakeBTCOrderBookUSD(@PathParam("pair") LakeBTC.Pair pair) throws IOException;

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
