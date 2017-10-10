package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.marketdata.BinanceOrderBook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Path("/api/")
@Produces(MediaType.APPLICATION_JSON)
public interface Binance {

  /**
   * MARKET DATA API
   */
  @GET
  @Path("v1/ticker/24hr?symbol={symbol}")
  BinanceTicker getTicker(@PathParam("symbol") String currencyPair) throws IOException;

  @GET
  @Path("v1/depth?symbol={symbol}&limit={limit}")
  BinanceOrderBook getOrderBook(@PathParam("symbol") String currencyPair, @PathParam("limit") String limit) throws IOException;

  /**
   * ACCOUNT API
   */

}