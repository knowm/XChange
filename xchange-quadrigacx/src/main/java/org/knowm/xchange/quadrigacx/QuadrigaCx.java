package org.knowm.xchange.quadrigacx;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.quadrigacx.dto.QuadrigaCxException;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxOrderBook;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxTicker;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxTransaction;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface QuadrigaCx {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("order_book?book={base}_{counter}")
  QuadrigaCxOrderBook getOrderBook(@PathParam("base") String base, @PathParam("counter") String counter) throws QuadrigaCxException, IOException;

  @GET
  @Path("ticker?book={base}_{counter}")
  QuadrigaCxTicker getTicker(@PathParam("base") String base, @PathParam("counter") String counter) throws QuadrigaCxException, IOException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions?book={base}_{counter}")
  QuadrigaCxTransaction[] getTransactions(@PathParam("base") String base,
      @PathParam("counter") String counter) throws QuadrigaCxException, IOException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions?book={base}_{counter}")
  QuadrigaCxTransaction[] getTransactions(@PathParam("base") String base, @PathParam("counter") String counter,
      @QueryParam("time") String time) throws QuadrigaCxException, IOException;

}
