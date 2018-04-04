package org.knowm.xchange.liqui;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiDepthResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiInfoResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiPublicTradesResult;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiTickersResult;

@Path("api/3")
@Produces(MediaType.APPLICATION_JSON)
public interface Liqui {

  @GET
  @Path("info")
  @Produces(MediaType.APPLICATION_JSON)
  LiquiInfoResult getInfo();

  @GET
  @Path("ticker/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  LiquiTickersResult getTicker(@PathParam("pairs") Pairs pairs);

  @GET
  @Path("depth/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  LiquiDepthResult getDepth(@PathParam("pairs") Pairs pairs);

  @GET
  @Path("depth/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  LiquiDepthResult getDepth(@PathParam("pairs") Pairs pairs, @QueryParam("limit") int limit);

  @GET
  @Path("trades/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  LiquiPublicTradesResult getTrades(@PathParam("pairs") Pairs pairs);

  @GET
  @Path("trades/{pairs}")
  @Produces(MediaType.APPLICATION_JSON)
  LiquiPublicTradesResult getTrades(
      @PathParam("pairs") Pairs pairs, @QueryParam("limit") int limit);

  class Pairs {

    private final List<CurrencyPair> currencyPairs;

    public Pairs(final CurrencyPair currencyPair) {
      this(Collections.singletonList(currencyPair));
    }

    public Pairs(final List<CurrencyPair> currencyPairs) {
      this.currencyPairs = currencyPairs;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      final Pairs pairs = (Pairs) o;

      return currencyPairs != null
          ? currencyPairs.equals(pairs.currencyPairs)
          : pairs.currencyPairs == null;
    }

    @Override
    public int hashCode() {
      return Objects.hash(currencyPairs);
    }

    @Override
    public String toString() {
      final StringBuilder builder = new StringBuilder();
      for (int i = 0; i < currencyPairs.size(); i++) {
        builder.append(
            String.format(
                "%s_%s",
                currencyPairs.get(i).base.getCurrencyCode().toLowerCase(),
                currencyPairs.get(i).counter.getCurrencyCode().toLowerCase()));

        if (i < currencyPairs.size() - 1) {
          builder.append("-");
        }
      }

      return builder.toString();
    }
  }
}
