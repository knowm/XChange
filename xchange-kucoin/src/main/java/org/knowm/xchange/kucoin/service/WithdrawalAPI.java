package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.request.ApplyWithdrawApiRequest;
import org.knowm.xchange.kucoin.dto.response.ApplyWithdrawResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import org.knowm.xchange.kucoin.dto.response.WithdrawalResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1/withdrawals")
@Produces(MediaType.APPLICATION_JSON)
public interface WithdrawalAPI {

  /**
   * Apply withdraw.
   *
   * @see https://docs.kucoin.com/#apply-withdraw
   * @param req creation request
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  KucoinResponse<ApplyWithdrawResponse> applyWithdraw(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      ApplyWithdrawApiRequest req)
      throws IOException;

  /**
   * Get Withdrawals List.
   *
   * @param currency string [optional] Currency code
   * @param status string [optional] Status. Available value: PROCESSING, WALLET_PROCESSING,
   *     SUCCESS, and FAILURE
   * @param startAt long [optional] Start time. Unix timestamp calculated in milliseconds will
   *     return only items which were created after the start time.
   * @param endAt long [optional] End time. Unix timestamp calculated in milliseconds will return
   *     only items which were created before the end time.
   * @param pageSize The page size.
   * @param currentPage The page to select.
   * @return A page of orders.
   */
  @GET
  KucoinResponse<Pagination<WithdrawalResponse>> getWithdrawalsList(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("status") String status,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("pageSize") Integer pageSize,
      @QueryParam("currentPage") Integer currentPage)
      throws IOException;
}
