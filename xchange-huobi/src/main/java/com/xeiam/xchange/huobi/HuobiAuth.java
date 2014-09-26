package com.xeiam.xchange.huobi;

import com.xeiam.xchange.huobi.dto.account.polling.HuobiAccountInfo;
import com.xeiam.xchange.huobi.dto.trade.polling.HuobiExecutionReport;
import com.xeiam.xchange.huobi.dto.trade.polling.HuobiOrder;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface HuobiAuth {

    @GET
    @FormParam("method")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HuobiAccountInfo get_account_info(@FormParam("access_key") String apiKey,
                                      @FormParam("created") long created,
                                      @FormParam("sign") ParamsDigest sign) throws IOException;

    @GET
    @FormParam("method")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HuobiOrder[] get_orders(@FormParam("access_key") String apiKey,
                            @FormParam("created") long created,
                            @FormParam("sign") ParamsDigest sign) throws IOException;

    @GET
    @FormParam("method")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HuobiOrder order_info(@FormParam("access_key") String apiKey,
                          @FormParam("created") long created,
                          @FormParam("id") String id,
                          @FormParam("sign") ParamsDigest sign) throws IOException;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HuobiExecutionReport createNewOrder(@FormParam("access_key") String apiKey,
                                        @FormParam("created") long created,
                                        @FormParam("price") BigDecimal price,
                                        @FormParam("amount") BigDecimal amount,
                                        @FormParam("method") String method,
                                        @FormParam("sign") ParamsDigest sign) throws IOException;

    @POST
    @FormParam("method")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HuobiExecutionReport modify_order(@FormParam("access_key") String apiKey,
                                      @FormParam("created") long created,
                                      @FormParam("id") String id,
                                      @FormParam("price") BigDecimal price,
                                      @FormParam("amount") BigDecimal amount,
                                      @FormParam("sign") ParamsDigest sign) throws IOException;

    @POST
    @FormParam("method")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HuobiExecutionReport cancel_order(@FormParam("access_key") String apiKey,
                                      @FormParam("created") long created,
                                      @FormParam("id") String id,
                                      @FormParam("sign") ParamsDigest sign) throws IOException;

}
