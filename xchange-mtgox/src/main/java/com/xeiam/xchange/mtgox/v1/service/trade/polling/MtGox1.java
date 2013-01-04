package com.xeiam.xchange.mtgox.v1.service.trade.polling;

import com.sun.istack.internal.NotNull;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.proxy.HmacPostBodyDigest;

import javax.ws.rs.*;
import java.math.BigDecimal;

/**
 * @author Matija Mazi <br/>
 */
@Path("api/1")
public interface MtGox1 {
  @POST
  @Path("generic/private/orders?raw")
  MtGoxOpenOrder[] getOpenOrders(
      @HeaderParam("Rest-Key") @NotNull String apiKey,
      @HeaderParam("Rest-Sign") @NotNull HmacPostBodyDigest postBodySignatureCreator,
      @FormParam("nonce") String nonce);

  @POST
  @Path("generic/bitcoin/send_simple")
  Object withdrawBtc(
      @HeaderParam("Rest-Key") @NotNull String apiKey,
      @HeaderParam("Rest-Sign") @NotNull HmacPostBodyDigest postBodySignatureCreator,
      @FormParam("nonce") String nonce,
      @FormParam("address") String address,
      @FormParam("amount_int") int amount,
      @FormParam("fee_int") int fee,
      @FormParam("no_instant") boolean noInstant,
      @FormParam("green") boolean green);

  @POST
  @Path("{tradeIdent}{currency}/private/order/add")
  MtGoxGenericResponse placeLimitOrder(
      @HeaderParam("Rest-Key") @NotNull String apiKey,
      @HeaderParam("Rest-Sign") @NotNull HmacPostBodyDigest postBodySignatureCreator,
      @FormParam("nonce") String nonce,
      @PathParam("tradeIdent") String tradableIdentifier,
      @PathParam("currency") String currency,
      @FormParam("type") String type,
      @FormParam("amount_int") BigDecimal amount,
      @FormParam("price_int") String price);

}
