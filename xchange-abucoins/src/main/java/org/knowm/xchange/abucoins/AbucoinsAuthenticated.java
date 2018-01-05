package org.knowm.xchange.abucoins;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AbucoinsAuthenticated extends Abucoins {
/*
  @POST
  @Path("balance/")
  CexIOBalanceInfo getBalance(@HeaderParam("signature") ParamsDigest signer, CexIORequest cexIORequest) throws IOException;

  @POST
  @Path("open_orders/{ident}/{currency}/")
  CexIOOpenOrders getOpenOrders(@HeaderParam("signature") ParamsDigest signer, @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency, CexIORequest cexIORequest) throws IOException;

  @POST
  @Path("cancel_order/")
  Object cancelOrder(@HeaderParam("signature") ParamsDigest signer, AbucoinsSingleOrderIdRequest request) throws IOException;

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
  List<AbucoinsArchivedOrder> archivedOrders(@HeaderParam("signature") ParamsDigest signer, @PathParam("baseCcy") String baseCcy, @PathParam("counterCcy") String counterCcy, ArchivedOrdersRequest request);

  @POST
  @Path("get_order/")
  AbucoinsOpenOrder getOrder(@HeaderParam("signature") ParamsDigest signer, AbucoinsSingleOrderIdRequest request) throws IOException;

  @POST
  @Path("get_order_tx/")
  Map getOrderTransactions(@HeaderParam("signature") ParamsDigest signer, AbucoinsSingleIdRequest request) throws IOException;
*/
}
