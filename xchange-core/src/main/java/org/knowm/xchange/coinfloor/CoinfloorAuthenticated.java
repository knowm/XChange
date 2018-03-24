package org.knowm.xchange.coinfloor;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinfloor.dto.CoinfloorException;
import org.knowm.xchange.coinfloor.dto.account.CoinfloorBalance;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorMarketOrderResponse;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorOrder;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorUserTransaction;
import org.knowm.xchange.currency.Currency;

@Path("bist")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinfloorAuthenticated {
  @GET
  @Path("{base}/{counter}/balance/")
  CoinfloorBalance getBalance(@PathParam("base") Currency base, @PathParam("counter") Currency counter) throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/user_transactions/")
  CoinfloorUserTransaction[] getUserTransactions(@PathParam("base") Currency base, @PathParam("counter") Currency counter,
      @FormParam("limit") Integer numberOfTransactions, @FormParam("offset") Long offset, @FormParam("sort") String sort)
      throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/open_orders/")
  CoinfloorOrder[] getOpenOrders(@PathParam("base") Currency base, @PathParam("counter") Currency counter) throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/buy/")
  CoinfloorOrder buy(@PathParam("base") Currency base, @PathParam("counter") Currency counter, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/buy_market/")
  CoinfloorMarketOrderResponse buyMarket(@PathParam("base") Currency base, @PathParam("counter") Currency counter,
      @FormParam("quantity") BigDecimal quantity) throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/sell/")
  CoinfloorOrder sell(@PathParam("base") Currency base, @PathParam("counter") Currency counter, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/sell_market/")
  CoinfloorMarketOrderResponse sellMarket(@PathParam("base") Currency base, @PathParam("counter") Currency counter,
      @FormParam("quantity") BigDecimal quantity) throws CoinfloorException, IOException;

  @GET
  @Path("{base}/{counter}/cancel_order/")
  boolean cancelOrder(@PathParam("base") Currency base, @PathParam("counter") Currency counter, @FormParam("id") long id)
      throws CoinfloorException, IOException;
}
