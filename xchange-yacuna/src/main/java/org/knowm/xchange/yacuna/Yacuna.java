package org.knowm.xchange.yacuna;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.yacuna.dto.marketdata.YacunaTickerReturn;

/**
 * Created by Yingzhe on 12/23/2014.
 */
@Path("1")
@Produces(MediaType.APPLICATION_JSON)
public interface Yacuna {

  /**
   * Gets ticker from Yacuna for base/target currency pair URL: https://yacuna.com/api/1/market/list?currency1=EUR&currency2=XBT
   *
   * @param baseCurrency Base currency
   * @param targetCurrency Target currency
   * @return Data object for ticker
   * @throws java.io.IOException
   */
  @GET
  @Path("market/list?currency1={targetCurrency}&currency2={baseCurrency}")
  public YacunaTickerReturn getTicker(@PathParam("targetCurrency") String targetCurrency, @PathParam("baseCurrency") String baseCurrency)
      throws IOException;

  /**
   * Gets all tickers from Yacuna URL: https://yacuna.com/api/1/market/list
   *
   * @return Data object for ticker
   * @throws java.io.IOException
   */
  @GET
  @Path("market/list")
  public YacunaTickerReturn getTickers() throws IOException;
}