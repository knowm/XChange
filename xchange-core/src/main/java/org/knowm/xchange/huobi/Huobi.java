package org.knowm.xchange.huobi;

import org.knowm.xchange.huobi.dto.account.results.HuobiAccountResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiBalanceResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetPairsResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetsResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiTickerResult;
import org.knowm.xchange.huobi.dto.trade.HuobiCreateOrderRequest;
import org.knowm.xchange.huobi.dto.trade.results.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrderInfoResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrdersResult;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Huobi {

    @GET
    @Path("market/detail/merged")
    HuobiTickerResult getTicker(@QueryParam("symbol") String symbol) throws IOException;

    @GET
    @Path("v1/common/symbols")
    HuobiAssetPairsResult getAssetPairs() throws IOException;

    @GET
    @Path("v1/common/currencys")
    HuobiAssetsResult getAssets() throws IOException;

    @GET
    @Path("v1/account/accounts")
    HuobiAccountResult getAccount(@QueryParam("AccessKeyId") String apiKey,
                                  @QueryParam("SignatureMethod") String signatureMethod,
                                  @QueryParam("SignatureVersion") int signatureVersion,
                                  @QueryParam("Timestamp") String nonce,
                                  @QueryParam("Signature") ParamsDigest signature) throws IOException;

    @GET
    @Path("v1/account/accounts/{account-id}/balance")
    HuobiBalanceResult getBalance(@PathParam("account-id") String accountID,
                                  @QueryParam("AccessKeyId") String apiKey,
                                  @QueryParam("SignatureMethod") String signatureMethod,
                                  @QueryParam("SignatureVersion") int signatureVersion,
                                  @QueryParam("Timestamp") String nonce,
                                  @QueryParam("Signature") ParamsDigest signature) throws IOException;

    @GET
    @Path("v1/order/orders")
    HuobiOrdersResult getOpenOrders(@QueryParam("states") String states,
                                    @QueryParam("AccessKeyId") String apiKey,
                                    @QueryParam("SignatureMethod") String signatureMethod,
                                    @QueryParam("SignatureVersion") int signatureVersion,
                                    @QueryParam("Timestamp") String nonce,
                                    @QueryParam("Signature") ParamsDigest signature) throws IOException;

    @GET
    @Path("v1/order/orders/{order-id}")
    HuobiOrderInfoResult getOrder(@PathParam("order-id") String orderID,
                                  @QueryParam("AccessKeyId") String apiKey,
                                  @QueryParam("SignatureMethod") String signatureMethod,
                                  @QueryParam("SignatureVersion") int signatureVersion,
                                  @QueryParam("Timestamp") String nonce,
                                  @QueryParam("Signature") ParamsDigest signature) throws IOException;

    @POST
    @Path("v1/order/orders/{order-id}/submitcancel")
    HuobiCancelOrderResult cancelOrder(@PathParam("order-id") String orderID,
                                       @QueryParam("AccessKeyId") String apiKey,
                                       @QueryParam("SignatureMethod") String signatureMethod,
                                       @QueryParam("SignatureVersion") int signatureVersion,
                                       @QueryParam("Timestamp") String nonce,
                                       @QueryParam("Signature") ParamsDigest signature) throws IOException;

    @POST
    @Path("v1/order/orders/place")
    @Consumes(MediaType.APPLICATION_JSON)
    HuobiOrderResult placeLimitOrder(HuobiCreateOrderRequest body,
                                     @QueryParam("AccessKeyId") String apiKey,
                                     @QueryParam("SignatureMethod") String signatureMethod,
                                     @QueryParam("SignatureVersion") int signatureVersion,
                                     @QueryParam("Timestamp") String nonce,
                                     @QueryParam("Signature") ParamsDigest signature) throws IOException;

    @POST
    @Path("v1/order/orders/place")
    @Consumes(MediaType.APPLICATION_JSON)
    HuobiOrderResult placeMarketOrder(HuobiCreateOrderRequest body,
                                      @QueryParam("AccessKeyId") String apiKey,
                                      @QueryParam("SignatureMethod") String signatureMethod,
                                      @QueryParam("SignatureVersion") int signatureVersion,
                                      @QueryParam("Timestamp") String nonce,
                                      @QueryParam("Signature") ParamsDigest signature) throws IOException;

}
