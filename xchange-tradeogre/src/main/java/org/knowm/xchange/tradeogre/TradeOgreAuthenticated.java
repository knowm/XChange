package org.knowm.xchange.tradeogre;

import java.io.IOException;
import java.util.Collection;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalances;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreOrder;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreTradeResponse;

@Path("")
public interface TradeOgreAuthenticated extends TradeOgre {

  @GET
  @Path("account/balances")
  TradeOgreBalances getBalances(@HeaderParam("Authorization") String base64UserPwd)
      throws IOException;

  @POST
  @Path("account/balance")
  TradeOgreBalance getBalance(
      @HeaderParam("Authorization") String base64UserPwd, @FormParam("currency") String currency)
      throws IOException;

  @POST
  @Path("order/buy")
  TradeOgreTradeResponse buy(
      @HeaderParam("Authorization") String base64UserPwd,
      @FormParam("market") String market,
      @FormParam("quantity") String quantity,
      @FormParam("price") String price)
      throws IOException;

  @POST
  @Path("order/sell")
  TradeOgreTradeResponse sell(
      @HeaderParam("Authorization") String base64UserPwd,
      @FormParam("market") String market,
      @FormParam("quantity") String quantity,
      @FormParam("price") String price)
      throws IOException;

  @POST
  @Path("order/cancel")
  TradeOgreTradeResponse cancel(
      @HeaderParam("Authorization") String base64UserPwd, @FormParam("uuid") String uuid)
      throws IOException;

  @POST
  @Path("account/orders")
  Collection<TradeOgreOrder> getOrders(
      @HeaderParam("Authorization") String base64UserPwd, @FormParam("market") String market)
      throws IOException;
}
