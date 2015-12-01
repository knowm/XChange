package com.xeiam.xchange.cointrader;

import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import com.xeiam.xchange.cointrader.dto.marketdata.CointraderTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

@Path("api4")
@Produces(MediaType.APPLICATION_JSON)
public interface Cointrader {

  @GET
  @Path("stats/{type}/{currencyPair}")
  CointraderTicker.Response getTicker(@PathParam("currencyPair") Pair currencyPair, @PathParam("type") CointraderTicker.Type type);

  @GET
  @Path("stats/orders/{currencyPair}/{type}/{limit}")
  CointraderOrderBook getOrderBook(@PathParam("currencyPair") Pair currencyPair, @PathParam("limit") Integer limit,
      @PathParam("type") OrderBookType type);

  @GET
  @Path("stats/orders/{currencyPair}/{type}")
  CointraderOrderBook getOrderBook(@PathParam("currencyPair") Pair currencyPair, @PathParam("type") OrderBookType type);

  @GET
  @Path("stats/orders/{currencyPair}")
  CointraderOrderBook getOrderBook(@PathParam("currencyPair") Pair currencyPair);

  enum OrderBookType {
    buy, sell, all
  }

  class Pair {
    private CurrencyPair pair;

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
