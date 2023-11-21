package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.response.DepositAddressResponse;
import org.knowm.xchange.kucoin.dto.response.DepositResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api")
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
  @Path("/v1/deposits")
  KucoinResponse<Pagination<DepositResponse>> getDepositList(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("status") String status,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("currentPage") Integer currentPage,
      @QueryParam("pageSize") Integer pageSize)
      throws IOException;

  @POST
  @Path("/v1/deposit-addresses")
  KucoinResponse<DepositAddressResponse> createDepositAddress(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("chain") String chain)
      throws IOException;

  @GET
  @Path("/v1/deposit-addresses")
  KucoinResponse<DepositAddressResponse> getDepositAddress(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("chain") String chain)
      throws IOException;

  @GET
  @Path("/v2/deposit-addresses")
  KucoinResponse<List<DepositAddressResponse>> getDepositAddresses(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency)
      throws IOException;
}