package org.knowm.xchange.okcoin;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.okcoin.v3.dto.account.OkexDepositRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexFundingAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexSpotAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRequest;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalResponse;
import org.knowm.xchange.okcoin.v3.dto.marketdata.OkexTokenPairInformation;
import org.knowm.xchange.okcoin.v3.dto.trade.FundsTransferRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.FundsTransferResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexOpenOrder;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexTransaction;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderBatchCancellationRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderCancellationRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderCancellationResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderPlacementRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderPlacementResponse;
import org.knowm.xchange.okcoin.v3.service.OkexException;
import si.mazi.rescu.ParamsDigest;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface OkexV3 {

  public static final String OK_ACCESS_PASSPHRASE = "OK-ACCESS-PASSPHRASE";
  public static final String OK_ACCESS_KEY = "OK-ACCESS-KEY";
  public static final String OK_ACCESS_SIGN = "OK-ACCESS-SIGN";
  public static final String OK_ACCESS_TIMESTAMP = "OK-ACCESS-TIMESTAMP";

  /* Funding Account API */

  @GET
  @Path("/account/v3/wallet")
  List<OkexFundingAccountRecord> fundingAccountInformation(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase)
      throws IOException, OkexException;

  /**
   * @param currency required, Token
   * @param amount required, Transfer amount
   * @param from required, the remitting account (0: sub account 1: spot 3: futures 4:C2C 5: margin
   *     6: Funding Account 8:PiggyBank 9：swap)
   * @param to required, the beneficiary account(0: sub account 1:spot 3: futures 4:C2C 5: margin 6:
   *     Funding Account 8:PiggyBank 9 :swap)
   * @param subAccount optional, sub account name
   * @param instrumentid optional, margin token pair ID, for supported pairs only
   * @return
   * @throws IOException
   * @throws OkexException
   */
  @POST
  @Path("/account/v3/transfer")
  @Consumes(MediaType.APPLICATION_JSON)
  FundsTransferResponse fundsTransfer(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      FundsTransferRequest req)
      throws IOException, OkexException;

  @POST
  @Path("/account/v3/withdrawal")
  @Consumes(MediaType.APPLICATION_JSON)
  OkexWithdrawalResponse withdrawal(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      OkexWithdrawalRequest req)
      throws IOException, OkexException;

  @GET
  @Path("/account/v3/withdrawal/history")
  List<OkexWithdrawalRecord> recentWithdrawalHistory(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase)
      throws IOException, OkexException;

  @GET
  @Path("/account/v3/deposit/history")
  List<OkexDepositRecord> recentDepositHistory(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase)
      throws IOException, OkexException;

  /* Token Trading API */

  @GET
  @Path("/spot/v3/accounts")
  List<OkexSpotAccountRecord> spotTradingAccount(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase)
      throws IOException, OkexException;

  @POST
  @Path("/spot/v3/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  OrderPlacementResponse placeAnOrder(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      OrderPlacementRequest req)
      throws IOException, OkexException;

  @POST
  @Path("/spot/v3/batch_orders")
  @Consumes(MediaType.APPLICATION_JSON)
  Map<String, List<OrderPlacementResponse>> placeMultipleOrders(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      List<OrderPlacementRequest> req)
      throws IOException, OkexException;

  @POST
  @Path("/spot/v3/cancel_orders/{order_id}")
  @Consumes(MediaType.APPLICATION_JSON)
  OrderCancellationResponse cancelAnOrder(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      @PathParam("order_id") String orderId,
      OrderCancellationRequest req)
      throws IOException, OkexException;

  @POST
  @Path("/spot/v3/cancel_batch_orders")
  @Consumes(MediaType.APPLICATION_JSON)
  List<OrderCancellationResponse> cancelAllOrders(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      List<OrderBatchCancellationRequest> req)
      throws IOException, OkexException;

  /**
   * @param instrumentId [required] list the orders of specific trading pairs
   * @param from [optional] request page after this id (latest information) (eg. 1, 2, 3, 4, 5.
   *     There is only a 5 "from 4", while there are 1, 2, 3 "to 4")
   * @param to [optional] request page after (newer) this id.
   * @param limit [optional] number of results per request. Maximum 100. (default 100)
   * @param state [required ] Order Status("-2":Failed,"-1":Cancelled,"0":Open ,"1":Partially
   *     Filled, "2":Fully Filled,"3":Submitting,"4":Cancelling,"6": Incomplete（open+partially
   *     filled），"7":Complete（cancelled+fully filled））
   * @return
   * @throws IOException
   * @throws OkexException
   */
  @GET
  @Path("/spot/v3/orders")
  List<OkexOpenOrder> getOrderList(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      @QueryParam("instrument_id") String instrumentId,
      @QueryParam("from") String from,
      @QueryParam("to") String to,
      @QueryParam("limit") Integer limit,
      @QueryParam("state") String state)
      throws IOException, OkexException;

  /**
   * @param orderId required, order ID
   * @param instrumentId required, trading pair
   * @param from optional, Request page before (older) this pagination id,the parameter are
   *     order_id, ledger_id or trade_id of the endpoint, etc.
   * @param to optional, Request page after (newer) this pagination id,the parameter are order_id,
   *     ledger_id or trade_id of the endpoint, etc.
   * @param limit optional, Number of results per request. Maximum 100. (default 100)
   * @return
   * @throws IOException
   * @throws OkexException
   */
  @GET
  @Path("/spot/v3/fills")
  List<OkexTransaction> getTransactionDetails(
      @HeaderParam(OK_ACCESS_KEY) String apiKey,
      @HeaderParam(OK_ACCESS_SIGN) ParamsDigest signature,
      @HeaderParam(OK_ACCESS_TIMESTAMP) String timestamp,
      @HeaderParam(OK_ACCESS_PASSPHRASE) String passphrase,
      @QueryParam("order_id") String orderId,
      @QueryParam("instrument_id") String instrumentId,
      @QueryParam("from") String from,
      @QueryParam("to") String to,
      @QueryParam("limit") Integer limit)
      throws IOException, OkexException;

  @GET
  @Path("/spot/v3/instruments/ticker")
  List<OkexTokenPairInformation> getAllTokenPairInformations() throws IOException, OkexException;

  @GET
  @Path("/spot/v3/instruments/{instrument_id}/ticker")
  OkexTokenPairInformation getTokenPairInformation(@PathParam("instrument_id") String instrumentId)
      throws IOException, OkexException;
}
