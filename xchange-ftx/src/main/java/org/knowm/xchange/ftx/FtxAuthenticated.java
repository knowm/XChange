package org.knowm.xchange.ftx;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.*;
import org.knowm.xchange.ftx.dto.trade.CancelAllFtxOrdersParams;
import org.knowm.xchange.ftx.dto.trade.FtxOrderDto;
import org.knowm.xchange.ftx.dto.trade.FtxOrderRequestPayload;
import org.knowm.xchange.ftx.dto.trade.FtxPositionDto;
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
      @HeaderParam("FTX-SIGN") ParamsDigest signature)
      throws IOException, FtxException;

  @GET
  @Path("/positions")
  FtxResponse<List<FtxPositionDto>> getFtxPositions(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount)
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
  @Path("/orders")
  FtxResponse<FtxOrderDto> placeOrder(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxOrderRequestPayload payload)
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
  @Path("/orders/history?market={market}")
  FtxResponse<List<FtxOrderDto>> orderHistory(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      @PathParam("market") String market)
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

  @POST
  @Path("/account/leverage")
  FtxResponse<FtxLeverageDto> changeLeverage(
      @HeaderParam("FTX-KEY") String apiKey,
      @HeaderParam("FTX-TS") Long nonce,
      @HeaderParam("FTX-SIGN") ParamsDigest signature,
      @HeaderParam("FTX-SUBACCOUNT") String subaccount,
      FtxLeverageDto leverage)
      throws IOException, FtxException;
}
