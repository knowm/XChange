package org.knowm.xchange.huobi;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.huobi.dto.account.HuobiCreateWithdrawRequest;
import org.knowm.xchange.huobi.dto.account.results.HuobiAccountResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiBalanceResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiCreateWithdrawResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiDepositAddressResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiDepositAddressWithTagResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiFundingHistoryResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiWithdrawFeeRangeResult;
import org.knowm.xchange.huobi.dto.marketdata.results.*;
import org.knowm.xchange.huobi.dto.trade.HuobiCreateOrderRequest;
import org.knowm.xchange.huobi.dto.trade.results.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrderInfoResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrdersResult;
import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Huobi {

  @GET
  @Path("market/detail/merged")
  HuobiTickerResult getTicker(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("market/tickers")
  HuobiAllTickersResult getAllTickers() throws IOException;

  @GET
  @Path("market/depth")
  HuobiDepthResult getDepth(@QueryParam("symbol") String symbol, @QueryParam("type") String type)
      throws IOException;

  @GET
  @Path("market/history/trade")
  HuobiTradesResult getTrades(@QueryParam("symbol") String symbol, @QueryParam("size") int size)
      throws IOException;

  @GET
  @Path("v1/common/symbols")
  HuobiAssetPairsResult getAssetPairs() throws IOException;

  @GET
  @Path("v1/common/currencys")
  HuobiAssetsResult getAssets() throws IOException;

  @GET
  @Path("v1/dw/deposit-virtual/addresses")
  HuobiDepositAddressResult getDepositAddress(
      @QueryParam("currency") String currency,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/query/deposit-withdraw")
  HuobiFundingHistoryResult getFundingHistory(
      @QueryParam("currency") String currency,
      @QueryParam("type") String type,
      @QueryParam("from") String from,
      @QueryParam("size") String size,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/dw/deposit-virtual/sharedAddressWithTag")
  HuobiDepositAddressWithTagResult getDepositAddressWithTag(
      @QueryParam("currency") String currency,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("v1/dw/withdraw/api/create")
  @Consumes(MediaType.APPLICATION_JSON)
  HuobiCreateWithdrawResult createWithdraw(
      HuobiCreateWithdrawRequest body,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/dw/withdraw-virtual/fee-range")
  @Consumes(MediaType.APPLICATION_JSON)
  HuobiWithdrawFeeRangeResult getWithdrawFeeRange(
      @QueryParam("currency") String currency,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/account/accounts")
  HuobiAccountResult getAccount(
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/account/accounts/{account-id}/balance")
  HuobiBalanceResult getBalance(
      @PathParam("account-id") String accountID,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/order/orders")
  HuobiOrdersResult getOpenOrders(
      @QueryParam("states") String states,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/order/orders/{order-id}")
  HuobiOrderInfoResult getOrder(
      @PathParam("order-id") String orderID,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("v1/order/orders/{order-id}/submitcancel")
  HuobiCancelOrderResult cancelOrder(
      @PathParam("order-id") String orderID,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("v1/order/orders/place")
  @Consumes(MediaType.APPLICATION_JSON)
  HuobiOrderResult placeLimitOrder(
      HuobiCreateOrderRequest body,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("v1/order/orders/place")
  @Consumes(MediaType.APPLICATION_JSON)
  HuobiOrderResult placeMarketOrder(
      HuobiCreateOrderRequest body,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;
}
