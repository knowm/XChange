package org.knowm.xchange.paymium;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.paymium.dto.marketdata.PaymiumMarketDepth;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTicker;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Paymium {

  /**
   * @return Paymium ticker
   * @throws IOException
   */
  @GET
  @Path("ticker/")
  public PaymiumTicker getPaymiumTicker() throws IOException;

  @GET
  @Path("depth/")
  public PaymiumMarketDepth getOrderBook() throws IOException;

  @GET
  @Path("trades/")
  public PaymiumTrade[] getTrades() throws IOException;
}
