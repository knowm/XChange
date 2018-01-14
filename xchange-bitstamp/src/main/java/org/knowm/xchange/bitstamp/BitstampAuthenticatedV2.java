package org.knowm.xchange.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.BitstampTransferBalanceResponse;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticatedV2 {

  @POST
  @Path("open_orders/{pair}/")
  BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("pair") BitstampV2.Pair pair) throws BitstampException, IOException;

  @POST
  @Path("{side}/market/{pair}/")
  BitstampOrder placeMarketOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("side") Side side, @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("amount") BigDecimal amount) throws BitstampException, IOException;

  @POST
  @Path("{side}/{pair}/")
  BitstampOrder placeOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("side") Side side, @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("limit") Long numberOfTransactions, @FormParam("offset") Long offset,
      @FormParam("sort") String sort) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/{pair}/")
  BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("limit") Long numberOfTransactions, @FormParam("offset") Long offset,
      @FormParam("sort") String sort) throws BitstampException, IOException;

  enum Side {
    buy, sell
  }

  @POST
  @Path("xrp_withdrawal/")
  BitstampWithdrawal xrpWithdrawal(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String rippleAddress, @FormParam("destination_tag") String destinationTag) throws BitstampException, IOException;

  @POST
  @Path("ltc_withdrawal/")
  BitstampWithdrawal withdrawLitecoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws BitstampException, IOException;

  @POST
  @Path("bch_withdrawal/")
  BitstampWithdrawal bchWithdrawal(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws BitstampException, IOException;

  @POST
  @Path("eth_withdrawal/")
  BitstampWithdrawal withdrawEther(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws BitstampException, IOException;

  @POST
  @Path("transfer-to-main/")
  BitstampTransferBalanceResponse transferSubAccountBalanceToMain(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency, @FormParam("subAccount") String subAccount) throws BitstampException, IOException;

  @POST
  @Path("withdrawal-requests/")
  WithdrawalRequest[] getWithdrawalRequests(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("timedelta") Long timeDelta) throws BitstampException, IOException;

}
