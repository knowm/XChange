package org.knowm.xchange.fcoin;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import javax.ws.rs.*;

public interface FCoin {

  @POST
  @Path("order")
  String placeOrder(
      @FormParam("symbol") String symbol,
      @Nullable @FormParam("side") String side,
      @FormParam("orderQty") int orderQuantity,
      @FormParam("price") BigDecimal price,
      @Nullable @FormParam("stopPx") BigDecimal stopPrice,
      @Nullable @FormParam("ordType") String orderType,
      @Nullable @FormParam("clOrdID") String clOrdID,
      @Nullable @FormParam("execInst") String executionInstructions);
}
