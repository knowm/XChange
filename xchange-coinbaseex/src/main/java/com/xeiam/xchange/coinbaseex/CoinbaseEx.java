package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProduct;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yingzhe on 3/31/2015.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbaseEx {

  @GET
  @Path("products")
  List<CoinbaseExProduct> getProducts() throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/ticker")
  CoinbaseExProductTicker getProductTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/stats")
  CoinbaseExProductStats getProductStats(@PathParam("baseCurrency")String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/book")
  CoinbaseExProductBook getProductOrderBook(@PathParam("baseCurrency")String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;
}