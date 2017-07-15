package org.knowm.xchange.cexio;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorkers;
import org.knowm.xchange.cexio.dto.trade.CexIOArchivedOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author brox
 */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CexIOAuthenticated extends CexIO {

  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOpenOrders getOpenOrders(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("id") long orderId) throws IOException;

  @POST
  @Path("place_order/{ident}/{currency}/")
  CexIOOrder placeOrder(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("type") CexIOOrder.Type type,
      @FormParam("price") BigDecimal price, @FormParam("amount") BigDecimal amount) throws IOException;

  // GHash.IO calls
  @POST
  @Path("ghash.io/hashrate")
  GHashIOHashrate getHashrate(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("ghash.io/workers")
  GHashIOWorkers getWorkers(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("archived_orders/{baseCcy}/{counterCcy}")
  List<CexIOArchivedOrder> archivedOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                                          @PathParam("baseCcy") String baseCcy,
                                          @PathParam("counterCcy") String counterCcy,
                                          @FormParam("limit") Integer limit,
                                          @FormParam("dateFrom") Long dateFrom,
                                          @FormParam("dateTo") Long dateTo,
                                          @FormParam("lastTxDateFrom") Long lastTxDateFrom,
                                          @FormParam("lastTxDateTo") Long lastTxDateTo,
                                          @FormParam("status") String status) throws HttpStatusIOException;

  @POST
  @Path("get_order/")
  CexIOOpenOrder getOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                          @FormParam("id") String orderId) throws IOException;

  @POST
  @Path("get_order_tx/")
  Map getOrderTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                           @FormParam("id") String orderId) throws IOException;

}
