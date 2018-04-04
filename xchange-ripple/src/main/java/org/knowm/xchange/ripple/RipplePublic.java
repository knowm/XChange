package org.knowm.xchange.ripple;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ripple.dto.RippleException;
import org.knowm.xchange.ripple.dto.account.RippleAccountBalances;
import org.knowm.xchange.ripple.dto.account.RippleAccountSettings;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrderBook;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrders;
import org.knowm.xchange.ripple.dto.trade.RippleNotifications;
import org.knowm.xchange.ripple.dto.trade.RippleOrderTransaction;
import org.knowm.xchange.ripple.dto.trade.RipplePaymentTransaction;
import org.knowm.xchange.ripple.dto.trade.RippleTransactionFee;

/**
 * Returns public information that is stored in the ledger - secret not needed. See
 * https://github.com/ripple/ripple-rest for up-to-date documentation.
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface RipplePublic {

  /** Returns the order book for this address and base/counter pair. */
  @GET
  @Path("accounts/{address}/order_book/{base}/{counter}")
  RippleOrderBook getOrderBook(
      @PathParam("address") final String address,
      @PathParam("base") final String base,
      @PathParam("counter") final String counter,
      @QueryParam("limit") final String limit)
      throws IOException, RippleException;

  /**
   * Returns the account balances for this address. This is public information in the ledger (secret
   * not needed).
   */
  @GET
  @Path("accounts/{address}/balances")
  RippleAccountBalances getAccountBalances(@PathParam("address") final String address)
      throws IOException, RippleException;

  /**
   * Returns the account settings for this address. This is public information in the ledger (secret
   * not needed).
   */
  @GET
  @Path("accounts/{address}/settings")
  RippleAccountSettings getAccountSettings(@PathParam("address") final String address)
      throws IOException, RippleException;

  /** Returns the account information for this address. */
  @GET
  @Path("accounts/{address}/orders")
  RippleAccountOrders openAccountOrders(@PathParam("address") final String address)
      throws IOException, RippleException;

  /** Returns detailed information about this order transaction. */
  @GET
  @Path("accounts/{address}/orders/{hash}")
  RippleOrderTransaction orderTransaction(
      @PathParam("address") final String address, @PathParam("hash") final String hash)
      throws IOException, RippleException;

  /** Returns detailed information about this payment transaction. */
  @GET
  @Path("accounts/{address}/payments/{hash}")
  RipplePaymentTransaction paymentTransaction(
      @PathParam("address") final String address, @PathParam("hash") final String hash)
      throws IOException, RippleException;

  /** Returns notifications for this address. */
  @GET
  @Path("accounts/{address}/notifications")
  RippleNotifications notifications(
      @PathParam("address") final String address,
      @QueryParam("exclude_failed") final Boolean excludeFailed,
      @QueryParam("earliest_first") final Boolean earliestFirst,
      @QueryParam("results_per_page") final Integer resultsPerPage,
      @QueryParam("page") final Integer page,
      @QueryParam("start_ledger") final Long startLedger,
      @QueryParam("end_ledger") final Long endLedger)
      throws IOException, RippleException;

  /** Fetch the network transaction fee. */
  @GET
  @Path("transaction-fee")
  RippleTransactionFee getTransactionFee();
}
