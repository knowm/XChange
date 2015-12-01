package com.xeiam.xchange.therock;

import java.io.IOException;
import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.therock.dto.TheRockException;
import com.xeiam.xchange.therock.dto.marketdata.TheRockTicker;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface TheRock {

  @GET
  @Path("funds/{id}/ticker")
  TheRockTicker getTicker(@PathParam("id") Pair currencyPair) throws TheRockException, IOException;

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
      return this == o || !(o == null || getClass() != o.getClass()) && Objects.equals(pair, ((Pair) o).pair);
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
