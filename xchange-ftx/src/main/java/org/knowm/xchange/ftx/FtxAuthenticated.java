package org.knowm.xchange.ftx;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.FtxAccountDto;
import org.knowm.xchange.ftx.dto.account.FtxBorrowingHistoryDto;
import org.knowm.xchange.ftx.dto.account.FtxBorrowingInfoDto;
import org.knowm.xchange.ftx.dto.account.FtxBorrowingRatesDto;
import org.knowm.xchange.ftx.dto.account.FtxChangeSubAccountNamePOJO;
import org.knowm.xchange.ftx.dto.account.FtxConvertAcceptPayloadRequestDto;
import org.knowm.xchange.ftx.dto.account.FtxConvertAcceptRequestDto;
import org.knowm.xchange.ftx.dto.account.FtxConvertDto;
import org.knowm.xchange.ftx.dto.account.FtxConvertSimulatePayloadRequestDto;
import org.knowm.xchange.ftx.dto.account.FtxConvertSimulatetDto;
import org.knowm.xchange.ftx.dto.account.FtxFundingPaymentsDto;
import org.knowm.xchange.ftx.dto.account.FtxLendingHistoryDto;
import org.knowm.xchange.ftx.dto.account.FtxLendingInfoDto;
import org.knowm.xchange.ftx.dto.account.FtxLendingRatesDto;
import org.knowm.xchange.ftx.dto.account.FtxLeverageDto;
import org.knowm.xchange.ftx.dto.account.FtxPositionDto;
import org.knowm.xchange.ftx.dto.account.FtxSubAccountBalanceDto;
import org.knowm.xchange.ftx.dto.account.FtxSubAccountDto;
import org.knowm.xchange.ftx.dto.account.FtxSubAccountRequestPOJO;
import org.knowm.xchange.ftx.dto.account.FtxSubAccountTranferDto;
import org.knowm.xchange.ftx.dto.account.FtxSubAccountTransferPOJO;
import org.knowm.xchange.ftx.dto.account.FtxSubmitLendingOfferParams;
import org.knowm.xchange.ftx.dto.account.FtxWalletBalanceDto;
import org.knowm.xchange.ftx.dto.trade.*;
import si.mazi.rescu.ParamsDigest;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface FtxAuthenticated extends Ftx {

  @GET
  @Path("/account")
  FtxResponse<FtxAccountDto> getAccountInformation(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
      throws IOException, FtxException;

  @GET
  @Path("/wallet/balances")
  FtxResponse<List<FtxWalletBalanceDto>> getWalletBalances(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
      throws IOException, FtxException;

  @GET
  @Path("/positions")
  FtxResponse<List<FtxPositionDto>> getFtxPositions(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @QueryParam("showAvgPrice") boolean showAvgPrice)
      throws IOException, FtxException;

  @DELETE
  @Path("/subaccounts")
  FtxResponse deleteSubAccounts(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      FtxSubAccountRequestPOJO payload)
      throws IOException, FtxException;

  @GET
  @Path("/subaccounts")
  FtxResponse<List<FtxSubAccountDto>> getAllSubAccounts(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature)
      throws IOException, FtxException;

  @POST
  @Path("/subaccounts")
  FtxResponse<FtxSubAccountDto> createSubAccount(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxSubAccountRequestPOJO payload)
      throws IOException, FtxException;

  @POST
  @Path("/subaccounts/update_name")
  FtxResponse changeSubAccountName(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      FtxChangeSubAccountNamePOJO payload)
      throws IOException, FtxException;

  @GET
  @Path("/subaccounts/{nickname}/balances")
  FtxResponse<FtxSubAccountBalanceDto> getSubAccountBalances(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("nickname") String nickname)
      throws IOException, FtxException;

  @POST
  @Path("/subaccounts/transfer")
  FtxResponse<FtxSubAccountTranferDto> transferBetweenSubAccounts(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxSubAccountTransferPOJO payload)
      throws IOException, FtxException;

  @POST
  @Path("/otc/quotes")
  FtxResponse<FtxConvertSimulatetDto> simulateConvert(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxConvertSimulatePayloadRequestDto payload)
      throws IOException, FtxException;

  @GET
  @Path("/otc/quotes/{quoteId}")
  FtxResponse<FtxConvertDto> getConvertStatus(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("quoteId") String quoteId)
      throws IOException, FtxException;

  @POST
  @Path("/otc/quotes/{quoteId}/accept")
  FtxResponse<FtxConvertAcceptRequestDto> acceptConvert(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("quoteId") String quoteId,
      FtxConvertAcceptPayloadRequestDto payload)
      throws IOException, FtxException;

  @POST
  @Path("/orders")
  FtxResponse<FtxOrderDto> placeOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxOrderRequestPayload payload)
      throws IOException, FtxException;

  @POST
  @Path("/orders/{order_id}/modify")
  FtxResponse<FtxOrderDto> modifyOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("order_id") String orderId,
      FtxModifyOrderRequestPayload payload)
      throws IOException, FtxException;

  @POST
  @Path("/orders/by_client_id/{client_order_id}/modify")
  FtxResponse<FtxOrderDto> modifyOrderByClientId(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("client_order_id") String clientId,
      FtxModifyOrderRequestPayload payload)
      throws IOException, FtxException;

  @GET
  @Path("/orders/{order_id}")
  FtxResponse<FtxOrderDto> getOrderStatus(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("order_id") String orderId)
      throws IOException, FtxException;

  @GET
  @Path("/orders?market={market}")
  FtxResponse<List<FtxOrderDto>> openOrders(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("market") String market)
      throws IOException, FtxException;

  @GET
  @Path("/orders")
  FtxResponse<List<FtxOrderDto>> openOrdersWithoutMarket(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
      throws IOException, FtxException;

  @DELETE
  @Path("/orders/{orderId}")
  FtxResponse<String> cancelOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("orderId") String orderId)
      throws IOException, FtxException;

  @DELETE
  @Path("/orders")
  FtxResponse<String> cancelAllOrders(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      CancelAllFtxOrdersParams payLoad)
      throws IOException, FtxException;

  @GET
  @Path("/orders/history")
  FtxResponse<List<FtxOrderDto>> orderHistory(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @QueryParam("market") String market,
      @QueryParam("start_time") Long startTime,
      @QueryParam("end_time") Long endTime)
      throws IOException, FtxException;

  @DELETE
  @Path("/orders/by_client_id/{client_order_id}")
  FtxResponse<FtxOrderDto> cancelOrderByClientId(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("client_order_id") String clientOrderId)
      throws IOException, FtxException;

  @GET
  @Path("/fills")
  FtxResponse<List<FtxFillDto>> fills(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @QueryParam("market") String market,
      @QueryParam("start_time") Long startTime,
      @QueryParam("end_time") Long endTime)
      throws IOException, FtxException;

  @POST
  @Path("/account/leverage")
  FtxResponse<FtxLeverageDto> changeLeverage(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxLeverageDto leverage)
      throws IOException, FtxException;

  @GET
  @Path("/spot_margin/borrow_history")
  FtxResponse<List<FtxBorrowingHistoryDto>> getBorrowHistory(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @QueryParam("start_time") Long start_time,
      @QueryParam("end_time") Long end_time)
      throws IOException, FtxException;

  @GET
  @Path("/funding_payments")
  FtxResponse<List<FtxFundingPaymentsDto>> getFundingPayments(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @QueryParam("start_time") Long startTime,
      @QueryParam("end_time") Long endTime,
      @QueryParam("future") String future)
      throws IOException, FtxException;

  @GET
  @Path("/spot_margin/lending_info")
  FtxResponse<List<FtxLendingInfoDto>> getLendingInfos(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
      throws IOException, FtxException;

  @GET
  @Path("/spot_margin/lending_rates")
  FtxResponse<List<FtxLendingRatesDto>> getLendingRates(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature)
      throws IOException, FtxException;

  @GET
  @Path("/spot_margin/lending_history")
  FtxResponse<List<FtxLendingHistoryDto>> getlendingHistories(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
      throws IOException, FtxException;

  @POST
  @Path("/spot_margin/offers")
  FtxResponse submitLendingOffer(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxSubmitLendingOfferParams payload)
      throws IOException, FtxException;

  @GET
  @Path("/spot_margin/borrow_rates")
  FtxResponse<List<FtxBorrowingRatesDto>> getBorrowRates(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature)
      throws IOException, FtxException;

  @GET
  @Path("/spot_margin/borrow_info")
  FtxResponse<List<FtxBorrowingInfoDto>> getBorrowingInfos(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
      throws IOException, FtxException;

  @POST
  @Path("/conditional_orders")
  FtxResponse<FtxConditionalOrderDto> placeConditionalOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxConditionalOrderRequestPayload payload)
      throws IOException, FtxException;

  @POST
  @Path("/conditional_orders/{order_id}/modify")
  FtxResponse<FtxConditionalOrderDto> modifyConditionalOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("order_id") String orderId,
      FtxModifyConditionalOrderRequestPayload payload)
      throws IOException, FtxException;

  @DELETE
  @Path("/conditional_orders/{orderId}")
  FtxResponse<String> cancelConditionalOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("orderId") String orderId)
      throws IOException, FtxException;

  @GET
  @Path("/conditional_orders/history?market={market}")
  FtxResponse<List<FtxConditionalOrderDto>> conditionalOrderHistory(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("market") String market)
      throws IOException, FtxException;

  @GET
  @Path("/conditional_orders?market={market}")
  FtxResponse<List<FtxConditionalOrderDto>> openConditionalOrders(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("market") String market)
      throws IOException, FtxException;

  @GET
  @Path("/conditional_orders/{id}/triggers")
  FtxResponse<List<FtxTriggerDto>> getTriggers(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("id") String id)
      throws IOException, FtxException;
}
