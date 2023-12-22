/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.kucoin.dto.request.CreateAccountRequest;
import org.knowm.xchange.kucoin.dto.request.InnerTransferRequest;
import org.knowm.xchange.kucoin.dto.response.AccountBalancesResponse;
import org.knowm.xchange.kucoin.dto.response.AccountLedgersResponse;
import org.knowm.xchange.kucoin.dto.response.AccountResponse;
import org.knowm.xchange.kucoin.dto.response.InternalTransferResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountAPI {

  /**
   * Get a list of accounts.
   *
   * <p>Your accounts are separate from your KuCoin accounts. See the Deposits section for
   * documentation on how to deposit funds to begin trading.
   *
   * @param currency The code of the currency
   * @param type Account type:，"main" or "trade"
   * @return The accounts.
   * @throws IOException on socket errors.
   * @throws KucoinApiException when errors are returned from the exchange.
   */
  @GET
  @Path("v1/accounts")
  KucoinResponse<List<AccountBalancesResponse>> getAccountList(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("type") String type)
      throws IOException;

  @POST
  @Path("v1/accounts")
  @Consumes(MediaType.APPLICATION_JSON)
  KucoinResponse<Void> createAccount(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      CreateAccountRequest req)
      throws IOException;

  @POST
  @Path("v2/accounts/inner-transfer")
  @Consumes(MediaType.APPLICATION_JSON)
  KucoinResponse<InternalTransferResponse> innerTransfer(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      InnerTransferRequest req)
      throws IOException;

  @GET
  @Path("v1/accounts/{accountId}/ledgers")
  KucoinResponse<Pagination<AccountLedgersResponse>> getAccountLedgers(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @PathParam("accountId") String accountId,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("pageSize") Integer pageSize,
      @QueryParam("currentPage") Integer currentPage)
      throws IOException;

  @GET
  @Path("v1/accounts/ledgers")
  KucoinResponse<Pagination<AccountLedgersResponse>> getAccountLedgersWithParams(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("currency") String currency,
      @QueryParam("direction") String direction,
      @QueryParam("bizType") String bizType,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("pageSize") Integer pageSize,
      @QueryParam("currentPage") Integer currentPage)
      throws IOException;

  @GET
  @Path("v1/accounts/{accountId}")
  KucoinResponse<AccountResponse> getAccount(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @PathParam("accountId") String accountId)
      throws IOException;
}
