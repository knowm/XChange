package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import com.xeiam.xchange.coinbaseex.dto.account.CoinbaseExSendMoneyRequest;
import com.xeiam.xchange.coinbaseex.dto.marketdata.*;
import com.xeiam.xchange.coinbaseex.dto.trade.*;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

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
