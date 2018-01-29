package org.knowm.xchange.gdax;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccount;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccountAddress;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface GDAX {

  @GET
  @Path("products")
  GDAXProduct[] getProducts() throws GDAXException, IOException;

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
  
  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXIdResponse placeStopOrder(GDAXPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;

  @DELETE
  @Path("orders/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  void cancelOrder(@PathParam("id") String id, @HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws GDAXException, IOException;

  @GET
  @Path("orders/{id}")
  @Produces(MediaType.APPLICATION_JSON)
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

  @GET
  @Path("accounts/{account_id}/ledger")
  @Consumes(MediaType.APPLICATION_JSON)
  List<Map> ledger(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, @PathParam("account_id") String accountId, @QueryParam("after") Integer startingOrderId) throws GDAXException, IOException;

  @POST
  @Path("reports")
  @Consumes(MediaType.APPLICATION_JSON)
  Map createReport(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, GDAXReportRequest request) throws GDAXException, IOException;

  @GET
  @Path("reports/{report_id}")
  @Consumes(MediaType.APPLICATION_JSON)
  List<Map> getReport(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, @PathParam("report_id") String reportId) throws GDAXException, IOException;

  @POST
  @Path("withdrawals/crypto")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  GDAXWithdrawCryptoResponse withdrawCrypto(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      GDAXWithdrawFundsRequest request) throws HttpStatusIOException;

  @GET
  @Path("coinbase-accounts")
  GDAXCoinbaseAccount[] getGDAXAccounts(@HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase) throws HttpStatusIOException;

  @POST
  @Path("coinbase-accounts/{account_id}/addresses")
  GDAXCoinbaseAccountAddress getGDAXAccountAddress(@HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("account_id") String accountId);

  class GDAXReportRequest {
    public final @JsonProperty("type") String type;
    public final @JsonProperty("start_date") String startDate;
    public final @JsonProperty("end_date") String endDate;
    public final @JsonProperty("product_id") String productId;
    public final @JsonProperty("account_id") String accountId;
    public final @JsonProperty("format") String format;
    public final @JsonProperty("email") String email;

    public enum Type {
      fills, account
    }

    public enum Format {
      pdf, csv
    }

    public GDAXReportRequest(Type type, Date startDate, Date endDate, String productId, String accountId, Format format, String email) {
      this(type.name(), DateUtils.toUTCString(startDate), DateUtils.toUTCString(endDate), productId, accountId, format == null ? null : format.name(), email);
    }

    public GDAXReportRequest(String type, String startDate, String endDate, String productId, String accountId, String format, String email) {
      this.type = type;
      this.startDate = startDate;
      this.endDate = endDate;
      this.productId = productId;
      this.accountId = accountId;
      this.format = format;
      this.email = email;
    }
  }

}
