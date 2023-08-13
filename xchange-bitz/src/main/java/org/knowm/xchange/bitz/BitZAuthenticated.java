package org.knowm.xchange.bitz;

import java.math.BigDecimal;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bitz.dto.trade.result.BitZOpenOrderResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeAddResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeCancelResult;

@Path("api_v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitZAuthenticated {

  @POST
  @Path("tradeAdd")
  BitZTradeAddResult addTrade(
      @FormParam("api_key") String apiKey,
      @FormParam("timestamp") String timestamp,
      @FormParam("nonce") String nonce,
      @FormParam("type") String type,
      @FormParam("price") BigDecimal price,
      @FormParam("number") BigDecimal amount,
      @FormParam("coin") String pair,
      @FormParam("tradepwd") String tradePwd,
      @FormParam("sign") String signed);

  @POST
  @Path("tradeCancel")
  BitZTradeCancelResult cancelTrade(
      @FormParam("api_key") String apiKey,
      @FormParam("timestamp") String timestamp,
      @FormParam("nonce") String nonce,
      @FormParam("id") String id,
      @FormParam("sign") String signed);

  @POST
  @Path("openOrders")
  BitZOpenOrderResult openOrders(
      @FormParam("api_key") String apiKey,
      @FormParam("timesamp") String timestamp,
      @FormParam("nonce") String nonce,
      @FormParam("coin") String coin,
      @FormParam("sign") String signed);
}