package org.knowm.xchange.livecoin;

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
import org.knowm.xchange.livecoin.dto.trade.LivecoinCancelResponse;
import org.knowm.xchange.livecoin.dto.trade.LivecoinOrderResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Livecoin {

  @GET
  @Path("exchange/restrictions")
  LivecoinRestrictions getRestrictions() throws IOException, LivecoinException;

  @GET
  @Path(
      "exchange/order_book?currencyPair={baseCurrency}/{targetCurrency}&depth={depth}&groupByPrice={groupByPrice}")
  LivecoinOrderBook getOrderBook(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency,
      @PathParam("depth") int depth,
      @PathParam("groupByPrice") String groupByPrice)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/all/order_book?depth={depth}&groupByPrice={groupByPrice}")
  LivecoinAllOrderBooks getAllOrderBooks(
      @PathParam("depth") int depth, @PathParam("groupByPrice") String groupByPrice)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/last_trades?currencyPair={baseCurrency}/{targetCurrency}")
  List<LivecoinTrade> getTrades(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/ticker?currencyPair={baseCurrency}/{targetCurrency}")
  LivecoinTicker getTicker(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws IOException, LivecoinException;

  @GET
  @Path("exchange/ticker")
  List<LivecoinTicker> getTicker() throws IOException, LivecoinException;

  @GET
  @Path("payment/balances")
  List<LivecoinBalance> balances(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("currency") String currency)
      throws IOException, LivecoinException;

  @GET
  @Path("payment/history/transactions")
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
  LivecoinWalletAddressResponse paymentAddress(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("currency") String currency)
      throws IOException, LivecoinException;

  @POST
  @Path("payment/out/coin")
  LivecoinPaymentOutResponse paymentOutCoin(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet)
      throws IOException, LivecoinException;

  @POST
  @Path("payment/out/payeer")
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
  LivecoinResponseWithDataMap paymentOutCapitalist(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("wallet") String wallet)
      throws IOException;

  @POST
  @Path("payment/out/card")
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
  LivecoinPaginatedResponse clientOrders(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("currencyPair") String currencyPair,
      @QueryParam("openClosed") String openClosed,
      @QueryParam("issuedFrom") Long issuedFrom,
      @QueryParam("issuedTo") Long issuedTo,
      @QueryParam("startRow") Long startRow,
      @QueryParam("endRow") Long endRow)
      throws IOException;

  @GET
  @Path("exchange/client_orders")
  LivecoinPaginatedResponse allClientOrders(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @QueryParam("openClosed") String openClosed)
      throws IOException;

  @POST
  @Path("exchange/buylimit")
  LivecoinOrderResponse buyWithLimitOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/selllimit")
  LivecoinOrderResponse sellWithLimitOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("price") BigDecimal price,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/buymarket")
  LivecoinOrderResponse buyWithMarketOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/sellmarket")
  LivecoinOrderResponse sellWithMarketOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("quantity") BigDecimal quantity)
      throws IOException;

  @POST
  @Path("exchange/cancellimit")
  LivecoinCancelResponse cancelLimitOrder(
      @HeaderParam("Api-key") String apiKey,
      @HeaderParam("Sign") LivecoinDigest signatureCreator,
      @FormParam("currencyPair") String currencyPair,
      @FormParam("orderId") long orderId)
      throws IOException;
}
