package com.xeiam.xchange.mtgox.v1;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxGenericResponse;
import com.xeiam.xchange.proxy.ParamsDigest;

/**
 * @author timmolter
 */
@Path("api/0")
public interface MtGoxV0 {

  @POST
  @Path("cancelOrder.php")
  MtGoxGenericResponse cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") long nonce,
      @FormParam("oid") String orderId);

}
