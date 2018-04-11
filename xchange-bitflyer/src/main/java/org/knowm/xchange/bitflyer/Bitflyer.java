package org.knowm.xchange.bitflyer;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.bitflyer.dto.account.BitflyerAddress;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBalance;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBankAccount;
import org.knowm.xchange.bitflyer.dto.account.BitflyerCoinHistory;
import org.knowm.xchange.bitflyer.dto.account.BitflyerDepositOrWithdrawal;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginAccount;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginStatus;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginTransaction;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarket;
import org.knowm.xchange.bitflyer.dto.account.BitflyerWithdrawRequest;
import org.knowm.xchange.bitflyer.dto.account.BitflyerWithdrawResponse;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerOrderbook;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerTicker;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerExecution;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerPosition;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitflyer {

  String ACCESS_KEY = "ACCESS-KEY";
  String ACCESS_TIMESTAMP = "ACCESS-TIMESTAMP";
  String ACCESS_SIGN = "ACCESS-SIGN";

  @GET
  @Path("getmarkets")
  List<BitflyerMarket> getMarkets() throws IOException, BitflyerException;

  /** return "BTC_JPY" or "BTC_USD" for U.S. accounts. */
  @GET
  @Path("getticker")
  BitflyerTicker getTicker() throws IOException, BitflyerException;

  @GET
  @Path("getticker")
  BitflyerTicker getTicker(@QueryParam("product_code") String productCode)
      throws IOException, BitflyerException;

  /** return "BTC_JPY" or "BTC_USD" for U.S. accounts. */
  @GET
  @Path("getboard")
  BitflyerOrderbook getBoard() throws IOException, BitflyerException;

  @GET
  @Path("getboard")
  BitflyerOrderbook getBoard(@QueryParam("product_code") String productCode)
      throws IOException, BitflyerException;

  @GET
  @Path("getexecutions")
  List<BitflyerExecution> getExecutions() throws IOException, BitflyerException;

  @GET
  @Path("getexecutions")
  List<BitflyerExecution> getExecutions(@QueryParam("product_code") String productCode)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getpermissions")
  List<String> getPermissions(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("/me/getbalance")
  List<BitflyerBalance> getBalances(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getcollateral")
  BitflyerMarginStatus getMarginStatus(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getcollateralaccounts")
  List<BitflyerMarginAccount> getMarginAccounts(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getaddresses")
  @Produces(MediaType.APPLICATION_JSON)
  List<BitflyerAddress> getAddresses(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getcoinins")
  List<BitflyerCoinHistory> getCoinIns(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getcoinouts")
  List<BitflyerCoinHistory> getCoinOuts(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getbankaccounts")
  List<BitflyerBankAccount> getBankAccounts(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getdeposits")
  List<BitflyerDepositOrWithdrawal> getCashDeposits(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @POST
  @Path("me/withdraw")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  BitflyerWithdrawResponse withdrawFunds(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest,
      BitflyerWithdrawRequest body)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getwithdrawals")
  List<BitflyerDepositOrWithdrawal> getWithdrawals(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getpositions")
  List<BitflyerPosition> getPositions(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest,
      @QueryParam("product_code") String productCode)
      throws IOException, BitflyerException;

  @GET
  @Path("me/getcollateralhistory")
  List<BitflyerMarginTransaction> getMarginHistory(
      @HeaderParam(ACCESS_KEY) String apiKey,
      @HeaderParam(ACCESS_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(ACCESS_SIGN) ParamsDigest paramsDigest)
      throws IOException, BitflyerException;
}
