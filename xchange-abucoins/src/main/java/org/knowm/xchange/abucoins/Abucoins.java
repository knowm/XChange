package org.knowm.xchange.abucoins;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.abucoins.dto.AbucoinsServerTime;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsFullTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsHistoricRates;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProduct;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProductStats;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;

/** @author bryant_harris */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Abucoins {
  @GET
  @Path("time")
  AbucoinsServerTime getTime() throws IOException;

  @GET
  @Path("products")
  AbucoinsProduct[] getProducts() throws IOException;

  @GET
  @Path("products/{product-id}")
  AbucoinsProduct getProduct(@PathParam("product-id") String product_id) throws IOException;

  @GET
  @Path("products/{product-id}/book")
  AbucoinsOrderBook getBook(@PathParam("product-id") String product_id) throws IOException;

  @GET
  @Path("products/{product-id}/book?level={level}")
  AbucoinsOrderBook getBook(
      @PathParam("product-id") String product_id, @PathParam("level") String level)
      throws IOException;

  @GET
  @Path("products/ticker")
  AbucoinsFullTicker[] getTicker() throws IOException;

  @GET
  @Path("products/{product-id}/ticker")
  AbucoinsTicker getTicker(@PathParam("product-id") String product_id) throws IOException;

  @GET
  @Path("products/{product-id}/trades")
  AbucoinsTrade[] getTrades(@PathParam("product-id") String product_id) throws IOException;

  @GET
  @Path("products/{product-id}/trades?after={after}&limit={limit}")
  AbucoinsTrade[] getTradesAfter(
      @PathParam("product-id") String product_id,
      @PathParam("after") String after,
      @PathParam("limit") String limit)
      throws IOException;

  @GET
  @Path("products/{product-id}/trades?before={before}&limit={limit}")
  AbucoinsTrade[] getTradesBefore(
      @PathParam("product-id") String product_id,
      @PathParam("before") String before,
      @PathParam("limit") String limit)
      throws IOException;

  @GET
  @Path("products/{product-id}/candles?granularity={granularity}&start={start}&end={end}")
  AbucoinsHistoricRates getHistoricRates(
      @PathParam("product-id") String product_id,
      @PathParam("granularity") String granularity,
      @PathParam("start") String start,
      @PathParam("end") String end);

  @GET
  @Path("products/stats")
  AbucoinsProductStats getProductStats();
}
