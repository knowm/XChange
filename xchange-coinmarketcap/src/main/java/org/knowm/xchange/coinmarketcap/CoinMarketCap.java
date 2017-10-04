package org.knowm.xchange.coinmarketcap;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/**
 * @author allenday
 * https://api.coinmarketcap.com/v1/ticker/
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinMarketCap {

  @GET
  @Path("/ticker")
  List<CoinMarketCapTicker> getTickers() throws IOException;

  CoinMarketCapTicker getTicker(CoinMarketCap.Pair pair);


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
      return this == o || !(o == null || getClass() != o.getClass()) && Objects.equals(pair, ((Pair) o).pair);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pair);
    }

    @Override
    public String toString() {
      return pair == null ? "" : String.format("%s%s", pair.base.getCurrencyCode().toLowerCase(), pair.counter.getCurrencyCode().toLowerCase());
    }
  }
}
