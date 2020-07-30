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
import org.knowm.xchange.huobi.dto.account.results.HuobiDepositAddressV2Result;
import org.knowm.xchange.huobi.dto.account.results.HuobiDepositAddressWithTagResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiFeeRateResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiFundingHistoryResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiTransactFeeRateResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiWithdrawFeeRangeResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAllTickersResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetPairsResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetsResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiDepthResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiTickerResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiTradesResult;
import org.knowm.xchange.huobi.dto.trade.HuobiCreateOrderRequest;
import org.knowm.xchange.huobi.dto.trade.results.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiMatchesResult;
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
  @Path("v1/fee/fee-rate/get")
  HuobiFeeRateResult getFeeRate(
          @QueryParam("symbols") String symbols,
          @QueryParam("AccessKeyId") String apiKey,
          @QueryParam("SignatureMethod") String signatureMethod,
          @QueryParam("SignatureVersion") int signatureVersion,
          @QueryParam("Timestamp") String nonce,
          @QueryParam("Signature") ParamsDigest signature)
          throws IOException;

  @GET
  @Path("v2/reference/transact-fee-rate")
  HuobiTransactFeeRateResult getTransactFeeRate(
          @QueryParam("symbols") String symbols,
          @QueryParam("AccessKeyId") String apiKey,
          @QueryParam("SignatureMethod") String signatureMethod,
          @QueryParam("SignatureVersion") int signatureVersion,
          @QueryParam("Timestamp") String nonce,
          @QueryParam("Signature") ParamsDigest signature)
          throws IOException;


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
  @Path("v2/account/deposit/address")
  HuobiDepositAddressV2Result getDepositAddressV2(
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
  @Path("v1/order/openOrders")
  HuobiOrdersResult getOpenOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("states") String states,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/order/orders")
  HuobiOrdersResult getOrders(
      @QueryParam("symbol") String symbol,
      @QueryParam("states") String states,
      @QueryParam("start-time") Long startTime,
      @QueryParam("from") String from,
      @QueryParam("direct") String direct,
      @QueryParam("AccessKeyId") String apiKey,
      @QueryParam("SignatureMethod") String signatureMethod,
      @QueryParam("SignatureVersion") int signatureVersion,
      @QueryParam("Timestamp") String nonce,
      @QueryParam("Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("v1/order/history")
  HuobiOrdersResult getOrdersHistory(
      @QueryParam("symbol") String symbol,
      @QueryParam("start-time") Long startTime,
      @QueryParam("end-time") Long endTime,
      @QueryParam("direct") String direct,
      @QueryParam("size") Integer size,
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

  @GET
  @Path("v1/order/matchresults")
  HuobiMatchesResult getMatchResults(
      @QueryParam("symbol") String symbol,
      @QueryParam("types") String types,
      @QueryParam("start-date") String startDate,
      @QueryParam("end-date") String endDate,
      @QueryParam("from") String from,
      @QueryParam("direct") String direct,
      @QueryParam("size") Integer size,
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
