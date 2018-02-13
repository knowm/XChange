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
import org.knowm.xchange.cexio.dto.CexIORequest;
import org.knowm.xchange.cexio.dto.CexioCryptoAddressRequest;
import org.knowm.xchange.cexio.dto.CexioSingleIdRequest;
import org.knowm.xchange.cexio.dto.CexioSingleOrderIdRequest;
import org.knowm.xchange.cexio.dto.PlaceOrderRequest;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.CexIOCryptoAddress;
import org.knowm.xchange.cexio.dto.account.CexIOFeeInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorkers;
import org.knowm.xchange.cexio.dto.trade.CexIOArchivedOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelAllOrdersResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;

import si.mazi.rescu.ParamsDigest;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CexIOAuthenticated extends CexIO {

  @POST
  @Path("get_myfee")
  CexIOFeeInfo getMyFee(@HeaderParam("signature") ParamsDigest signer, CexIORequest cexIORequest) throws IOException;

  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(@HeaderParam("signature") ParamsDigest signer, CexIORequest cexIORequest) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOpenOrders getOpenOrders(@HeaderParam("signature") ParamsDigest signer, @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, CexIORequest cexIORequest) throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(@HeaderParam("signature") ParamsDigest signer, CexioSingleOrderIdRequest request) throws IOException;

  @POST
  @Path("cancel_orders/{currencyA}/{currencyB}/")
  CexIOCancelAllOrdersResponse cancelAllOrders(@HeaderParam("signature") ParamsDigest signer, @PathParam("currencyA") String currencyA, @PathParam("currencyB") String currencyB, CexIORequest request) throws IOException;

  @POST
  @Path("place_order/{currencyA}/{currencyB}/")
  CexIOOrder placeOrder(@HeaderParam("signature") ParamsDigest signer, @PathParam("currencyA") String currencyA, @PathParam("currencyB") String currencyB, PlaceOrderRequest placeOrderRequest) throws IOException;

  // GHash.IO calls
  @POST
  @Path("ghash.io/hashrate")
  GHashIOHashrate getHashrate(@HeaderParam("signature") ParamsDigest signer) throws IOException;

  @POST
  @Path("ghash.io/workers")
  GHashIOWorkers getWorkers(@HeaderParam("signature") ParamsDigest signer) throws IOException;

  @POST
  @Path("archived_orders/{baseCcy}/{counterCcy}")
  List<CexIOArchivedOrder> archivedOrders(@HeaderParam("signature") ParamsDigest signer, @PathParam("baseCcy") String baseCcy, @PathParam("counterCcy") String counterCcy, ArchivedOrdersRequest request) throws IOException;

  @POST
  @Path("get_order/")
  CexIOOpenOrder getOrder(@HeaderParam("signature") ParamsDigest signer, CexioSingleOrderIdRequest request) throws IOException;

  @POST
  @Path("get_order_tx/")
  Map getOrderTransactions(@HeaderParam("signature") ParamsDigest signer, CexioSingleIdRequest request) throws IOException;

  @POST
  @Path("get_address")
  CexIOCryptoAddress getCryptoAddress(@HeaderParam("signature") ParamsDigest signer, CexioCryptoAddressRequest request) throws IOException;
}
