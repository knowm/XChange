package org.xchange.kraken;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xchange.kraken.dto.account.KrakenBalanceResult;

import si.mazi.rescu.ParamsDigest;

@Path("0/private")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenAuthenticated {

  @POST
  @Path("Balance")
  public KrakenBalanceResult getBalance(@HeaderParam("API-Key") String apiKey,@HeaderParam("API-Sign") ParamsDigest signer,@FormParam("nonce") long nonce);
  

  /**
   * 
   * @param apiKey 
   * @param signer
   * @param nonce
   * @param pair kraken currency pair
   * @param type buy or sell
   * @param ordertype market or limit
   * @param price optional: dependent upon ordertype
   * @param volume order volume in lots
   * @return
   */
  @POST
  @Path("AddOrder")
  public KrakenBalanceResult addOrder(@HeaderParam("API-Key") String apiKey,@HeaderParam("API-Sign") ParamsDigest signer,@FormParam("nonce") long nonce, @FormParam("pair") String pair, @FormParam("type") String type, @FormParam("ordertype") String ordertype, @FormParam("price") String price, @FormParam("volume") String volume);
  
  
}
