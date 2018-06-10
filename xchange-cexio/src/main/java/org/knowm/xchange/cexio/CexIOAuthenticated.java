package org.knowm.xchange.cexio;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cexio.dto.*;
import org.knowm.xchange.cexio.dto.CexioCancelReplaceOrderRequest;
import org.knowm.xchange.cexio.dto.account.*;
import org.knowm.xchange.cexio.dto.trade.*;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelReplaceOrderResponse;
import si.mazi.rescu.ParamsDigest;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CexIOAuthenticated extends CexIO {

  @POST
  @Path("get_myfee")
  CexIOFeeInfo getMyFee(@HeaderParam("_ignored_") ParamsDigest signer, CexIORequest cexIORequest)
      throws IOException;

  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(
      @HeaderParam("_ignored_") ParamsDigest signer, CexIORequest cexIORequest) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOpenOrders getOpenOrders(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency,
      CexIORequest cexIORequest)
      throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(
      @HeaderParam("_ignored_") ParamsDigest signer, CexioSingleOrderIdRequest request)
      throws IOException;

  @POST
  @Path("cancel_orders/{currencyA}/{currencyB}/")
  CexIOCancelAllOrdersResponse cancelAllOrders(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("currencyA") String currencyA,
      @PathParam("currencyB") String currencyB,
      CexIORequest request)
      throws IOException;

  @POST
  @Path("cancel_replace_order/{currencyA}/{currencyB}/")
  CexIOCancelReplaceOrderResponse cancelReplaceOrder(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("currencyA") String currencyA,
      @PathParam("currencyB") String currencyB,
      CexioCancelReplaceOrderRequest request)
      throws IOException;

  @POST
  @Path("place_order/{currencyA}/{currencyB}/")
  CexIOOrder placeOrder(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("currencyA") String currencyA,
      @PathParam("currencyB") String currencyB,
      PlaceOrderRequest placeOrderRequest)
      throws IOException;

  // GHash.IO calls
  @POST
  @Path("ghash.io/hashrate")
  GHashIOHashrate getHashrate(@HeaderParam("_ignored_") ParamsDigest signer) throws IOException;

  @POST
  @Path("ghash.io/workers")
  GHashIOWorkers getWorkers(@HeaderParam("_ignored_") ParamsDigest signer) throws IOException;

  @POST
  @Path("archived_orders/{baseCcy}/{counterCcy}")
  List<CexIOArchivedOrder> archivedOrders(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("baseCcy") String baseCcy,
      @PathParam("counterCcy") String counterCcy,
      ArchivedOrdersRequest request)
      throws IOException;

  @POST
  @Path("get_order/")
  CexIOOpenOrder getOrder(
      @HeaderParam("_ignored_") ParamsDigest signer, CexioSingleOrderIdRequest request)
      throws IOException;

  @POST
  @Path("get_order/")
  Map getOrderRaw(@HeaderParam("signature") ParamsDigest signer, CexioSingleOrderIdRequest request)
      throws IOException;

  @POST
  @Path("get_order_tx/")
  Map getOrderTransactions(
      @HeaderParam("_ignored_") ParamsDigest signer, CexioSingleIdRequest request)
      throws IOException;

  @POST
  @Path("get_address")
  CexIOCryptoAddress getCryptoAddress(
      @HeaderParam("_ignored_") ParamsDigest signer, CexioCryptoAddressRequest request)
      throws IOException;
}
