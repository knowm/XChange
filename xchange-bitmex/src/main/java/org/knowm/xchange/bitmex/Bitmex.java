package org.knowm.xchange.bitmex;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexTrade;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitmex {

  @GET
  @Path("user")
  BitmexAccount getAccount(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;

  @GET
  @Path("user/wallet")
  BitmexWallet getWallet(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency) throws IOException;

  //Get a history of all of your wallet transactions (deposits, withdrawals, PNL)
  @GET
  @Path("user/walletHistory")
  List<BitmexWalletTransaction> getWalletHistory(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency) throws IOException;

  //Get a summary of all of your wallet transactions (deposits, withdrawals, PNL)
  @GET
  @Path("user/walletSummary")
  List<BitmexWalletTransaction> getWalletSummary(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency) throws IOException;

  @GET
  @Path("user/margin")
  BitmexMarginAccount getMarginAccountStatus(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
      @Nullable @QueryParam("currency") String currency) throws IOException;

  @GET
  @Path("user/margin?currency=all")
  List<BitmexMarginAccount> getMarginAccountsStatus(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;

  @GET
  @Path("trade")
  List<BitmexTrade> getTrades(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;

  @GET
  @Path("trade")
  List<BitmexTrade> getTrades(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
      @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("position")
  List<BitmexPosition> getPositions(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;

  @GET
  @Path("position")
  List<BitmexPosition> getPositions(@HeaderParam("API-KEY") String apiKey,
      @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter) throws IOException;

  @GET
  @Path("instrument")
  List<BitmexTicker> getTickers() throws IOException, BitmexException;

  @GET
  @Path("instrument")
  List<BitmexTicker> getTicker(@QueryParam("symbol") String symbol) throws IOException, BitmexException;

  @GET
  @Path("instrument/active")
  List<BitmexTicker> getActiveTickers() throws IOException, BitmexException;
}