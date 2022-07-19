package org.knowm.xchange.ascendex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.account.*;
import org.knowm.xchange.ascendex.dto.balance.*;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;
import org.knowm.xchange.ascendex.dto.enums.AscendexTransactionType;
import org.knowm.xchange.ascendex.dto.trade.AscendexOpenOrdersResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexOrderResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexPlaceOrderRequestPayload;
import org.knowm.xchange.ascendex.dto.wallet.AscendDepositAddressesDto;
import org.knowm.xchange.ascendex.dto.wallet.AscendexWalletTransactionHistoryDto;
import si.mazi.rescu.ParamsDigest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IAscendexAuthenticated extends IAscendex {
  /**=========================Account======================================**/
  @GET
  @Path("api/pro/v1/info")
  AscendexResponse<AscendexAccountInfoDto> getAscendexAccountInfo(

          @HeaderParam("x-auth-key") String apiKey,
          @HeaderParam("x-auth-timestamp") Long nonce,
          @HeaderParam("x-auth-signature") ParamsDigest signature)
          throws IOException;

 @GET
 @Path("{account-group}/api/pro/v1/spot/fee/info")
 AscendexResponse<AscendexVIPFeeScheduleDto> getAscendexVIPFeeSchedule(
         @PathParam("account-group") String accountGroup,
         @HeaderParam("x-auth-key") String apiKey,
         @HeaderParam("x-auth-timestamp") Long nonce,
         @HeaderParam("x-auth-signature") ParamsDigest signature)
         throws IOException;

 @GET
 @Path("{account-group}/api/pro/v1/spot/fee")
 AscendexResponse<AscendexSymbolFeeDto> getAscendexSymbolFeeSchedule(
         @PathParam("account-group") String accgruop,
         @HeaderParam("x-auth-key") String apiKey,
         @HeaderParam("x-auth-timestamp") Long nonce,
         @HeaderParam("x-auth-signature") ParamsDigest signature)
         throws IOException;


 @GET
 @Path("/api/pro/v2/risk-limit-info")
 AscendexResponse<AscendexRiskLimitInfoDto> getAscendexRiskLimitInfo()
         throws IOException;


 @GET
 @Path("/api/pro/v1/exchange-info")
 AscendexResponse<AscendexExchangeLatencyInfoDto> getAscendexExchangeLatencyInfo(
         @QueryParam("requestTime") Long requestTime
 )
         throws IOException;

 /**=========================Balance======================================**/

  @GET
  @Path("{account-group}/api/pro/v1/cash/balance")
  AscendexResponse<List<AscendexCashAccountBalanceDto>> getAscendexCashAccountBalance(
          @QueryParam("asset") String asset,
      @QueryParam("showAll") Boolean showAll,
      @PathParam("account-group") String accountGroup,
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature)
      throws IOException;

 @GET
 @Path("{account-group}/api/pro/v1/margin/balance")
 AscendexResponse<List<AscendexMarginAccountBalanceDto>> getAscendexMarginAccountBalance(
         @QueryParam("asset") String asset,
         @QueryParam("showAll") Boolean showAll,
         @PathParam("account-group") String accountGroup,
         @HeaderParam("x-auth-key") String apiKey,
         @HeaderParam("x-auth-timestamp") Long nonce,
         @HeaderParam("x-auth-signature") ParamsDigest signature)
         throws IOException;

 @GET
 @Path("{account-group}/api/pro/v1/margin/risk")
 AscendexResponse<AscendexMarginRiskDto> getAscendexMarginRisk(
         @PathParam("account-group") String accountGroup,
         @HeaderParam("x-auth-key") String apiKey,
         @HeaderParam("x-auth-timestamp") Long nonce,
         @HeaderParam("x-auth-signature") ParamsDigest signature)
         throws IOException;

 @POST
 @Path("{account-group}/api/pro/v1/transfer")
 AscendexResponse<Void> getAscendexBalanceTransfer(
         AscendexBalanceTransferRequestPayload payload,
         @PathParam("account-group") String accountGroup,
         @HeaderParam("x-auth-key") String apiKey,
         @HeaderParam("x-auth-timestamp") Long nonce,
         @HeaderParam("x-auth-signature") ParamsDigest signature)
         throws IOException;

// TODO Balance Transfer for Subaccount
// TODO Balance Transfer history for Subaccount


    /**=========================Balance Snapshot And Update Detail======================================**/
@GET
@Path("api/pro/data/v1/{type}/balance/snapshot")
AscendexBalanceSnapshotDto getAscendexBalanceSnapshot(
        @QueryParam("date")String date,
  @PathParam("type") AccountCategory AccountCategory,
  @HeaderParam("x-auth-key") String apiKey,
  @HeaderParam("x-auth-timestamp") Long nonce,
  @HeaderParam("x-auth-signature") ParamsDigest signature)throws IOException;


    @GET
    @Path("api/pro/data/v1/{type}/balance/history")
    AscendexOrderAndBalanceDetailDto getAscendexOrderAndBalanceDetail(
            @QueryParam("date")String date,
            @PathParam("type") AccountCategory AccountCategory,
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature)throws IOException;

    /**=========================Wallet======================================**/
    @GET
    @Path("api/pro/v1/wallet/deposit/address")
    AscendexResponse<AscendDepositAddressesDto> getAscendexDepositAddresses(
            @QueryParam("asset")String asset,
            @QueryParam("blockchain")String blockchain,
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature)throws IOException;

    @GET
    @Path("api/pro/v1/wallet/transactions")
    AscendexResponse<AscendexWalletTransactionHistoryDto> getAscendexWalletTransactionHistory(
            @QueryParam("asset") String asset,
            @QueryParam("txType") AscendexTransactionType txType,
            @QueryParam("page") Integer page,
            @QueryParam("pageSize") Integer pageSize,
            @HeaderParam("x-auth-key") String apiKey,
            @HeaderParam("x-auth-timestamp") Long nonce,
            @HeaderParam("x-auth-signature") ParamsDigest signature)throws IOException;
  @POST
  @Path("api/pro/v1/{account-category}/order")
  AscendexResponse<AscendexOrderResponse> placeOrder(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      AscendexPlaceOrderRequestPayload payload)
      throws IOException;

  @DELETE
  @Path("api/pro/v1/{account-category}/order")
  AscendexResponse<AscendexOrderResponse> cancelOrder(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("orderId") String orderId,
      @QueryParam("symbol") String symbol,
      @QueryParam("time") Long time)
      throws IOException;

  @DELETE
  @Path("api/pro/v1/{account-category}/order/all")
  AscendexResponse<AscendexOrderResponse> cancelAllOrders(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("api/pro/v1/{account-category}/order/open")
  AscendexResponse<List<AscendexOpenOrdersResponse>> getOpenOrders(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("api/pro/v1/{account-category}/order/status")
  AscendexResponse<AscendexOpenOrdersResponse> getOrderById(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("orderId") String orderId)
      throws IOException;

  @GET
  @Path("api/pro/v1/{account-category}/order/hist/current")
  AscendexResponse<List<AscendexOpenOrdersResponse>> getOrdersHistory(
      @HeaderParam("x-auth-key") String apiKey,
      @HeaderParam("x-auth-timestamp") Long nonce,
      @HeaderParam("x-auth-signature") ParamsDigest signature,
      @PathParam("account-category") String accountCategory,
      @QueryParam("n") int numberOfRecords,
      @QueryParam("symbol") String symbol,
      @QueryParam("executedOnly") boolean executedOnly)
      throws IOException;
}
