package org.knowm.xchange.anx.v2;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.anx.v2.dto.account.ANXAccountInfoWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXBitcoinDepositAddressWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryWrapper;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponseWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXDepthWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXDepthsWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTickerWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTickersWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTradesWrapper;
import org.knowm.xchange.anx.v2.dto.trade.ANXGenericResponse;
import org.knowm.xchange.anx.v2.dto.trade.ANXLagWrapper;
import org.knowm.xchange.anx.v2.dto.trade.ANXOpenOrderWrapper;
import org.knowm.xchange.anx.v2.dto.trade.ANXOrderResultWrapper;
import org.knowm.xchange.anx.v2.dto.trade.ANXTradeResultWrapper;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/2")
@Produces(MediaType.APPLICATION_JSON)
public interface ANXV2 {

  @GET
  @Path("money/order/lag")
  ANXLagWrapper getLag() throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/ticker")
  ANXTickerWrapper getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/ticker")
  ANXTickersWrapper getTickers(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @QueryParam("extraCcyPairs") String extraCurrencyPairs) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/depth/fetch")
  ANXDepthWrapper getPartialDepth(@PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/depth/full")
  ANXDepthWrapper getFullDepth(@PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/depth/full")
  ANXDepthsWrapper getFullDepths(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @QueryParam("extraCcyPairs") String extraCurrencyPairs) throws ANXException, IOException;

  @GET
  @Path("{ident}{currency}/money/trade/fetch")
  ANXTradesWrapper getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency,
      @QueryParam("since") long since) throws ANXException, IOException;

  // Account Info API

  @POST
  @Path("money/info")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXAccountInfoWrapper getAccountInfo(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws ANXException, IOException;

  @POST
  @Path("money/{currency}/address")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXBitcoinDepositAddressWrapper requestDepositAddress(@HeaderParam("Rest-Key") String apiKey,
      @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currency") String currency) throws ANXException, IOException;

  @POST
  @Path("money/{currency}/send_simple")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXWithdrawalResponseWrapper withdrawBtc(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("currency") String currency, @FormParam("address") String address,
      @FormParam("amount_int") int amount, @FormParam("fee_int") int fee, @FormParam("no_instant") boolean noInstant,
      @FormParam("green") boolean green) throws ANXException, IOException;

  // Trade API
  @POST
  @Path("{baseCurrency}{counterCurrency}/money/orders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXOpenOrderWrapper getOpenOrders(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("baseCurrency") String baseCurrency,
      @PathParam("counterCurrency") String counterCurrency) throws ANXException, IOException;

  // Trade API
  @POST
  @Path("money/orders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXOpenOrderWrapper getOpenOrders(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws ANXException, IOException;

  /**
   * List of executed trades
   *
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param from optional Unix timestamp
   * @param to optional Unix timestamp
   * @return
   * @throws ANXException
   * @throws IOException
   */
  @POST
  @Path("money/trade/list")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXTradeResultWrapper getExecutedTrades(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("from") Long from,
      @FormParam("to") Long to) throws ANXException, IOException;

  /**
   * Status of the order
   *
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param order
   * @param type
   * @return
   * @throws ANXException
   * @throws IOException
   */
  @POST
  @Path("{baseCurrency}{counterCurrency}/money/order/result")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXOrderResultWrapper getOrderResult(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("baseCurrency") String baseCurrency,
      @PathParam("counterCurrency") String counterCurrency, @FormParam("order") String order,
      @FormParam("type") String type) throws ANXException, IOException;

  /**
   * @param postBodySignatureCreator
   * @param amount can be omitted to place market order
   */
  @POST
  @Path("{baseCurrency}{counterCurrency}/money/order/add")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXGenericResponse placeOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("baseCurrency") String baseCurrency,
      @PathParam("counterCurrency") String counterCurrency, @FormParam("type") String type, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws ANXException, IOException;

  /**
   * Note: I know it's weird to have BTCEUR hardcoded in the URL, but it really doesn't seems to matter. BTCUSD works too.
   * <p>
   *
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param orderId
   * @return
   */
  @POST
  @Path("{baseCurrency}{counterCurrency}/money/order/cancel")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXGenericResponse cancelOrder(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("oid") String orderId, @PathParam("baseCurrency") String baseCurrency,
      @PathParam("counterCurrency") String counterCurrency) throws ANXException, IOException;

  /**
   * Returns the History of the selected wallet
   *
   * @param apiKey
   * @param postBodySignatureCreator
   * @param nonce
   * @param currency
   * @param page to fetch (can be null for first page)
   * @param from start time (can be null)
   * @param to end time (can be null)
   * @return
   * @throws org.knowm.xchange.anx.v2.dto.ANXException
   */
  @POST
  @Path("money/wallet/history")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  ANXWalletHistoryWrapper getWalletHistory(@HeaderParam("Rest-Key") String apiKey, @HeaderParam("Rest-Sign") ParamsDigest postBodySignatureCreator,
                                           @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency,
                                           @FormParam("page") Integer page, @FormParam("from") Long from, @FormParam("to") Long to) throws ANXException, IOException;
}
