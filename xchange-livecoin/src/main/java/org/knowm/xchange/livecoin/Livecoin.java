package org.knowm.xchange.livecoin;

import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestrictions;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.livecoin.service.LivecoinPaginatedResponse;
import org.knowm.xchange.livecoin.service.LivecoinResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Livecoin {

  @GET
  @Path("exchange/restrictions")
  LivecoinRestrictions getRestrictions() throws IOException;

  @GET
  @Path("exchange/order_book?currencyPair={baseCurrency}/{targetCurrency}&depth={depth}")
  LivecoinOrderBook getOrderBook(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency,
                                 @PathParam("depth") int depth) throws IOException;

  @GET
  @Path("exchange/all/order_book?depth={depth}")
  Map<String, LivecoinOrderBook> getAllOrderBooks(@PathParam("depth") int depth) throws IOException;

  @GET
  @Path("exchange/last_trades?currencyPair={baseCurrency}/{targetCurrency}")
  LivecoinTrade[] getTrades(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  @GET
  @Path("exchange/ticker?currencyPair={baseCurrency}/{targetCurrency}")
  LivecoinTicker getTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  @GET
  @Path("exchange/ticker")
  List<LivecoinTicker> getTicker() throws IOException;

  @GET
  @Path("payment/balances")
  List<Map> balances(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator, @QueryParam("currency") String currency) throws IOException;

  @GET
  @Path("payment/history/transactions")
  List<Map> transactions(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                         @QueryParam("start") String start, @QueryParam("end") String end, @QueryParam("types") String types,
                         @QueryParam("limit") Integer limit, @QueryParam("offset") Long offset) throws IOException;

  @GET
  @Path("payment/get/address")
  Map paymentAddress(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator, @QueryParam("currency") String currency) throws IOException;

  @POST
  @Path("payment/out/coin")
  LivecoinResponse<Map> paymentOutCoin(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                       @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount, @FormParam("wallet") String wallet) throws IOException;

  @POST
  @Path("payment/out/payeer")
  LivecoinResponse<Map> paymentOutPayeer(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                         @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount, @FormParam("wallet") String wallet,
                                         @FormParam("protect") String protect, @FormParam("protect_code") String protectCode, @FormParam("protect_period") Integer protectPeriod) throws IOException;

  @POST
  @Path("payment/out/capitalist")
  LivecoinResponse<Map> paymentOutCapitalist(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                             @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount, @FormParam("wallet") String wallet) throws IOException;

  @POST
  @Path("payment/out/card")
  LivecoinResponse<Map> paymentOutCard(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                       @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount, @FormParam("card_number") String cardNumber,
                                       @FormParam("expiry_month") String expiryMonth, @FormParam("expiry_year") String expiryYear) throws IOException;

  @POST
  @Path("payment/out/okpay")
  LivecoinResponse<Map> paymentOutOkPay(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                        @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount, @FormParam("wallet") String wallet, @FormParam("invoice") String invoice) throws IOException;

  @POST
  @Path("payment/out/perfectmoney")
  LivecoinResponse<Map> paymentOutPerfectmoney(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                               @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount, @FormParam("wallet") String wallet,
                                               @FormParam("protect_code") String protectCode, @FormParam("protect_period") Integer protectPeriod) throws IOException;

  @GET
  @Path("exchange/client_orders")
  LivecoinPaginatedResponse clientOrders(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                                         @QueryParam("currencyPair") String currencyPair, @QueryParam("openClosed") String openClosed, @QueryParam("issuedFrom") Long issuedFrom,
                                         @QueryParam("issuedTo") Long issuedTo, @QueryParam("startRow") Long startRow, @QueryParam("endRow") Long endRow) throws IOException;

  @POST
  @Path("exchange/buylimit")
  Map buyWithLimitOrder(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                        @FormParam("currencyPair") String currencyPair, @FormParam("price") BigDecimal price, @FormParam("quantity") BigDecimal quantity) throws IOException;

  @POST
  @Path("exchange/selllimit")
  Map sellWithLimitOrder(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                         @FormParam("currencyPair") String currencyPair, @FormParam("price") BigDecimal price, @FormParam("quantity") BigDecimal quantity) throws IOException;

  @POST
  @Path("exchange/buymarket")
  Map buyWithMarketOrder(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                         @FormParam("currencyPair") String currencyPair, @FormParam("quantity") BigDecimal quantity) throws IOException;

  @POST
  @Path("exchange/sellmarket")
  Map sellWithMarketOrder(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                          @FormParam("currencyPair") String currencyPair, @FormParam("quantity") BigDecimal quantity) throws IOException;

  @POST
  @Path("exchange/cancellimit")
  Map cancelLimitOrder(@HeaderParam("Api-key") String apiKey, @HeaderParam("Sign") LivecoinDigest signatureCreator,
                       @FormParam("currencyPair") String currencyPair, @FormParam("orderId") long orderId) throws IOException;

}