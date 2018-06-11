package org.knowm.xchange.fcoin;

import java.math.BigDecimal;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public interface FCoin {

    @POST
    @Path("order")
    String placeOrder(
            @HeaderParam("api-key") String apiKey,
            @HeaderParam("api-expires") SynchronizedValueFactory<Long> nonce,
            @HeaderParam("api-signature") ParamsDigest paramsDigest,
            @FormParam("symbol") String symbol,
            @Nullable @FormParam("side") String side,
            @FormParam("orderQty") int orderQuantity,
            @FormParam("price") BigDecimal price,
            @Nullable @FormParam("stopPx") BigDecimal stopPrice,
            @Nullable @FormParam("ordType") String orderType,
            @Nullable @FormParam("clOrdID") String clOrdID,
            @Nullable @FormParam("execInst") String executionInstructions);

}
