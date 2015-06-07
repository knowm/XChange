package com.xeiam.xchange.ripple;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.ripple.dto.RippleException;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;

/**
 * Returns public information that is stored in the ledger - secret not needed.
 * <p>
 * See https://github.com/ripple/ripple-rest for up-to-date documentation.
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface RipplePublic {

  /**
   * Returns the order book for this address and base/counter pair.
   */
  @GET
  @Path("accounts/{address}/order_book/{base}/{counter}")
  public RippleOrderBook getOrderBook(@PathParam("address") final String address, @PathParam("base") final String base,
      @PathParam("counter") final String counter, @QueryParam("limit") String limit) throws IOException, RippleException;

  /**
   * Returns the account information for this address. This is public information in the ledger (secret not needed).
   */
  @GET
  @Path("accounts/{address}/balances")
  public RippleAccount getAccounts(@PathParam("address") final String address) throws IOException, RippleException;

  /**
   * Returns the account information for this address.
   */
  @GET
  @Path("accounts/{address}/orders")
  public RippleAccount getOpenOrders(@PathParam("address") final String address) throws IOException, RippleException;
}
