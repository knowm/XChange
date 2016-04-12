package org.knowm.xchange.therock;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.account.TheRockBalance;
import org.knowm.xchange.therock.dto.account.TheRockBalances;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawal;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.service.TheRockDigest;

import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TheRockAuthenticated {

  String X_TRT_NONCE = "X-TRT-NONCE";

  @POST
  @Path("funds/{fund_id}/orders")
  TheRockOrder placeOrder(@PathParam("fund_id") TheRock.Pair currencyPair, @HeaderParam("X-TRT-KEY") String publicKey,
      @HeaderParam("X-TRT-SIGN") TheRockDigest signer, @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory, TheRockOrder order)
      throws TheRockException, IOException;

  @POST
  @Path("atms/withdraw")
  TheRockWithdrawalResponse withdraw(@HeaderParam("X-TRT-KEY") String publicKey, @HeaderParam("X-TRT-SIGN") TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory, TheRockWithdrawal withdrawal) throws TheRockException, IOException;

  @GET
  @Path("balances/{currency}")
  TheRockBalance balance(@HeaderParam("X-TRT-KEY") String publicKey, @HeaderParam("X-TRT-SIGN") TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory, @PathParam("currency") String currency)
      throws TheRockException, IOException;

  @GET
  @Path("balances")
  TheRockBalances balances(@HeaderParam("X-TRT-KEY") String publicKey, @HeaderParam("X-TRT-SIGN") TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory) throws TheRockException, IOException;

}
