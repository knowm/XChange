package org.knowm.xchange.coinmarketcap;

import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapArrayData;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author allenday https://api.coinmarketcap.com/v2/ticker/ */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinMarketCap {

  @GET
  @Path("ticker")
  CoinMarketCapArrayData<CoinMarketCapTicker> getTickers(@QueryParam("structure") String structure)
      throws IOException;

  /**
   * @param start return results from rank [start] and above
   * @param limit number of results. 0 = unlimited
   * @see #getTickers(String structure)
   */
  @GET
  @Path("ticker")
  CoinMarketCapArrayData<CoinMarketCapTicker> getTickers(
      @QueryParam("start") int start,
      @QueryParam("limit") int limit,
      @QueryParam("structure") String structure)
      throws IOException;

  /**
   * @param limit number of results. 0 = unlimited
   * @param convert currency to get price converted to
   * @see #getTickers(String structure)
   */
  @GET
  @Path("ticker")
  CoinMarketCapArrayData<CoinMarketCapTicker> getTickers(
      @QueryParam("limit") int limit,
      @QueryParam("convert") String convert,
      @QueryParam("structure") String structure)
      throws IOException;

  /**
   * @param start return results from rank [start] and above
   * @param limit number of results. 0 = unlimited
   * @param convert currency to get price converted to
   * @see #getTickers(String structure)
   */
  @GET
  @Path("ticker")
  CoinMarketCapArrayData<CoinMarketCapTicker> getTickers(
      @QueryParam("start") int start,
      @QueryParam("limit") int limit,
      @QueryParam("convert") String convert,
      @QueryParam("structure") String structure)
      throws IOException;

  /**
   * @param limit number of results. 0 = unlimited
   * @see #getTickers(String structure)
   */
  @GET
  @Path("ticker")
  CoinMarketCapArrayData<CoinMarketCapTicker> getTickers(
      @QueryParam("limit") int limit, @QueryParam("structure") String structure) throws IOException;

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
