package com.xeiam.xchange.bitstamp.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.api.BitStamp;
import com.xeiam.xchange.bitstamp.api.model.*;
import com.xeiam.xchange.utils.HttpTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.money.CurrencyUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.util.HashMap;

/**
 * @author Matija Mazi <br/>
 * @created 1/2/13 7:10 PM
 */
public class BitStampImpl implements BitStamp {

  protected static final CurrencyUnit BTC = CurrencyUnit.of("BTC");

  private HttpTemplate httpTemplate;
  private ExchangeSpecification exchangeSpecification;
  private ObjectMapper mapper;

  public BitStampImpl(HttpTemplate httpTemplate, ExchangeSpecification exchangeSpecification, ObjectMapper mapper) {

    this.httpTemplate = httpTemplate;
    this.exchangeSpecification = exchangeSpecification;
    this.mapper = mapper;
  }

  private String getPwd() {

    return exchangeSpecification.getPassword();
  }

  private String getUser() {

    return exchangeSpecification.getUserName();
  }

  private QueryStringBuilder userPass(String user, String password) {

    return new QueryStringBuilder().add("user", user).add("password", password);
  }

  @Override
  public OrderBook getOrderBook() {

    return getForJsonObject("order_book", OrderBook.class);
  }

  @Override
  public Ticker getTicker() {

    return getForJsonObject("ticker", Ticker.class);
  }

  @Override
  public Transaction[] getTransactions() {

    return getForJsonObject("transactions", Transaction[].class);
  }

  @Override
  public Transaction[] getTransactions(@QueryParam("timedelta") long timedeltaSec) {

    return getForJsonObject("transactions", Transaction[].class, new QueryStringBuilder().add("timedelta", timedeltaSec));
  }

  @Override
  public Object cancelOrder(@FormParam("user") String user, @FormParam("password") String password, @FormParam("id") int orderId) {

    return postForJsonObject("cancel_order", Object.class, userPass(user, password).add("id", orderId));
  }

  @Override
  public Balance getBalance(@FormParam("user") String user, @FormParam("password") String password) {

    return postForJsonObject("balance", Balance.class, userPass(user, password));
  }

  @Override
  public UserTransaction[] getUserTransactions(@FormParam("user") String user, @FormParam("password") String password, @QueryParam("timedelta") long timedeltaSec) {

    return postForJsonObject("user_transactions", UserTransaction[].class, userPass(user, password).add("timedelta", timedeltaSec));
  }

  @Override
  public UserTransaction[] getUserTransactions(@FormParam("user") String user, @FormParam("password") String password) {

    return postForJsonObject("user_transactions", UserTransaction[].class, userPass(user, password));
  }

  @Override
  public Order[] getOpenOrders(@FormParam("user") String user, @FormParam("password") String password) {

    return postForJsonObject("open_orders", Order[].class, userPass(user, password));
  }

  @Override
  public Order buy(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") double amount, @FormParam("price") double price) {

    return postForJsonObject("buy", Order.class, userPass(user, password).add("amount", amount).add("price", price));
  }

  @Override
  public Order sell(@FormParam("user") String user, @FormParam("password") String password, @FormParam("amount") double amount, @FormParam("price") double price) {

    return postForJsonObject("sell", Order.class, userPass(user, password).add("amount", amount).add("price", price));
  }

  @Override
  public String getBitcoinDepositAddress(@FormParam("user") String user, @FormParam("password") String password) {

    return postForJsonObject("bitcoin_deposit_address", String.class, userPass(user, password));
  }

  private String getUrl(String method) {

    return String.format("%s/api/%s/", exchangeSpecification.getUri(), method);
  }

  protected <T> T getForJsonObject(String method, Class<T> returnType, QueryStringBuilder params) {

    String url = getUrl(method);
    if (params != null) {
      url += "?" + params.toString(true);
    }

    return httpTemplate.getForJsonObject(url, returnType, mapper, new HashMap<String, String>());
  }

  protected <T> T getForJsonObject(String method, Class<T> returnType) {

    return getForJsonObject(method, returnType, null);
  }

  protected  <T> T postForJsonObject(String method, Class<T> returnType, QueryStringBuilder postBody) {

    return httpTemplate.postForJsonObject(getUrl(method), returnType, postBody.toString(false), mapper, new HashMap<String, String>());
  }

}
