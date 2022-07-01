package org.knowm.xchange.cexio;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cexio.dto.ArchivedOrdersRequest;
import org.knowm.xchange.cexio.dto.CexIOGetPositionRequest;
import org.knowm.xchange.cexio.dto.CexIOOpenPositionRequest;
import org.knowm.xchange.cexio.dto.CexIORequest;
import org.knowm.xchange.cexio.dto.CexioCancelReplaceOrderRequest;
import org.knowm.xchange.cexio.dto.CexioCryptoAddressRequest;
import org.knowm.xchange.cexio.dto.CexioPlaceOrderRequest;
import org.knowm.xchange.cexio.dto.CexioSingleOrderIdRequest;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.CexIOCryptoAddress;
import org.knowm.xchange.cexio.dto.account.CexIOFeeInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorkers;
import org.knowm.xchange.cexio.dto.trade.CexIOArchivedOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelAllOrdersResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelReplaceOrderResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOrderTransactionsResponse;
import org.knowm.xchange.cexio.dto.trade.CexioClosePositionResponse;
import org.knowm.xchange.cexio.dto.trade.CexioOpenPositionResponse;
import org.knowm.xchange.cexio.dto.trade.CexioOpenPositionsResponse;
import org.knowm.xchange.cexio.dto.trade.CexioPositionResponse;
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
  @Path("open_orders/")
  CexIOOpenOrders getOpenOrders(
      @HeaderParam("_ignored_") ParamsDigest signer, CexIORequest cexIORequest) throws IOException;

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
      CexioPlaceOrderRequest placeOrderRequest)
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
  CexIOOrderTransactionsResponse getOrderTransactions(
      @HeaderParam("_ignored_") ParamsDigest signer, CexioSingleOrderIdRequest request)
      throws IOException;

  @POST
  @Path("get_address")
  CexIOCryptoAddress getCryptoAddress(
      @HeaderParam("_ignored_") ParamsDigest signer, CexioCryptoAddressRequest request)
      throws IOException;

  @POST
  @Path("open_position/{symbol1}/{symbol2}/")
  CexioOpenPositionResponse openPosition(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("symbol1") String symbol1,
      @PathParam("symbol2") String symbol2,
      CexIOOpenPositionRequest cexIOOpenPositionRequest)
      throws IOException;

  @POST
  @Path("get_position")
  CexioPositionResponse getPosition(
      @HeaderParam("_ignored_") ParamsDigest signer,
      CexIOGetPositionRequest cexIOGetPositionRequest)
      throws IOException;

  @POST
  @Path("close_position/{symbol1}/{symbol2}/")
  CexioClosePositionResponse closePosition(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("symbol1") String symbol1,
      @PathParam("symbol2") String symbol2,
      CexIOGetPositionRequest cexIOGetPositionRequest)
      throws IOException;

  @POST
  @Path("open_positions/{symbol1}/{symbol2}/")
  CexioOpenPositionsResponse getOpenPositions(
      @HeaderParam("_ignored_") ParamsDigest signer,
      @PathParam("symbol1") String symbol1,
      @PathParam("symbol2") String symbol2,
      CexIORequest emptyRequest)
      throws IOException;
}
