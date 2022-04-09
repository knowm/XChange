package org.knowm.xchange.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.BitstampTransferBalanceResponse;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampRippleDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.DepositTransaction;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.dto.trade.BitstampCancelAllOrdersResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderCancelResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticatedV2 {

  @POST
  @Path("open_orders/all/")
  BitstampOrder[] getOpenOrders(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("open_orders/{pair}/")
  BitstampOrder[] getOpenOrders(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @PathParam("pair") BitstampV2.Pair pair)
      throws BitstampException, IOException;

  @POST
  @Path("{side}/market/{pair}/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampOrder placeMarketOrder(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @PathParam("side") Side side,
      @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("amount") BigDecimal amount)
      throws BitstampException, IOException;

  @POST
  @Path("{side}/{pair}/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampOrder placeOrder(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @PathParam("side") Side side,
      @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampOrderCancelResponse cancelOrder(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("id") long orderId)
      throws BitstampException, IOException;

  @POST
  @Path("cancel_all_orders/")
  BitstampCancelAllOrdersResponse cancelAllOrders(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("order_status/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampOrderStatusResponse getOrderStatus(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("id") long orderId)
      throws BitstampException, IOException;

  @POST
  @Path("balance/")
  BitstampBalance getBalance(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampUserTransaction[] getUserTransactions(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("limit") Long numberOfTransactions,
      @FormParam("offset") Long offset,
      @FormParam("sort") String sort,
      @FormParam("since_timestamp") Long sinceTimestamp,
      @FormParam("since_id") String sinceId)
      throws BitstampException, IOException;

  @POST
  @Path("user_transactions/{pair}/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampUserTransaction[] getUserTransactions(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @PathParam("pair") BitstampV2.Pair pair,
      @FormParam("limit") Long numberOfTransactions,
      @FormParam("offset") Long offset,
      @FormParam("sort") String sort,
      @FormParam("since_timestamp") Long sinceTimestamp,
      @FormParam("since_id") String sinceId)
      throws BitstampException, IOException;

  /**
   * please keep in mind that the methods below are called through reflections with naming pattern
   * "withdraw" + Currency code
   */
  @POST
  @Path("btc_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawBTC(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("xrp_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawXRP(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String rippleAddress,
      @FormParam("destination_tag") Long destinationTag)
      throws BitstampException, IOException;

  @POST
  @Path("ltc_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawLTC(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("bch_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawBCH(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("eth_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawETH(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("xlm_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawXLM(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address,
      @FormParam("memo_id") Long memo)
      throws BitstampException, IOException;

  @POST
  @Path("pax_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawPAX(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("link_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawLINK(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("omg_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawOMG(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("usdc_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawUSDC(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("aave_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawAAVE(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("bat_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawBAT(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("uma_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawUMA(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("dai_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawDAI(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("knc_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawKNC(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("mkr_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawMKR(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("zrx_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawZRX(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("gusd_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawGUSD(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("algo_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawALGO(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("audio_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawAUDIO(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("crv_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawCRV(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("snx_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawSNX(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("uni_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawUNI(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("yfi_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawYFI(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("COMP_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawCOMP(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("grt_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawGRT(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("usdt_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawUSDT(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("eurt_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawEURT(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("matic_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawMATIC(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("sushi_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawSUSHI(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("chz_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawCHZ(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("enj_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawENJ(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("alpha_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawALPHA(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("ftt_withdrawal/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal withdrawFTT(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("transfer-to-main/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampTransferBalanceResponse transferSubAccountBalanceToMain(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("subAccount") String subAccount)
      throws BitstampException, IOException;

  @POST
  @Path("withdrawal-requests/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  WithdrawalRequest[] getWithdrawalRequests(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("timedelta") Long timeDelta)
      throws BitstampException, IOException;

  @POST
  @Path("withdrawal/open/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BitstampWithdrawal bankWithdrawal(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("account_currency") AccountCurrency accountCurrency,
      @FormParam("name") String name,
      @FormParam("iban") String IBAN,
      @FormParam("bic") String BIK,
      @FormParam("address") String address,
      @FormParam("postal_code") String postalCode,
      @FormParam("city") String city,
      @FormParam("country") String countryAlpha2,
      @FormParam("type") BankWithdrawalType type,
      @FormParam("bank_name") String bankName,
      @FormParam("bank_address") String bankAddress,
      @FormParam("bank_postal_code") String bankPostalCode,
      @FormParam("bank_city") String bankCity,
      @FormParam("bank_country") String bankCountryAlpha2,
      @FormParam("currency") BankCurrency currency,
      @FormParam("comment") String comment)
      throws BitstampException, IOException;

  @POST
  @Path("btc_address/")
  BitstampDepositAddress getBitcoinDepositAddress(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("ltc_address/")
  BitstampDepositAddress getLitecoinDepositAddress(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("bch_address/")
  BitstampDepositAddress getBitcoinCashDepositAddress(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("eth_address/")
  BitstampDepositAddress getEthereumDepositAddress(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("xrp_address/")
  BitstampRippleDepositAddress getXRPDepositAddress(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("ripple_address/")
  BitstampRippleDepositAddress getRippleIOUDepositAddress(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  @POST
  @Path("ripple_withdrawal/")
  boolean withdrawToRipple(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String rippleAddress,
      @FormParam("currency") String currency)
      throws BitstampException, IOException;

  @POST
  @Path("btc_unconfirmed/")
  DepositTransaction[] getUnconfirmedBTCDeposits(
      @HeaderParam("X-Auth") String apiKey,
      @HeaderParam("X-Auth-Signature") ParamsDigest signer,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<String> nonce,
      @HeaderParam("X-Auth-Timestamp") SynchronizedValueFactory<String> timeStamp,
      @HeaderParam("X-Auth-Version") String version)
      throws BitstampException, IOException;

  enum Side {
    buy,
    sell
  }

  enum AccountCurrency {
    USD,
    EUR
  }

  enum BankWithdrawalType {
    sepa,
    international
  }

  enum Country {
    Afghanistan("AF"),
    Åland_Islands("AX"),
    Albania("AL"),
    Algeria("DZ"),
    American_Samoa("AS"),
    Andorra("AD"),
    Angola("AO"),
    Anguilla("AI"),
    Antarctica("AQ"),
    Antigua_and_Barbuda("AG"),
    Argentina("AR"),
    Armenia("AM"),
    Aruba("AW"),
    Australia("AU"),
    Austria("AT"),
    Azerbaijan("AZ"),
    Bahamas("BS"),
    Bahrain("BH"),
    Bangladesh("BD"),
    Barbados("BB"),
    Belarus("BY"),
    Belgium("BE"),
    Belize("BZ"),
    Benin("BJ"),
    Bermuda("BM"),
    Bhutan("BT"),
    Bolivia("BO"),
    Bosnia_and_Herzegovina("BA"),
    Botswana("BW"),
    Bouvet_Island("BV"),
    Brazil("BR"),
    Brunei_Darussalam("BN"),
    Bulgaria("BG"),
    Burkina_Faso("BF"),
    Burundi("BI"),
    Cabo_Verde("CV"),
    Cambodia("KH"),
    Cameroon("CM"),
    Canada("CA"),
    Cayman_Islands("KY"),
    Central_African_Republic("CF"),
    Chad("TD"),
    Chile("CL"),
    China("CN"),
    Christmas_Island("CX"),
    Cocos_Islands("CC"),
    Colombia("CO"),
    Comoros("KM"),
    Cook_Islands("CK"),
    Costa_Rica("CR"),
    Cote_dIvoire("CI"),
    Croatia("HR"),
    Cuba("CU"),
    Curacao("CW"),
    Cyprus("CY"),
    Czechia("CZ"),
    Denmark("DK"),
    Djibouti("DJ"),
    Dominica("DM"),
    Dominican_Republic("DO"),
    Ecuador("EC"),
    Egypt("EG"),
    El_Salvador("SV"),
    Equatorial_Guinea("GQ"),
    Eritrea("ER"),
    Estonia("EE"),
    Ethiopia("ET"),
    Falkland_Islands("FK"),
    Faroe_Islands("FO"),
    Fiji("FJ"),
    Finland("FI"),
    France("FR"),
    French_Guiana("GF"),
    French_Polynesia("PF"),
    French_Southern_Territories("TF"),
    Gabon("GA"),
    Gambia("GM"),
    Georgia("GE"),
    Germany("DE"),
    Ghana("GH"),
    Gibraltar("GI"),
    Greece("GR"),
    Greenland("GL"),
    Grenada("GD"),
    Guadeloupe("GP"),
    Guam("GU"),
    Guatemala("GT"),
    Guernsey("GG"),
    Guinea("GN"),
    Guinea_Bissau("GW"),
    Guyana("GY"),
    Haiti("HT"),
    Holy_See("VA"),
    Honduras("HN"),
    Hong_Kong("HK"),
    Hungary("HU"),
    Iceland("IS"),
    India("IN"),
    Indonesia("ID"),
    Iran("IR"),
    Iraq("IQ"),
    Ireland("IE"),
    Isle_of_Man("IM"),
    Israel("IL"),
    Italy("IT"),
    Jamaica("JM"),
    Japan("JP"),
    Jersey("JE"),
    Jordan("JO"),
    Kazakhstan("KZ"),
    Kenya("KE"),
    Kiribati("KI"),
    Korea_South("KP"),
    Kuwait("KW"),
    Kyrgyzstan("KG"),
    Lao("LA"),
    Latvia("LV"),
    Lebanon("LB"),
    Lesotho("LS"),
    Liberia("LR"),
    Libya("LY"),
    Liechtenstein("LI"),
    Lithuania("LT"),
    Luxembourg("LU"),
    Macao("MO"),
    Macedonia("MK"),
    Malaysia("MY"),
    Maldives("MV"),
    Mali("ML"),
    Malta("MT"),
    Marshall_Islands("MH"),
    Martinique("MQ"),
    Mauritania("MR"),
    Mauritius("MU"),
    Mayotte("YT"),
    Mexico("MX"),
    Micronesia("FM"),
    Moldova("MD"),
    Monaco("MC"),
    Mongolia("MN"),
    Montenegro("ME"),
    Montserrat("MS"),
    Morocco("MA"),
    Mozambique("MZ"),
    Myanmar("MM"),
    Namibia("NA"),
    Nauru("NR"),
    Nepal("NP"),
    Netherlands("NL"),
    New_Caledonia("NC"),
    New_Zealand("NZ"),
    Nicaragua("NI"),
    Niger("NE"),
    Nigeria("NG"),
    Niue("NU"),
    Norfolk_Island("NF"),
    Northern_Mariana_Islands("MP"),
    Norway("NO"),
    Oman("OM"),
    Pakistan("PK"),
    Palau("PW"),
    Palestine("PS"),
    Panama("PA"),
    Papua_New_Guinea("PG"),
    Paraguay("PY"),
    Peru("PE"),
    Philippines("PH"),
    Pitcairn("PN"),
    Poland("PL"),
    Portugal("PT"),
    Puerto_Rico("PR"),
    Qatar("QA"),
    Reunion("RE"),
    Romania("RO"),
    Russian_Federation("RU"),
    Rwanda("RW"),
    Saint_Lucia("LC"),
    Samoa("WS"),
    San_Marino("SM"),
    Saudi_Arabia("SA"),
    Senegal("SN"),
    Serbia("RS"),
    Seychelles("SC"),
    Sierra_Leone("SL"),
    Singapore("SG"),
    Slovakia("SK"),
    Slovenia("SI"),
    Solomon_Islands("SB"),
    Somalia("SO"),
    South_Africa("ZA"),
    South_Sudan("SS"),
    Spain("ES"),
    Sri_Lanka("LK"),
    Sudan("SD"),
    Suriname("SR"),
    Swaziland("SZ"),
    Sweden("SE"),
    Switzerland("CH"),
    Syria("SY"),
    Taiwan("TW"),
    Tajikistan("TJ"),
    Tanzania("TZ"),
    Thailand("TH"),
    Timor_Leste("TL"),
    Togo("TG"),
    Tokelau("TK"),
    Tonga("TO"),
    Trinidad_and_Tobago("TT"),
    Tunisia("TN"),
    Turkey("TR"),
    Turkmenistan("TM"),
    Tuvalu("TV"),
    Uganda("UG"),
    Ukraine("UA"),
    United_Arab_Emirates("AE"),
    England("GB"),
    USA("US"),
    Uruguay("UY"),
    Uzbekistan("UZ"),
    Vanuatu("VU"),
    Venezuela("VE"),
    Viet_Nam("VN"),
    Virgin_Islands_British("VG"),
    Virgin_Islands_USA("VI"),
    Wallis_and_Futuna("WF"),
    Western_Sahara("EH"),
    Yemen("YE"),
    Zambia("ZM"),
    Zimbabwe("ZW");

    Country(String alpha2) {
      this.alpha2 = alpha2;
    }

    public String alpha2;
  }

  enum BankCurrency {
    AED,
    AFN,
    ALL,
    AMD,
    ANG,
    AOA,
    ARS,
    AUD,
    AWG,
    AZN,
    BAM,
    BBD,
    BDT,
    BGN,
    BHD,
    BIF,
    BMD,
    BND,
    BOB,
    BOV,
    BRL,
    BSD,
    BTN,
    BWP,
    BYN,
    BZD,
    CAD,
    CDF,
    CHE,
    CHF,
    CHW,
    CLF,
    CLP,
    CNY,
    COP,
    COU,
    CRC,
    CUC,
    CUP,
    CVE,
    CZK,
    DJF,
    DKK,
    DOP,
    DZD,
    EGP,
    ERN,
    ETB,
    EUR,
    FJD,
    FKP,
    GBP,
    GEL,
    GHS,
    GIP,
    GMD,
    GNF,
    GTQ,
    GYD,
    HKD,
    HNL,
    HRK,
    HTG,
    HUF,
    IDR,
    ILS,
    INR,
    IQD,
    IRR,
    ISK,
    JMD,
    JOD,
    JPY,
    KES,
    KGS,
    KHR,
    KMF,
    KPW,
    KRW,
    KWD,
    KYD,
    KZT,
    LAK,
    LBP,
    LKR,
    LRD,
    LSL,
    LYD,
    MAD,
    MDL,
    MGA,
    MKD,
    MMK,
    MNT,
    MOP,
    MUR,
    MVR,
    MWK,
    MXN,
    MXV,
    MYR,
    MZN,
    NAD,
    NGN,
    NIO,
    NOK,
    NPR,
    NZD,
    OMR,
    PAB,
    PEN,
    PGK,
    PHP,
    PKR,
    PLN,
    PYG,
    QAR,
    RON,
    RSD,
    RUB,
    RWF,
    SAR,
    SBD,
    SCR,
    SDG,
    SEK,
    SGD,
    SHP,
    SLL,
    SOS,
    SRD,
    SSP,
    SVC,
    SYP,
    SZL,
    THB,
    TJS,
    TMT,
    TND,
    TOP,
    TRY,
    TTD,
    TWD,
    TZS,
    UAH,
    UGX,
    USD,
    USN,
    UYI,
    UYU,
    UZS,
    VEF,
    VND,
    VUV,
    WST,
    XAF,
    XAG,
    XAU,
    XBA,
    XBB,
    XBC,
    XBD,
    XCD,
    XDR,
    XOF,
    XPD,
    XPF,
    XPT,
    XSU,
    XTS,
    XUA,
    XXX,
    YER,
    ZAR,
    ZMW,
    ZWL
  }
}
