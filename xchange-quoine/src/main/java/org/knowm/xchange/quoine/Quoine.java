package org.knowm.xchange.quoine;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBook;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Quoine {

  @GET
  @Path("products/code/CASH/{currency_pair_code}")
  QuoineProduct getQuoineProduct(@PathParam("currency_pair_code") String currencyPairCode)
      throws IOException;

  @GET
  @Path("products")
  QuoineProduct[] getQuoineProducts() throws IOException;

  @GET
  @Path("products/{product_id}/price_levels?full=1")
  QuoineOrderBook getOrderBook(@PathParam("product_id") int currencyPairCode) throws IOException;
}
