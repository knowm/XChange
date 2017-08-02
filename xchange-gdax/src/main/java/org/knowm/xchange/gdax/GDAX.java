package org.knowm.xchange.gdax;

import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXSendMoneyRequest;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawFundsRequest;
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
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface GDAX {

  @GET
  @Path("products")
  List<GDAXProduct> getProducts() throws GDAXException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/ticker")
  GDAXProductTicker getProductTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws GDAXException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/stats")
  GDAXProductStats getProductStats(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws GDAXException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/book?level={level}")
  GDAXProductBook getProductOrderBook(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency,
                                      @PathParam("level") String level) throws GDAXException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/trades")
  GDAXTrade[] getTrades(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency)
      throws GDAXException, IOException;

  /**
   * Authenticated calls
   */

  @GET
  @Path("accounts")
  GDAXAccount[] getAccounts(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
                            @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;

  @GET
  @Path("orders?status={status}")
  GDAXOrder[] getListOrders(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
                            @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
                            @PathParam("status") String status) throws GDAXException, IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXIdResponse placeLimitOrder(GDAXPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
                                 @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
                                 @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXIdResponse placeMarketOrder(GDAXPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
                                  @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
                                  @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;

  @DELETE
  @Path("orders/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  void cancelOrder(@PathParam("id") String id, @HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
                   @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;

  @GET
  @Path("orders/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  GDAXOrder getOrder(@PathParam("id") String id, @HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
                   @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;


  @GET
  @Path("fills")
  GDAXFill[] getFills(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
                      @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
                      @QueryParam("after") Integer startingOrderId, @QueryParam("order_id") String orderId, @QueryParam("product_id") String productId) throws GDAXException, IOException;

  @POST
  @Path("accounts/{account_id}/transactions")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXSendMoneyResponse sendMoney(GDAXSendMoneyRequest sendMoney, @HeaderParam("CB-ACCESS-KEY") String apiKey,
                                  @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
                                  @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, @PathParam("account_id") String accountId) throws GDAXException, IOException;

  @POST
  @Path("withdrawals/crypto")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXWithdrawCryptoResponse withdrawCrypto(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
                                            @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
                                            GDAXWithdrawFundsRequest request) throws HttpStatusIOException;
}
