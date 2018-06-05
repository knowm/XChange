package org.knowm.xchange.exmo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import si.mazi.rescu.SynchronizedValueFactory;

// todo: strongly type the returned values

@Path("v1")
public interface Exmo {

  @GET
  @Path("/trades")
  Map<String, List<Map>> trades(@QueryParam("pair") String pair);

  @POST
  @Path("/ticker")
  Map<String, Map<String, String>> ticker() throws IOException;

  @POST
  @Path("/pair_settings")
  Map<String, Map<String, String>> pairSettings() throws IOException;

  @POST
  @Path("/order_book/")
  Map<String, Map<String, Object>> orderBook(@QueryParam("pair") String pair) throws IOException;

  @POST
  @Path("/user_info/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map userInfo(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory);

  @POST
  @Path("/order_create/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map orderCreate(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory,
      @FormParam("pair") String pair,
      @FormParam("quantity") BigDecimal quantity,
      @FormParam("price") BigDecimal price,
      @FormParam("type") String type);

  @POST
  @Path("/order_cancel/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map orderCancel(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory,
      @FormParam("order_id") String orderId);

  @POST
  @Path("/user_open_orders/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map<String, List<Map<String, String>>> userOpenOrders(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory);

  @POST
  @Path("/user_trades/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map<String, List<Map<String, String>>> userTrades(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory,
      @FormParam("pair") String pair,
      @FormParam("offset") Long offset,
      @FormParam("limit") Integer limit);

  @POST
  @Path("/order_trades/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map<String, Object> orderTrades(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory,
      @FormParam("order_id") String orderId);

  @POST
  @Path("/deposit_address/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map<String, String> depositAddress(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory);

  @POST
  @Path("/wallet_history/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map<String, Object> walletHistory(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory,
      @QueryParam("date") long date);

  @POST
  @Path("/withdraw_crypt/")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  Map<String, Object> withdrawCrypt(
      @HeaderParam("Sign") ExmoDigest signatureCreator,
      @HeaderParam("Key") String publicKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonceFactory,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("currency") String currency,
      @QueryParam("address") String address,
      @QueryParam("invoice") String invoice);
}
