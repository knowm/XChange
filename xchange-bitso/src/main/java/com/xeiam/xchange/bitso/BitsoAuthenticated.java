package com.xeiam.xchange.bitso;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.bitso.dto.BitsoException;
import com.xeiam.xchange.bitso.dto.account.BitsoBalance;
import com.xeiam.xchange.bitso.dto.account.BitsoDepositAddress;
import com.xeiam.xchange.bitso.service.BitsoDigest;

/**
 * @author Benedikt Bünz See https://www.bitso.net/api/ for up-to-date docs.
 */
@Path("v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitsoAuthenticated {

  @POST
  @Path("balance/")
  BitsoBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") BitsoDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitsoException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  BitsoDepositAddress getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") BitsoDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitsoException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  String withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") BitsoDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
                                  @FormParam("address") String address) throws BitsoException, IOException;

  @POST
  @Path("ripple_withdrawal/")
  String withdrawToRipple(@FormParam("key") String apiKey, @FormParam("signature") BitsoDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                           @FormParam("amount") BigDecimal amount, @FormParam("currency") String currency, @FormParam("address") String rippleAddress)
      throws BitsoException, IOException;

}
