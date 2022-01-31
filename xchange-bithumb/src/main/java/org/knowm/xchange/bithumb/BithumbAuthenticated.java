package org.knowm.xchange.bithumb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbOrderDetail;
import org.knowm.xchange.bithumb.dto.account.BithumbWalletAddress;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTicker;
import org.knowm.xchange.bithumb.dto.trade.BithumbTradeResponse;
import org.knowm.xchange.bithumb.dto.trade.BithumbUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BithumbAuthenticated {
  String API_KEY = "Api-Key";
  String API_SIGN = "Api-Sign";
  String API_NONCE = "Api-Nonce";
  String ENDPOINT = "endpoint";
  String API_CLIENT_TYPE = "api-client-type";

  @POST
  @Path("info/account")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<BithumbAccount> getAccount(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("order_currency") String order_currency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("info/balance")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<BithumbBalance> getBalance(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("currency") String currency)
      throws BithumbException, IOException;

  @POST
  @Path("info/wallet_address")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<BithumbWalletAddress> getWalletAddress(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("currency") String currency)
      throws BithumbException, IOException;

  @POST
  @Path("info/ticker")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<BithumbTicker> getTicker(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("order_currency") String orderCurrency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("info/orders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<List<BithumbOrder>> getOrders(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("order_id") String orderId,
      @FormParam("type") String type,
      @FormParam("count") Integer count,
      @FormParam("after") Integer after,
      @FormParam("order_currency") String orderCurrency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("info/order_detail")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<BithumbOrderDetail> getOrderDetail(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("order_id") String orderId,
      @FormParam("order_currency") String orderCurrency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("info/user_transactions")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse<List<BithumbUserTransaction>> getUserTransactions(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("offset") Integer offset,
      @FormParam("count") Integer count,
      @FormParam("searchGb") Integer searchGb,
      @FormParam("order_currency") String order_currency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("trade/place")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbTradeResponse placeOrder(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("order_currency") String orderCurrency,
      @FormParam("payment_currency") String paymentCurrency,
      @FormParam("units") BigDecimal units,
      @FormParam("price") BigDecimal price,
      @FormParam("type") String type)
      throws BithumbException, IOException;

  @POST
  @Path("trade/cancel")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbResponse cancelOrder(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("type") String type,
      @FormParam("order_id") String orderId,
      @FormParam("order_currency") String order_currency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("trade/market_buy")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbTradeResponse marketBuy(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("units") BigDecimal units,
      @FormParam("order_currency") String order_currency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;

  @POST
  @Path("trade/market_sell")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  BithumbTradeResponse marketSell(
      @HeaderParam(API_KEY) String apiKey,
      @HeaderParam(API_SIGN) ParamsDigest signature,
      @HeaderParam(API_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(API_CLIENT_TYPE) String apiClientType,
      @FormParam(ENDPOINT) ParamsDigest endpointGenerator,
      @FormParam("units") BigDecimal units,
      @FormParam("order_currency") String order_currency,
      @FormParam("payment_currency") String payment_currency)
      throws BithumbException, IOException;
}
