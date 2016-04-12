package org.knowm.xchange.loyalbit;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.loyalbit.dto.LoyalbitBaseResponse;
import org.knowm.xchange.loyalbit.dto.LoyalbitException;
import org.knowm.xchange.loyalbit.dto.account.LoyalbitBalance;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitOrder;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitSubmitOrderResponse;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitUserTransaction;
import org.knowm.xchange.loyalbit.service.LoyalbitDigest;

import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/private")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface LoyalbitAuthenticated {

  @POST
  @Path("balance")
  LoyalbitBalance getBalance(@FormParam("api_key") String apiKey, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer) throws LoyalbitException, IOException;

  @POST
  @Path("transactions")
  LoyalbitUserTransaction[] getTransactions(@FormParam("api_key") String apiKey, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer, @FormParam("offset") Integer offset, @FormParam("limit") Integer limit,
      @FormParam("sort") Sort sort) throws LoyalbitException, IOException;

  @POST
  @Path("open_orders")
  LoyalbitOrder[] getOpenOrders(@FormParam("api_key") String apiKey, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer) throws LoyalbitException, IOException;

  @POST
  @Path("delete_order")
  LoyalbitBaseResponse deleteOrder(@FormParam("api_key") String apiKey, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer, @FormParam("order_id") Long orderId) throws LoyalbitException, IOException;

  @POST
  @Path("trade")
  LoyalbitSubmitOrderResponse submitOrder(@FormParam("api_key") String apiKey, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("base64_hmac") LoyalbitDigest signer, @FormParam("type") LoyalbitOrder.Type type, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price, @FormParam("pair") Pair pair) throws LoyalbitException, IOException;

  enum Sort {
    asc, desc
  }

  enum Pair {
    BTCUSD
  }
}
