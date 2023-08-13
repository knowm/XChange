package org.knowm.xchange.okex;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.okex.dto.OkexException;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.*;
import org.knowm.xchange.okex.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.dto.subaccount.OkexSubAccountDetails;
import org.knowm.xchange.okex.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexCancelOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.dto.trade.OkexOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexOrderResponse;
import si.mazi.rescu.ParamsDigest;

@Path("/api/v5")
@Produces(MediaType.APPLICATION_JSON)
public interface OkexAuthenticated extends Okex {
  String balancePath = "/account/balance"; // Stated as 10 req/2 sec
  String tradeFeePath = "/account/trade-fee"; // Stated as 5 req/2 sec
  String configPath = "/account/config"; // Stated as 5 req/2 sec
  String getBillsPath = "/account/bills"; // Stated as 6 req/sec
  String changeMarginPath = "/account/position/margin-balance"; // Stated as 20 req/2 sec
  String currenciesPath = "/asset/currencies"; // Stated as 6 req/sec
  String assetBalancesPath = "/asset/balances"; // Stated as 6 req/sec
  String assetWithdrawalPath = "/asset/withdrawal"; // Stated as 6 req/sec
  String positionsPath = "/account/positions"; // Stated as 10 req/2 sec
  String accountPositionAtRiskPath = "/account/account-position-risk"; // Stated as 10 req/2 sec
  String setLeveragePath = "/account/set-leverage"; // Stated as 20 req/2 sec
  String pendingOrdersPath = "/trade/orders-pending"; // Stated as 20 req/2 sec
  String orderDetailsPath = "/trade/order";
  String placeOrderPath = "/trade/order"; // Stated as 60 req/2 sec
  String placeBatchOrderPath = "/trade/batch-orders"; // Stated as 300 req/2 sec
  String cancelOrderPath = "/trade/cancel-order"; // Stated as 60 req/2 sec
  String cancelBatchOrderPath = "/trade/cancel-batch-orders"; // Stated as 300 req/2 sec
  String amendOrderPath = "/trade/amend-order"; // Stated as 60 req/2 sec
  String amendBatchOrderPath = "trade/amend-batch-orders"; // Stated as 300 req/2 sec
  String depositAddressPath = "/asset/deposit-address"; // Stated as 6 req/sec
  String ordersHistoryPath = "/trade/orders-history"; // Stated as 40 req/2 sec
  String subAccountList = "/users/subaccount/list"; // Stated as 2 req/2 sec
  String subAccountBalance = "/account/subaccount/balances"; // Stated as 2 req/2 sec
  String piggyBalance = "/asset/piggy-balance"; // Stated as 6 req/1 sec

  // To avoid 429s, actual req/second may need to be lowered!
  Map<String, List<Integer>> privatePathRateLimits =
      new HashMap<String, List<Integer>>() {
        {
          put(balancePath, Arrays.asList(5, 1));
          put(currenciesPath, Arrays.asList(6, 1));
          put(assetBalancesPath, Arrays.asList(6, 1));
          put(positionsPath, Arrays.asList(5, 1));
          put(setLeveragePath, Arrays.asList(20, 2));
          put(pendingOrdersPath, Arrays.asList(20, 2));
          put(orderDetailsPath, Arrays.asList(60, 2));
          put(placeOrderPath, Arrays.asList(60, 2));
          put(placeBatchOrderPath, Arrays.asList(300, 2));
          put(cancelOrderPath, Arrays.asList(60, 2));
          put(cancelBatchOrderPath, Arrays.asList(300, 2));
          put(amendOrderPath, Arrays.asList(60, 2));
          put(amendBatchOrderPath, Arrays.asList(300, 2));
          put(depositAddressPath, Arrays.asList(6, 1));
          put(ordersHistoryPath, Arrays.asList(40, 2));
          put(tradeFeePath, Arrays.asList(5, 2));
          put(configPath, Arrays.asList(5, 2));
          put(getBillsPath, Arrays.asList(6, 1));
          put(changeMarginPath, Arrays.asList(20, 2));
          put(subAccountList, Arrays.asList(2, 2));
          put(subAccountBalance, Arrays.asList(2, 2));
          put(piggyBalance, Arrays.asList(6, 1));
        }
      };

  @GET
  @Path(tradeFeePath)
  OkexResponse<List<OkexTradeFee>> getTradeFee(
      @QueryParam("instType") String instrumentType,
      @QueryParam("instId") String instrumentId,
      @QueryParam("uly") String underlying,
      @QueryParam("category") String category,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading);

  @GET
  @Path(configPath)
  OkexResponse<List<OkexAccountConfig>> getAccountConfiguration(
          @HeaderParam("OK-ACCESS-KEY") String apiKey,
          @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
          @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
          @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
          @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
          throws OkexException, IOException;

  @GET
  @Path(getBillsPath)
  OkexResponse<List<OkexBillDetails>> getBills(
          @QueryParam("instType") String instrumentType,
          @QueryParam("ccy") String currency,
          @QueryParam("mgnMode") String marginMode,
          @QueryParam("ctType") String contractType,
          @QueryParam("type") String billType,
          @QueryParam("subType") String billSubType,
          @QueryParam("after") String afterBillId,
          @QueryParam("before") String beforeBillId,
          @QueryParam("begin") String beginTimestamp,
          @QueryParam("end") String endTimestamp,
          @QueryParam("limit") String maxNumberOfResults,
          @HeaderParam("OK-ACCESS-KEY") String apiKey,
          @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
          @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
          @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
          @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
          throws OkexException, IOException;

  @POST
  @Path(changeMarginPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexChangeMarginResponse>> changeMargin(
          @HeaderParam("OK-ACCESS-KEY") String apiKey,
          @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
          @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
          @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
          @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
          OkexChangeMarginRequest requestPayload)
          throws OkexException, IOException;

  @GET
  @Path(ordersHistoryPath)
  OkexResponse<List<OkexOrderDetails>> getOrderHistory(
      @QueryParam("instType") String instType,
      @QueryParam("instId") String instrumentId,
      @QueryParam("ordType") String orderType,
      @QueryParam("state") String state,
      @QueryParam("after") String after,
      @QueryParam("before") String before,
      @QueryParam("limit") String limit,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading);

  @GET
  @Path(depositAddressPath)
  OkexResponse<List<OkexDepositAddress>> getDepositAddress(
      @QueryParam("ccy") String currency,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
      throws IOException, OkexException;

  @GET
  @Path(balancePath)
  OkexResponse<List<OkexWalletBalance>> getWalletBalances(
      @QueryParam("ccy") List<Currency> currencies,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
      throws IOException, OkexException;

  @GET
  @Path(currenciesPath)
  OkexResponse<List<OkexCurrency>> getCurrencies(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
      throws OkexException, IOException;

  @GET
  @Path(assetBalancesPath)
  OkexResponse<List<OkexAssetBalance>> getAssetBalances(
      @QueryParam("ccy") List<Currency> currencies,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
      throws OkexException, IOException;

  @POST
  @Path(assetWithdrawalPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexWithdrawalResponse>> assetWithdrawal(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      OkexWithdrawalRequest requestPayload)
      throws OkexException, IOException;

  @GET
  @Path(positionsPath)
  OkexResponse<List<OkexPosition>> getPositions(
          @QueryParam("instType") String instrumentType,
          @QueryParam("instId") String instrumentId,
          @QueryParam("posId") String positionId,
          @HeaderParam("OK-ACCESS-KEY") String apiKey,
          @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
          @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
          @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
          @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
  throws IOException, OkexException;

  @GET
  @Path(accountPositionAtRiskPath)
  OkexResponse<List<OkexAccountPositionRisk>> getAccountPositionRisk(
          @HeaderParam("OK-ACCESS-KEY") String apiKey,
          @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
          @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
          @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
          @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading)
          throws IOException, OkexException;

  @POST
  @Path(setLeveragePath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexSetLeverageResponse>> setLeverage(
          @HeaderParam("OK-ACCESS-KEY") String apiKey,
          @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
          @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
          @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
          @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
          OkexSetLeverageRequest requestPayload)
          throws IOException, OkexException;

  @GET
  @Path(pendingOrdersPath)
  OkexResponse<List<OkexOrderDetails>> getPendingOrders(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      @QueryParam("instType") String instrumentType,
      @QueryParam("uly") String underlying,
      @QueryParam("instId") String instrumentId,
      @QueryParam("ordType") String orderType,
      @QueryParam("state") String state,
      @QueryParam("after") String after,
      @QueryParam("before") String before,
      @QueryParam("limit") String limit)
      throws OkexException, IOException;

  @GET
  @Path(orderDetailsPath)
  OkexResponse<List<OkexOrderDetails>> getOrderDetails(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      @QueryParam("instId") String instrumentId,
      @QueryParam("ordId") String orderId,
      @QueryParam("clOrdId") String clientOrderId)
      throws OkexException, IOException;

  @GET
  @Path(subAccountList)
  OkexResponse<List<OkexSubAccountDetails>> getSubAccountList(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      @QueryParam("enable") String enable,
      @QueryParam("subAcct") String subAcct)
      throws OkexException, IOException;

  @GET
  @Path(subAccountBalance)
  OkexResponse<List<OkexWalletBalance>> getSubAccountBalance(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      @QueryParam("subAcct") String subAcct)
      throws OkexException, IOException;

  @GET
  @Path(piggyBalance)
  OkexResponse<List<PiggyBalance>> getPiggyBalance(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      @QueryParam("ccy") String ccy)
      throws OkexException, IOException;

  @POST
  @Path(placeOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> placeOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      OkexOrderRequest requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(placeBatchOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> placeBatchOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      List<OkexOrderRequest> requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(cancelOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> cancelOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      OkexCancelOrderRequest requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(cancelBatchOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> cancelBatchOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      List<OkexCancelOrderRequest> requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(amendOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> amendOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      OkexAmendOrderRequest requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(amendBatchOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> amendBatchOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("X-SIMULATED-TRADING") String simulatedTrading,
      List<OkexAmendOrderRequest> requestPayload)
      throws OkexException, IOException;
}