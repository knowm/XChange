package com.xeiam.xchange.mtgox.v1;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.xeiam.xchange.mtgox.v0.dto.trade.MtGoxCancelOrder;
import com.xeiam.xchange.rest.ParamsDigest;

/**
 * @author timmolter
 */
@Path("api/0")
public interface MtGoxV0 {

  @POST
  @Path("cancelOrder.php")
  MtGoxCancelOrder cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce, @FormParam("oid") String orderId);

}
