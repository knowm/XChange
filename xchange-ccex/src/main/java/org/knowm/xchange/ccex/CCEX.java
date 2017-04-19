package org.knowm.xchange.ccex;

import java.io.IOException;
import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.ccex.dto.marketdata.CCEXGetorderbook;
import org.knowm.xchange.ccex.dto.marketdata.CCEXMarkets;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrades;
import org.knowm.xchange.ccex.dto.ticker.CCEXPairs;
import org.knowm.xchange.ccex.dto.ticker.CCEXTickerResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Path("t")
@Produces(MediaType.APPLICATION_JSON)
public interface CCEX {

  @GET
  @Path("api_pub.html?a=getmarkets")
  CCEXMarkets getProducts() throws IOException;

  @GET
  @Path("pairs.json")
  CCEXPairs getPairs() throws IOException;

  @GET
  @Path("{lpair}-{rpair}.json")
  CCEXTickerResponse getTicker(@PathParam("lpair") String lpair, @PathParam("rpair") String rpair) throws IOException;

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of data.
   */
  @GET
  @Path("api_pub.html?a=getorderbook&market={pair}&type=both&depth={depth}")
  CCEXGetorderbook getOrderBook(@PathParam("pair") Pair pair, @PathParam("depth") int depth) throws IOException;

  /**
   * Returns Latest trades that have occured for a specific market.
   */
  @GET
  @Path("api_pub.html?a=getmarkethistory&market={pair}&count=100")
  CCEXTrades getTrades(@PathParam("pair") Pair pair) throws IOException;

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
      return String.format("%s-%s", pair.base.getCurrencyCode().toLowerCase(), pair.counter.getCurrencyCode().toLowerCase());
    }
  }
}