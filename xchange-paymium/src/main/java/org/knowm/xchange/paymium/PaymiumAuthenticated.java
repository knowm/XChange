package org.knowm.xchange.paymium;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.paymium.dto.account.PaymiumBalance;
import org.knowm.xchange.paymium.dto.account.PaymiumOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PaymiumAuthenticated extends Paymium {

  @GET
  @Path("user")
  PaymiumBalance getBalance(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @HeaderParam("Api-Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @GET
  @Path("user/orders")
  List<PaymiumOrder> getOrders(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @HeaderParam("Api-Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("offset") Long offset,
      @QueryParam("limit") Integer limit,
      @QueryParam("types[]") List<String> types,
      @QueryParam("active") Boolean active)
      throws IOException;
}
