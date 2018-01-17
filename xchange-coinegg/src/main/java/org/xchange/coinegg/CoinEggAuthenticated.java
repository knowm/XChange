package org.xchange.coinegg;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xchange.coinegg.dto.accounts.CoinEggBalance;
import org.xchange.coinegg.dto.trade.CoinEggTradeAdd;
import org.xchange.coinegg.dto.trade.CoinEggTradeCancel;
import org.xchange.coinegg.dto.trade.CoinEggTradeList;
import org.xchange.coinegg.dto.trade.CoinEggTradeView;

import si.mazi.rescu.ParamsDigest;

@Path("api/v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CoinEggAuthenticated {

  @POST
  @Path("balance")
  CoinEggBalance getBalance(@FormParam("key") String apiKey, @FormParam("nonce") long nonce, @FormParam("signature") ParamsDigest signer) throws IOException;
  
  @POST
  @Path("trade_list")
  CoinEggTradeList getTradeList(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce, @FormParam("since") long since, @FormParam("coin") String coin,
      @FormParam("type ") String type) throws IOException;
  
  @POST
  @Path("trade_view")
  CoinEggTradeView getTradeView(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce, @FormParam("id") String tradeID, @FormParam("coin") String coin) throws IOException;
  
  @POST
  @Path("trade_cancel")
  CoinEggTradeCancel getTradeCancel(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce, @FormParam("id") String id, @FormParam("coin") String coin) throws IOException;
  
  @POST
  @Path("trade_add")
  CoinEggTradeAdd getTradeAdd(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price,
      @FormParam("type") String type, @FormParam("coin") String coin) throws IOException;
  
}