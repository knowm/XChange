package org.knowm.xchange.coinegg;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinegg.dto.accounts.CoinEggBalance;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeAdd;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeCancel;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeList;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeView;
import si.mazi.rescu.ParamsDigest;

@Path("api/v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CoinEggAuthenticated {

  @POST
  @Path("balance")
  CoinEggBalance getBalance(
      @FormParam("key") String apiKey,
      @FormParam("nonce") long nonce,
      @FormParam("signature") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("trade_list")
  CoinEggTradeList getTradeList(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce,
      @FormParam("since") long since,
      @FormParam("coin") String coin,
      @FormParam("type ") String type)
      throws IOException;

  @POST
  @Path("trade_view")
  CoinEggTradeView getTradeView(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce,
      @FormParam("id") String tradeID,
      @FormParam("coin") String coin)
      throws IOException;

  @POST
  @Path("trade_cancel")
  CoinEggTradeCancel getTradeCancel(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce,
      @FormParam("id") String id,
      @FormParam("coin") String coin)
      throws IOException;

  @POST
  @Path("trade_add")
  CoinEggTradeAdd getTradeAdd(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") long nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price,
      @FormParam("type") String type,
      @FormParam("coin") String coin)
      throws IOException;
}
