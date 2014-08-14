
package com.xeiam.xchange.vaultofsatoshi;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.HeaderParam;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosOrderId;

/**
 * @author Michael Lagacé
 */

@Path("trade")
@Produces(MediaType.APPLICATION_JSON)
public interface VaultOfSatoshiTrading {
	
  @POST
  @Path("place")
  public VosResponse<VosOrderId> placeOrder(@HeaderParam("Api-Key") String apiKey, 
		  @HeaderParam("Api-Sign") ParamsDigest signer, 
		  @FormParam("nonce") long nonce, @FormParam("type") String type,
		  @FormParam("order_currency") String order_currency, 
		  @FormParam("units[precision]") int precision, 
		  @FormParam("units[value]") BigDecimal units_float, 
		  @FormParam("units[value_int]") long units_int, 
		  @FormParam("payment_currency") String payment, 
		  @FormParam("price[precision]") int price_precision, 
		  @FormParam("price[value]") BigDecimal price_float, 
		  @FormParam("price[value_int]") long price_int) throws IOException;

  @POST
  @Path("cancel")
  public VosResponse<Void> cancelOrder(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("order_id") int order_id) throws IOException;
  
}
