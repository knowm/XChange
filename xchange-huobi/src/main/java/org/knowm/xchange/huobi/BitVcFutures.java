package org.knowm.xchange.huobi;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.huobi.dto.account.BitVcFuturesAccountInfo;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPlaceOrderResult;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPosition;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPositionByContract;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;
import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitVcFutures {

    @GET
    @Path("ticker_{symbol}_{contract}.js")
    public BitVcFuturesTicker getTicker(@PathParam("symbol") String symbol, @PathParam("contract") String contract) throws IOException;

    @GET
    @Path("depths_{symbol}_{contract}.js")
    public BitVcFuturesDepth getDepths(@PathParam("symbol") String symbol, @PathParam("contract") String contract) throws IOException;

    @GET
    @Path("trades_{symbol}_{contract}.js")
    public BitVcFuturesTrade[] getTrades(@PathParam("symbol") String symbol, @PathParam("contract") String contract) throws IOException;

    /**
     * Non-XChange compatible methods
     */

    @GET
    @Path("exchange_rate.js")
    public BitVcExchangeRate getExchangeRate() throws IOException;

    @POST
    @Path("order/save")
    public BitVcFuturesPlaceOrderResult placeLimitOrder(
            @FormParam("accessKey") String accessKey,
            @FormParam("coinType") int coinType,
            @FormParam("contractType") String contractType,
            @FormParam("created") long created,
            @FormParam("sign") ParamsDigest sign,
            @FormParam("orderType") int orderType,
            @FormParam("tradeType") int tradeType,
            @FormParam("price") double price,
            @FormParam("money") double amount) throws IOException;

    @POST
    @Path("balance")
    public BitVcFuturesAccountInfo balance(
            @FormParam("accessKey") String accessKey,
            @FormParam("coinType") int coinType,
            @FormParam("created") long created,
            @FormParam("sign") ParamsDigest sign);

    @POST
    @Path("holdOrder/list")
    public BitVcFuturesPositionByContract positions(
            @FormParam("accessKey") String accessKey,
            @FormParam("coinType") int coinType,
            @FormParam("created") long created,
            @FormParam("sign") ParamsDigest sign);

}