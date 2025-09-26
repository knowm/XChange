package org.knowm.xchange.bitso;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.marketdata.BitsoAvailableBooks;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTrades;

@Path("api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitso {

  /** Returns available trading books (currency pairs) on Bitso. */
  @GET
  @Path("available_books")
  BitsoAvailableBooks getAvailableBooks() throws BitsoException, IOException;

  /**
   * Returns "bids" and "asks" for the specified book. Each is a list of open orders and each order
   * is represented as a list of price and amount.
   */
  @GET
  @Path("order_book")
  BitsoOrderBook getOrderBook(@QueryParam("book") String book) throws BitsoException, IOException;

  /** Returns ticker information for the specified book. */
  @GET
  @Path("ticker")
  BitsoTicker getTicker(@QueryParam("book") String book) throws BitsoException, IOException;

  /** Returns a list of recent trades for the specified book. */
  @GET
  @Path("trades")
  BitsoTrades getTrades(@QueryParam("book") String book) throws BitsoException, IOException;

  /** Returns a list of recent trades for the specified book with optional parameters. */
  @GET
  @Path("trades")
  BitsoTrades getTrades(
      @QueryParam("book") String book,
      @QueryParam("marker") String marker,
      @QueryParam("sort") String sort,
      @QueryParam("limit") Integer limit)
      throws BitsoException, IOException;
}
