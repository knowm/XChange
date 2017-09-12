package org.knowm.xchange.jubi;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.jubi.dto.account.JubiBalance;
import org.knowm.xchange.jubi.dto.trade.JubiOrderHistory;
import org.knowm.xchange.jubi.dto.trade.JubiOrderStatus;
import org.knowm.xchange.jubi.dto.trade.JubiTradeResult;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Dzf on 2017/7/8.
 */
@Path("api/v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface JubiAuthernticated {
  @POST
  @Path("balance")
  JubiBalance getBalance(@FormParam("key") String apiKey, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature) throws IOException;

  @POST
  @Path("trade_list")
  JubiOrderHistory getOrderHistory(@FormParam("coin") String coinType, @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("since") long since, @FormParam("type") String type,
      @FormParam("signature") ParamsDigest signature) throws IOException;

  @POST
  @Path("trade_view")
  JubiOrderStatus getOrderStatus(@FormParam("coin") String coinType,
      @FormParam("id") BigDecimal id,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature) throws IOException;

  @POST
  @Path("trade_add")
  JubiTradeResult placeOrder(@FormParam("amount") BigDecimal amount,
      @FormParam("coin") String coinType,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("price") BigDecimal price,
      @FormParam("type") String type,
      @FormParam("signature") ParamsDigest signature) throws IOException;

  @POST
  @Path("trade_cancel")
  JubiTradeResult cancelOrder(@FormParam("coin") String coinType,
      @FormParam("id") BigDecimal id,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature) throws IOException;
}
