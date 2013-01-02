package com.xeiam.xchange.bitstamp.api;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.xeiam.xchange.bitstamp.api.model.Balance;
import com.xeiam.xchange.bitstamp.api.model.Order;
import com.xeiam.xchange.bitstamp.api.model.OrderBook;
import com.xeiam.xchange.bitstamp.api.model.Ticker;
import com.xeiam.xchange.bitstamp.api.model.Transaction;
import com.xeiam.xchange.bitstamp.api.model.UserTransaction;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 5:53 PM See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Produces("application/json")
public interface BitStamp {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("order_book/")
  @Produces("application/json")
  public OrderBook getOrderBook();

  @GET
  @Path("ticker/")
  @Produces("application/json")
  public Ticker getTicker();

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions/")
  @Produces("application/json")
  public List<Transaction> getTransactions();

  @GET
  @Path("transactions/")
  @Produces("application/json")
  public List<Transaction> getTransactions(@QueryParam("timedelta") long timedeltaSec);

  /** @return true if order has been found and canceled. */
  @POST
  @Path("cancel_order/")
  @Produces("application/json")
  public Object cancelOrder(@FormParam("user") String user, @FormParam("password") String password, @FormParam("id") int orderId);

  @POST
  @Path("balance/")
  @Produces("application/json")
  public Balance getBalance(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public List<UserTransaction> getUserTransactions(@FormParam("user") String user, @FormParam("password") String password, @QueryParam("timedelta") long timedeltaSec);

  @POST
  @Path("user_transactions/")
  @Produces("application/json")
  public List<UserTransaction> getUserTransactions(@FormParam("user") String user, @FormParam("password") String password);

  @POST
  @Path("open_orders/")
  @Produces("application/json")
  public List<Order> getOpenOrders(@FormParam("user") String user, @FormParam("password") String password);

  /** buy limit order */
  @POST
  @Path("buy/")
  @Produces("application/json")
  public Order buy(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") double amount, @FormParam("price") double price);

  /** sell limit order */
  @POST
  @Path("sell/")
  @Produces("application/json")
  public Order sell(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") double amount, @FormParam("price") double price);

  @POST
  @Path("bitcoin_deposit_address/")
  @Produces("application/json")
  public String getBitcoinDepositAddress(@FormParam("user") String user, @FormParam("password") String password);

  // todo: bitstamp code handling, send to user, withdrawals
}
