package com.xeiam.xchange.mtgox.v1;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxBitcoinDepositAddress;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxWithdrawalResponse;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.proxy.ParamsDigest;

/**
 * @author Matija Mazi
 */
@Path("api/1")
public interface MtGoxV1 {

  @GET
  @Path("/{ident}{currency}/public/ticker?raw")
  MtGoxTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @GET
  @Path("/{ident}{currency}/public/depth?raw")
  MtGoxDepth getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @GET
  @Path("/{ident}{currency}/public/fulldepth?raw")
  MtGoxDepth getFullDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @GET
  @Path("/{ident}{currency}/public/trades?raw")
  MtGoxTrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency);

  @POST
  @Path("generic/private/info?raw")
  MtGoxAccountInfo getAccountInfo(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce);

  @POST
  @Path("generic/bitcoin/address?raw")
  MtGoxBitcoinDepositAddress requestDepositAddress(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("description") String description, @FormParam("ipn") String notificationUrl);

  @POST
  @Path("generic/private/orders?raw")
  MtGoxOpenOrder[] getOpenOrders(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce);

  @POST
  @Path("generic/bitcoin/send_simple?raw")
  MtGoxWithdrawalResponse withdrawBtc(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("address") String address, @FormParam("amount_int") int amount, @FormParam("fee_int") int fee, @FormParam("no_instant") boolean noInstant, @FormParam("green") boolean green);

  /**
   * @param postBodySignatureCreator
   * @param amount can be omitted to place market order
   */
  @POST
  @Path("{tradeIdent}{currency}/private/order/add")
  MtGoxGenericResponse placeOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @PathParam("tradeIdent") String tradableIdentifier, @PathParam("currency") String currency, @FormParam("type") String type, @FormParam("amount_int") BigDecimal amount,
      @FormParam("price_int") String price);

  // TODO eventually implement this when MtGox supports it, and get rid of V0 version
  // @POST
  // @Path("private/order/cancelorder")
  // MtGoxGenericResponse cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
  // @HeaderParam("oid") String orderId);

}
