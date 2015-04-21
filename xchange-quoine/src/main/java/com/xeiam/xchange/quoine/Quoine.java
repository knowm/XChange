package com.xeiam.xchange.quoine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.quoine.dto.marketdata.QuoineOrderBook;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineProduct;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Quoine {

  @GET
  @Path("products/code/CASH/{currency_pair_code}")
  QuoineProduct getQuoineProduct(@PathParam("currency_pair_code") String currencyPairCode);

  @GET
  @Path("products")
  QuoineProduct[] getQuoineProducts();

  @GET
  @Path("products/{product_id}/price_levels")
  QuoineOrderBook getOrderBook(@PathParam("product_id") int currencyPairCode);
}
