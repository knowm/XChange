package org.knowm.xchange.bitso;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bitso.dto.BitsoBaseResponse;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.funding.*;
import org.knowm.xchange.bitso.service.BitsoDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Bitso Funding API for deposits and withdrawals
 *
 * @see <a href="https://docs.bitso.com/bitso-payouts-funding/docs/getting-started">Bitso Funding
 *     API Documentation</a>
 */
@Path("/api/v3")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BitsoFundingAuthenticated {
  /**
   * List funding transactions (deposits)
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param currency Filter by currency (optional)
   * @param limit Number of results to return (optional, max 100)
   * @param marker Marker for pagination (optional)
   * @param sort Sort order (asc, desc) (optional)
   * @return List of funding transactions
   * @throws BitsoException API exception
   * @throws IOException Network exception
   */
  @GET
  @Path("/fundings")
  BitsoBaseResponse<BitsoFunding[]> listFundings(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("marker") String marker,
      @QueryParam("sort") String sort)
      throws BitsoException, IOException;

  // ==========================================================================
  // WITHDRAWAL / PAYOUT ENDPOINTS
  // ==========================================================================

  /**
   * List withdrawal transactions
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param currency Filter by currency (optional)
   * @param limit Number of results to return (optional, max 100)
   * @param marker Marker for pagination (optional)
   * @param sort Sort order (asc, desc) (optional)
   * @return List of withdrawal transactions
   * @throws BitsoException API exception
   * @throws IOException Network exception
   */
  @GET
  @Path("/withdrawals")
  BitsoBaseResponse<BitsoWithdrawal[]> listWithdrawals(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency,
      @QueryParam("limit") Integer limit,
      @QueryParam("marker") String marker,
      @QueryParam("sort") String sort)
      throws BitsoException, IOException;

  /**
   * Create a cryptocurrency withdrawal
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param withdrawalRequest Withdrawal request details
   * @return Withdrawal response with transaction ID
   * @throws BitsoException API exception
   * @throws IOException Network exception
   */
  @POST
  @Path("/withdrawals")
  BitsoBaseResponse<BitsoWithdrawal> createCryptoWithdrawal(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      BitsoWithdrawalRequest withdrawalRequest)
      throws BitsoException, IOException;

  /**
   * Create a fiat withdrawal
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param withdrawalRequest Withdrawal request details
   * @return Withdrawal response with transaction ID
   * @throws BitsoException API exception
   * @throws IOException Network exception
   */
  @POST
  @Path("/withdrawals")
  BitsoBaseResponse<BitsoWithdrawal> createFiatWithdrawal(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      BitsoWithdrawalRequest withdrawalRequest)
      throws BitsoException, IOException;

  /**
   * Get withdrawal methods for a specific currency
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param currencyTicker Currency ticker (btc, eth, mxn, cop, etc.)
   * @return Withdrawal methods configuration for the currency
   * @throws BitsoException API exception
   * @throws IOException Network exception
   */
  @GET
  @Path("/withdrawal_methods/{currency_ticker}")
  BitsoBaseResponse<BitsoWithdrawalMethod[]> getWithdrawalMethods(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currency_ticker") String currencyTicker)
      throws BitsoException, IOException;

  /**
   * List receiving accounts
   *
   * @param signer Authentication signature
   * @param nonce Request nonce
   * @param currency Filter by currency (optional)
   * @return List of receiving accounts
   * @throws BitsoException API exception
   * @throws IOException Network exception
   */
  @GET
  @Path("/consumer-contacts")
  BitsoBaseResponse<BitsoReceivingAccount[]> listReceivingAccounts(
      @HeaderParam("Authorization") BitsoDigest signer,
      @HeaderParam("Bitso-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency)
      throws BitsoException, IOException;
}
