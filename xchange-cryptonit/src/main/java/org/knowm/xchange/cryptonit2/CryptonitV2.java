package org.knowm.xchange.cryptonit2;

import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitWithdrawal;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitOrderBook;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTransaction;
import org.knowm.xchange.cryptonit2.service.CryptonitMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author Matija Mazi See https://www.cryptonit.net/api/ for up-to-date docs. */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptonitV2 {

  @GET
  @Path("order_book/{pair}/")
  CryptonitOrderBook getOrderBook(@PathParam("pair") Pair pair)
      throws IOException, CryptonitException;

  @GET
  @Path("ticker/{pair}/")
  CryptonitTicker getTicker(@PathParam("pair") Pair pair) throws IOException, CryptonitException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/{pair}/")
  CryptonitTransaction[] getTransactions(
      @PathParam("pair") Pair pair,
      @QueryParam("time") CryptonitMarketDataServiceRaw.CryptonitTime time)
      throws IOException, CryptonitException;

  @GET
  @Path("/fiat_withdrawal/methods/")
  CryptonitWithdrawal fiatWithdrawalMethods() throws CryptonitException, IOException;

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
