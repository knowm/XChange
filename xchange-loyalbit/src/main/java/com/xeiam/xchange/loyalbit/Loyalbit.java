package com.xeiam.xchange.loyalbit;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;
import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitTicker;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Loyalbit {

  @GET
  @Path("ticker/")
  LoyalbitTicker getTicker() throws IOException;

  @GET
  @Path("orderbook/")
  LoyalbitOrderBook getOrderBook() throws IOException;

}
