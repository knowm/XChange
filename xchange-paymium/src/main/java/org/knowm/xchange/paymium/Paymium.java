package org.knowm.xchange.paymium;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumMarketDepth;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTicker;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTrade;

@Path("/data/eur")
@Produces(MediaType.APPLICATION_JSON)
public interface Paymium {

  /**
   * @return Paymium ticker
   * @throws IOException
   */
  @GET
  @Path("ticker/")
  PaymiumTicker getPaymiumTicker() throws IOException;

  @GET
  @Path("depth/")
  PaymiumMarketDepth getOrderBook() throws IOException;

  @GET
  @Path("trades/")
  PaymiumTrade[] getTrades() throws IOException;
}