package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.response.DepositResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1/deposits")
@Produces(MediaType.APPLICATION_JSON)
public interface DepositAPI {

  /**
   * Get Deposit List.
   *
   * @param currency string [optional] Currency
   * @param status string [optional] Status. Available value: ROCESSING, SUCCESS, and FAILURE
   * @param startAt long [optional] Start time. Unix timestamp calculated in milliseconds will
   *     return only items which were created after the start time.
   * @param endAt long [optional] End time. Unix timestamp calculated in milliseconds will return
   *     only items which were created before the end time.
   * @param pageSize The page size.
   * @param currentPage The page to select.
   * @return A page of orders.
   */
  @GET
  KucoinResponse<Pagination<DepositResponse>> getDepositList(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("status") String status,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt
      // @QueryParam("pageSize") int pageSize
      // @QueryParam("currentPage") int currentPage
      ) throws IOException;
}
