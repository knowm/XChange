package org.knowm.xchange.abucoins;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProduct;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;

/**
 * @author bryant_harris
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Abucoins {
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
  AbucoinsOrderBook getBook(@PathParam("product-id") String product_id, @PathParam("level") String level) throws IOException;

  @GET
  @Path("products/{product-id}/ticker")
  AbucoinsTicker getTicker(@PathParam("product-id") String product_id) throws IOException;
}
