package org.knowm.xchange.gdax;

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

import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXSendMoneyRequest;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface GDAX {

  @GET
  @Path("products")
  List<GDAXProduct> getProducts() throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/ticker")
  GDAXProductTicker getProductTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/stats")
  GDAXProductStats getProductStats(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/book?level={level}")
  GDAXProductBook getProductOrderBook(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency,
      @PathParam("level") String level) throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/trades")
  GDAXTrade[] getTrades(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  /** Authenticated calls */

  @GET
  @Path("accounts")
  GDAXAccount[] getAccounts(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);

  @GET
  @Path("orders?status={status}")
  GDAXOrder[] getListOrders(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("status") String status);

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXIdResponse placeLimitOrder(GDAXPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXIdResponse placeMarketOrder(GDAXPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
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
  GDAXFill[] getFills(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("order_id") String orderId);

  @POST
  @Path("accounts/{account_id}/transactions")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXSendMoneyResponse sendMoney(GDAXSendMoneyRequest sendMoney, @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, @PathParam("account_id") String accountId);

}
