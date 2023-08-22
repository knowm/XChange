package org.knowm.xchange.coinbasepro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.coinbasepro.dto.CoinbasePagedResponse;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTrades;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFee;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProLedger;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProSendMoneyRequest;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWithdrawCryptoResponse;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWithdrawFundsRequest;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCandle;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBook;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProWallet;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProWalletAddress;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProIdResponse;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProSendMoneyResponse;
import org.knowm.xchange.utils.DateUtils;
import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbasePro {

  @GET
  @Path("currencies")
  CoinbaseProCurrency[] getCurrencies() throws CoinbaseProException, IOException;

  @GET
  @Path("products")
  CoinbaseProProduct[] getProducts() throws CoinbaseProException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/ticker")
  CoinbaseProProductTicker getProductTicker(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws CoinbaseProException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/stats")
  CoinbaseProProductStats getProductStats(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws CoinbaseProException, IOException;

  @GET
  @Path("products/stats")
  Map<String, CoinbaseProStats> getStats() throws CoinbaseProException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/book?level={level}")
  CoinbaseProProductBook getProductOrderBook(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency,
      @PathParam("level") String level)
      throws CoinbaseProException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/trades")
  CoinbaseProTrade[] getTrades(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws CoinbaseProException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/trades")
  CoinbaseProTrades getTradesPageable(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency,
      @QueryParam("after") Long after,
      @QueryParam("limit") Integer limit)
      throws CoinbaseProException, IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/candles")
  CoinbaseProCandle[] getHistoricalCandles(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency,
      @QueryParam("start") String start,
      @QueryParam("end") String end,
      @QueryParam("granularity") String granularity)
      throws CoinbaseProException, IOException;

  /** Authenticated calls */
  @GET
  @Path("accounts")
  CoinbaseProAccount[] getAccounts(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  @GET
  @Path("accounts/{account_id}")
  CoinbaseProAccount getAccountById(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("account_id") String accountId)
      throws CoinbaseProException, IOException;

  @GET
  @Path("fees")
  CoinbaseProFee getFees(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  @GET
  @Path("orders")
  CoinbaseProOrder[] getListOrders(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  @GET
  @Path("orders")
  CoinbasePagedResponse<CoinbaseProOrder> getListOrders(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @QueryParam("status") String status,
      @QueryParam("limit") Integer limit,
      @QueryParam("after") String after)
      throws CoinbaseProException, IOException;

  @GET
  @Path("orders")
  CoinbaseProOrder[] getListOrders(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @QueryParam("status") String status,
      @QueryParam("product_id") String productId)
      throws CoinbaseProException, IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseProIdResponse placeOrder(
      CoinbaseProPlaceOrder placeOrder,
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  @DELETE
  @Path("orders/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  void cancelOrder(
      @PathParam("id") String id,
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  @GET
  @Path("orders/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  CoinbaseProOrder getOrder(
      @PathParam("id") String id,
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  /**
   * Get a list of fills. A fill is a partial or complete match on a specific order.
   * @param apiKey
   * @param signer
   * @param timestamp
   * @param passphrase
   * @param orderId
   * @param productId
   * @param limit
   * @param tradeIdBefore
   * @param tradeIdAfter
   * @param marketType
   * @param startDate
   * @param endDate
   * @return CoinbasePagedResponse<CoinbaseProFill>
   * @throws CoinbaseProException
   * @throws IOException
   */
  @GET
  @Path("fills")
  CoinbasePagedResponse<CoinbaseProFill> getFills(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @QueryParam("order_id") String orderId,
      @QueryParam("product_id") String productId,
      @QueryParam("limit") Integer limit,
      @QueryParam("before") Integer tradeIdBefore,
      @QueryParam("after") Integer tradeIdAfter,
      @QueryParam("market_type") String marketType,
      @QueryParam("start_date") String startDate,
      @QueryParam("end_date") String endDate
  ) throws CoinbaseProException, IOException;

  @POST
  @Path("accounts/{account_id}/transactions")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseProSendMoneyResponse sendMoney(
      CoinbaseProSendMoneyRequest sendMoney,
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("account_id") String accountId)
      throws CoinbaseProException, IOException;

  @GET
  @Path("accounts/{account_id}/ledger")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseProLedger ledger(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("account_id") String accountId,
      @QueryParam("start_date") String startDate,
      @QueryParam("end_date") String endDate,
      @QueryParam("before") String beforeId,
      @QueryParam("after") String afterId,
      @QueryParam("limit") Integer limit,
      @QueryParam("profile_id") String profileId)
      throws CoinbaseProException, IOException;

  /**
   * Lists past withdrawals and deposits for an account.
   */
  @GET
  @Path("accounts/{account_id}/transfers")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseProTransfers getTransfersByAccountId(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("account_id") String accountId,
      @QueryParam("before") String beforeDate,
      @QueryParam("after") String afterDate,
      @QueryParam("limit") Integer limit,
      @QueryParam("type") String type) // Possible types [deposit, withdraw, internal_deposit, internal_withdraw]
      throws CoinbaseProException, IOException;

  /**
   * Gets a list of in-progress and completed transfers of funds in/out of any of the user's accounts.
   */
  @GET
  @Path("transfers")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseProTransfers getTransfers(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @QueryParam("type") String type, // Possible types [deposit, withdraw, internal_deposit, internal_withdraw]
      @QueryParam("profile_id") String profileId,
      @QueryParam("before") String beforeDate,
      @QueryParam("after") String afterDate,
      @QueryParam("limit") Integer limit)
      throws CoinbaseProException, IOException;

  @POST
  @Path("reports")
  @Consumes(MediaType.APPLICATION_JSON)
  Map<?, ?> createReport(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      CoinbaseProReportRequest request)
      throws CoinbaseProException, IOException;

  @GET
  @Path("reports/{report_id}")
  @Consumes(MediaType.APPLICATION_JSON)
  Map<?, ?> getReport(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("report_id") String reportId)
      throws CoinbaseProException, IOException;

  @POST
  @Path("withdrawals/crypto")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseProWithdrawCryptoResponse withdrawCrypto(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      CoinbaseProWithdrawFundsRequest request)
      throws CoinbaseProException, IOException;

  @GET
  @Path("coinbase-accounts")
  CoinbaseProWallet[] getCoinbaseProWallets(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  @POST
  @Path("coinbase-accounts/{account_id}/addresses")
  CoinbaseProWalletAddress getCoinbaseProWalletAddress(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase,
      @PathParam("account_id") String accountId)
      throws CoinbaseProException, IOException;

  @GET
  @Path("/users/self/verify")
  JsonNode getVerifyId(
      @HeaderParam("CB-ACCESS-KEY") String apiKey,
      @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer,
      @HeaderParam("CB-ACCESS-TIMESTAMP") long timestamp,
      @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase)
      throws CoinbaseProException, IOException;

  class CoinbaseProReportRequest {
    public final @JsonProperty("type") String type;
    public final @JsonProperty("start_date") String startDate;
    public final @JsonProperty("end_date") String endDate;
    public final @JsonProperty("product_id") String productId;
    public final @JsonProperty("account_id") String accountId;
    public final @JsonProperty("format") String format;
    public final @JsonProperty("email") String email;

    public CoinbaseProReportRequest(
        Type type,
        Date startDate,
        Date endDate,
        String productId,
        String accountId,
        Format format,
        String email) {
      this(
          type.name(),
          DateUtils.toUTCString(startDate),
          DateUtils.toUTCString(endDate),
          productId,
          accountId,
          format == null ? null : format.name(),
          email);
    }

    public CoinbaseProReportRequest(
        String type,
        String startDate,
        String endDate,
        String productId,
        String accountId,
        String format,
        String email) {
      this.type = type;
      this.startDate = startDate;
      this.endDate = endDate;
      this.productId = productId;
      this.accountId = accountId;
      this.format = format;
      this.email = email;
    }

    public enum Type {
      fills,
      account
    }

    public enum Format {
      pdf,
      csv
    }
  }
}