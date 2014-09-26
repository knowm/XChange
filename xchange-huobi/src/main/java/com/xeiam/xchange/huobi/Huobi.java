package com.xeiam.xchange.huobi;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.huobi.dto.account.polling.HuobiAccountInfo;
import com.xeiam.xchange.huobi.dto.marketdata.*;
import com.xeiam.xchange.huobi.dto.trade.polling.*;
import si.mazi.rescu.ParamsDigest;

@Path("staticmarket")
@Produces(MediaType.APPLICATION_JSON)
public interface Huobi {


    @GET
    @Path("ticker_{ident}_json.js")
    HuobiTickerWrapper getTicker(@PathParam("ident") String tradeableIdentifier) throws IOException;

    @GET
    @Path("depth_{ident}_json.js")
    HuobiDepthWrapper getDepth(@PathParam("ident") String tradeableIdentifier) throws IOException;


    @GET
    @Path("detail_{ident}_json.js")
    HuobiDetail getDetail(@PathParam("ident") String tradeableIdentifier) throws IOException;
}
