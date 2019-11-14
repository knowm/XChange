package org.knowm.xchange.livecoin;

import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME;
import static org.knowm.xchange.livecoin.LivecoinResilience.MAIN_RATE_LIMITER;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.client.resilience.Decorator;
import org.knowm.xchange.client.resilience.RateLimiter;
import org.knowm.xchange.client.resilience.Retry;
import org.knowm.xchange.livecoin.dto.LivecoinException;
import org.knowm.xchange.livecoin.dto.LivecoinPaginatedResponse;
import org.knowm.xchange.livecoin.dto.LivecoinResponseWithDataMap;
import org.knowm.xchange.livecoin.dto.account.LivecoinBalance;
import org.knowm.xchange.livecoin.dto.account.LivecoinPaymentOutResponse;
import org.knowm.xchange.livecoin.dto.account.LivecoinWalletAddressResponse;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinAllOrderBooks;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestrictions;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinUserOrder;
import org.knowm.xchange.livecoin.dto.trade.LivecoinCancelResponse;
import org.knowm.xchange.livecoin.dto.trade.LivecoinOrderResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Livecoin {

  @GET
  @Path("exchange/restrictions")
  @Decorator(retry = @Retry(name = "getRestrictions"))
  LivecoinRestrictions getRestrictions() throws IOException, LivecoinException;

  @GET
  @Path(
      "exchange/order_book?currencyPair={baseCurrency}/{targetCurrency}&depth={depth}&groupByPrice={groupByPrice}")
  @Decorator(retry = @Retry(name = "getOrderBook"))
  LivecoinOrderBook getOrderBook(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency,
      @PathParam("depth") int depth,
      @PathParam("groupByPrice") String groupByPrice)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/all/order_book?depth={depth}&groupByPrice={groupByPrice}")
  @Decorator(retry = @Retry(name = "getAllOrderBooks"))
  LivecoinAllOrderBooks getAllOrderBooks(
      @PathParam("depth") int depth, @PathParam("groupByPrice") String groupByPrice)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/last_trades?currencyPair={baseCurrency}/{targetCurrency}")
  @Decorator(retry = @Retry(name = "getTrades"))
  List<LivecoinTrade> getTrades(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/ticker?currencyPair={baseCurrency}/{targetCurrency}")
  @Decorator(retry = @Retry(name = "getTicker"))
  LivecoinTicker getTicker(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/ticker")
  @Decorator(retry = @Retry(name = "getTicker"))
  List<LivecoinTicker> getTicker() throws IOException, LivecoinException;

  @GET
  @Path("payment/balances")
  @Decorator(retry = @Retry(name = "balances"))
  List<LivecoinBalance> balances(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("currency") String currency)
      throws IOException, LivecoinException;

  @GET
  @Path("payment/history/transactions")
  @Decorator(retry = @Retry(name = "transactions"))
  List<Map> transactions(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("start") String start,
      @QueryParam("end") String end,
      @QueryParam("types") String types,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Long offset)
      throws IOException, LivecoinException;

  @GET
  @Path("payment/get/address")
  @Decorator(retry = @Retry(name = "paymentAddress"))
  LivecoinWalletAddressResponse paymentAddress(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("currency") String currency)
      throws IOException, LivecoinException;

  @POST
  @Path("payment/out/coin")
  @Decorator(
      retry = @Retry(name = "paymentOutCoin", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  LivecoinPaymentOutResponse paymentOutCoin(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet)
      throws IOException, LivecoinException;

  @POST
  @Path("payment/out/payeer")
  @Decorator(
      retry =
          @Retry(name = "paymentOutPayeer", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  LivecoinResponseWithDataMap paymentOutPayeer(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet,
      @FormParam("protect") String protect,
      @FormParam("protect_code") String protectCode,
      @FormParam("protect_period") Integer protectPeriod)
      throws IOException;

  @POST
  @Path("payment/out/capitalist")
  @Decorator(
      retry =
          @Retry(
              name = "paymentOutCapitalist",
              baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  LivecoinResponseWithDataMap paymentOutCapitalist(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet)
      throws IOException;

  @POST
  @Path("payment/out/card")
  @Decorator(
      retry = @Retry(name = "paymentOutCard", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  LivecoinResponseWithDataMap paymentOutCard(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("card_number") String cardNumber,
      @FormParam("expiry_month") String expiryMonth,
      @FormParam("expiry_year") String expiryYear)
      throws IOException;

  @POST
  @Path("payment/out/okpay")
  @Decorator(
      retry =
          @Retry(name = "paymentOutOkPay", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  LivecoinResponseWithDataMap paymentOutOkPay(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet,
      @FormParam("invoice") String invoice)
      throws IOException;

  @POST
  @Path("payment/out/perfectmoney")
  @Decorator(
      retry =
          @Retry(name = "paymentOutOkPay", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  LivecoinResponseWithDataMap paymentOutPerfectmoney(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet,
      @FormParam("protect_code") String protectCode,
      @FormParam("protect_period") Integer protectPeriod)
      throws IOException;

  @GET
  @Path("exchange/client_orders")
  @Decorator(retry = @Retry(name = "clientOrders"))
  @Decorator(rateLimiter = @RateLimiter(name = MAIN_RATE_LIMITER))
  LivecoinPaginatedResponse<LivecoinUserOrder> clientOrders(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("currencyPair") String currencyPair,
      @QueryParam("openClosed") String openClosed,
      @QueryParam("issuedFrom") Long issuedFrom,
      @QueryParam("issuedTo") Long issuedTo,
      @QueryParam("startRow") Long startRow,
      @QueryParam("endRow") Long endRow)
      throws IOException;

  @POST
  @Path("exchange/buylimit")
  @Decorator(
      retry =
          @Retry(name = "buyWithLimitOrder", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  @Decorator(rateLimiter = @RateLimiter(name = MAIN_RATE_LIMITER))
  LivecoinOrderResponse buyWithLimitOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/selllimit")
  @Decorator(
      retry =
          @Retry(name = "sellWithLimitOrder", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  @Decorator(rateLimiter = @RateLimiter(name = MAIN_RATE_LIMITER))
  LivecoinOrderResponse sellWithLimitOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/buymarket")
  @Decorator(
      retry =
          @Retry(name = "buyWithMarketOrder", baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  @Decorator(rateLimiter = @RateLimiter(name = MAIN_RATE_LIMITER))
  LivecoinOrderResponse buyWithMarketOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/sellmarket")
  @Decorator(
      retry =
          @Retry(
              name = "sellWithMarketOrder",
              baseConfig = NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
  @Decorator(rateLimiter = @RateLimiter(name = MAIN_RATE_LIMITER))
  LivecoinOrderResponse sellWithMarketOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/cancellimit")
  @Decorator(retry = @Retry(name = "cancelLimitOrder"))
  @Decorator(rateLimiter = @RateLimiter(name = MAIN_RATE_LIMITER))
  LivecoinCancelResponse cancelLimitOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderId") long orderId)
      throws IOException;
}
