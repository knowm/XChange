package org.xchange.bitz;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xchange.bitz.dto.trade.result.BitZTradeAddResult;

@Path("api_v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitZAuthenticated {
  
  @POST
  @Path("tradeAdd")
  BitZTradeAddResult addTrade(@FormParam("api_key") String apiKey, @FormParam("timestamp") long timestamp,
      @FormParam("nonce") String nonce, 
      @FormParam("type") String type, @FormParam("price") BigDecimal price, 
      @FormParam("number") BigDecimal amount, @FormParam("coin") String pair, 
      @FormParam("tradepwd") String tradePwd, @FormParam("sign") String signed);
  
}