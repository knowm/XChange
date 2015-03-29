package com.xeiam.xchange.loyalbit;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.loyalbit.dto.LoyalbitBaseResponse;
import com.xeiam.xchange.loyalbit.dto.LoyalbitException;
import com.xeiam.xchange.loyalbit.dto.account.LoyalbitBalance;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitOrder;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitSubmitOrderResponse;
import com.xeiam.xchange.loyalbit.dto.trade.LoyalbitUserTransaction;
import com.xeiam.xchange.loyalbit.service.LoyalbitDigest;

@Path("api/private")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface LoyalbitAuthenticated {

  @POST
  @Path("balance")
  LoyalbitBalance getBalance(
      @FormParam("api_key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer
  ) throws LoyalbitException, IOException;

  @POST
  @Path("transactions")
  LoyalbitUserTransaction[] getTransactions(
      @FormParam("api_key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer,
      @FormParam("offset") Integer offset,
      @FormParam("limit") Integer limit,
      @FormParam("sort") Sort sort
  ) throws LoyalbitException, IOException;

  @POST
  @Path("open_orders")
  LoyalbitOrder[] getOpenOrders(
      @FormParam("api_key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer
  ) throws LoyalbitException, IOException;

  @POST
  @Path("delete_order")
  LoyalbitBaseResponse deleteOrder(
      @FormParam("api_key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer,
      @FormParam("order_id") Long orderId
  ) throws LoyalbitException, IOException;

  @POST
  @Path("trade")
  LoyalbitSubmitOrderResponse submitOrder(
      @FormParam("api_key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer,
      @FormParam("type") LoyalbitOrder.Type type,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("pair") Pair pair
  ) throws LoyalbitException, IOException;

  enum Sort { asc, desc }

  enum Pair { BTCUSD }
}
