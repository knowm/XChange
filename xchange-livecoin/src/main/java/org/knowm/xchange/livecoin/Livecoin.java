package org.knowm.xchange.livecoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestrictions;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Livecoin {

  @GET
  @Path("exchange/restrictions")
  LivecoinRestrictions getProducts() throws IOException;

  @GET
  @Path("exchange/order_book?currencyPair={baseCurrency}/{targetCurrency}&depth={depth}")
  LivecoinOrderBook getOrderBook(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency,
      @PathParam("depth") int depth) throws IOException;

  @GET
  @Path("exchange/last_trades?currencyPair={baseCurrency}/{targetCurrency}")
  LivecoinTrade[] getTrades(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;
  
  @GET
  @Path("exchange/ticker?currencyPair={baseCurrency}/{targetCurrency}")
  LivecoinTicker getTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;
}