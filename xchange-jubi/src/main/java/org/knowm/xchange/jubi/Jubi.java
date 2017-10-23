package org.knowm.xchange.jubi;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;
import org.knowm.xchange.jubi.dto.marketdata.JubiTrade;

/**
 * Created by Yingzhe on 3/16/2015.
 */
@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Jubi {
  /**
   * Gets ticker from Jubi for currency pairs with CNY as counter URL: http://jubi.com/api/v1/ticker/?coin=btc
   *
   * @param baseCurrency Base currency
   * @return Data object for ticker
   * @throws java.io.IOException
   */
  @GET
  @Path("ticker/?coin={baseCurrency}")
  JubiTicker getTicker(@PathParam("baseCurrency") String baseCurrency) throws IOException;

  @GET
  @Path("allticker")
  Map<String, JubiTicker> getAllTicker() throws IOException;

  @GET
  @Path("orders/?coin={baseCurrency}")
  JubiTrade[] getTrades(@PathParam("baseCurrency") String baseCurrency) throws IOException;

  @GET
  @Path("orders/?coin={baseCurrency}&since={since}")
  JubiTrade[] getTradesSince(@PathParam("baseCurrency") String baseCurrency, @PathParam("since") Long since) throws IOException;
}
