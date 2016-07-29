package org.knowm.xchange.coinbaseex;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import org.knowm.xchange.coinbaseex.dto.account.CoinbaseExSendMoneyRequest;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProduct;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExTrade;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExFill;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExIdResponse;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExOrder;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExPlaceOrder;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExSendMoneyResponse;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbaseEx {

  @GET
  @Path("products")
  List<CoinbaseExProduct> getProducts() throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/ticker")
  CoinbaseExProductTicker getProductTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/stats")
  CoinbaseExProductStats getProductStats(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/book?level={level}")
  CoinbaseExProductBook getProductOrderBook(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency,
      @PathParam("level") String level) throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/trades?limit={limit}")
  CoinbaseExTrade[] getTrades(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency,
      @PathParam("limit") String limit) throws IOException;

  /** Authenticated calls */

  @GET
  @Path("accounts")
  CoinbaseExAccount[] getAccounts(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);

  @GET
  @Path("orders?status={status}")
  CoinbaseExOrder[] getListOrders(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("status") String status);

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseExIdResponse placeLimitOrder(CoinbaseExPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseExIdResponse placeMarketOrder(CoinbaseExPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);

  @DELETE
  @Path("orders/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  void cancelOrder(@PathParam("id") String id, @HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);

  @GET
  @Path("fills")
  CoinbaseExFill[] getFills(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("order_id") String orderId);

  @POST
  @Path("accounts/{account_id}/transactions")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseExSendMoneyResponse sendMoney(CoinbaseExSendMoneyRequest sendMoney, @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, @PathParam("account_id") String accountId);

}
