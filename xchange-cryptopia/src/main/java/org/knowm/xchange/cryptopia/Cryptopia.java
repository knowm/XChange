package org.knowm.xchange.cryptopia;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.ParamsDigest;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Cryptopia {

  @GET
  @Path("GetCurrencies")
  CryptopiaBaseResponse<List<CryptopiaCurrency>> getCurrencies() throws IOException;

  @GET
  @Path("GetTradePairs")
  CryptopiaBaseResponse<List<CryptopiaTradePair>> getTradePairs() throws IOException;

  @GET
  @Path("GetMarkets")
  CryptopiaBaseResponse<List<CryptopiaTicker>> getMarkets() throws IOException;

  @GET
  @Path("GetMarkets/{baseMarket}")
  CryptopiaBaseResponse<List<CryptopiaTicker>> getMarkets(@PathParam("baseMarket") String baseMarket) throws IOException;

  @GET
  @Path("GetMarkets/{baseMarket}/{hours}")
  CryptopiaBaseResponse<List<CryptopiaTicker>> getMarkets(@PathParam("baseMarket") String baseMarket, @PathParam("hours") long hours) throws IOException;

  @GET
  @Path("GetMarket/{market}")
  CryptopiaBaseResponse<CryptopiaTicker> getMarket(@PathParam("market") String market) throws IOException;

  @GET
  @Path("GetMarket/{market}/{hours}")
  CryptopiaBaseResponse<CryptopiaTicker> getMarket(@PathParam("market") String market, @PathParam("hours") long hours) throws IOException;

  @GET
  @Path("GetMarketHistory/{market}")
  CryptopiaBaseResponse<List<CryptopiaMarketHistory>> getMarketHistory(@PathParam("market") String market) throws IOException;

  @GET
  @Path("GetMarketHistory/{market}/{hours}")
  CryptopiaBaseResponse<List<CryptopiaMarketHistory>> getMarketHistory(@PathParam("market") String market, @PathParam("hours") long hours) throws IOException;

  @GET
  @Path("GetMarketOrders/{market}")
  CryptopiaBaseResponse<CryptopiaOrderBook> getMarketOrders(@PathParam("market") String market) throws IOException;

  @GET
  @Path("GetMarketOrders/{market}/{orderCount}")
  CryptopiaBaseResponse<CryptopiaOrderBook> getMarketOrders(@PathParam("market") String pair, @PathParam("orderCount") long orderCount) throws IOException;

  @POST
  @Path("GetBalance")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<List<Map>> getBalance(@HeaderParam("Authorization") ParamsDigest signature, Object o) throws IOException;

  @POST
  @Path("GetDepositAddress")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<Map> getDepositAddress(@HeaderParam("Authorization") ParamsDigest signature, GetDepositAddressRequest request) throws IOException;

  @POST
  @Path("SubmitWithdraw")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<Long> submitWithdraw(@HeaderParam("Authorization") ParamsDigest signature, SubmitWithdrawRequest request) throws IOException;

  @POST
  @Path("GetTransactions")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<List<Map>> getTransactions(@HeaderParam("Authorization") ParamsDigest signature, GetTransactionsRequest request) throws IOException;

  @POST
  @Path("GetOpenOrders")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<List<Map>> getOpenOrders(@HeaderParam("Authorization") ParamsDigest signature, GetOpenOrdersRequest request) throws IOException;

  @POST
  @Path("SubmitTrade")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<Map> submitTrade(@HeaderParam("Authorization") ParamsDigest signature, SubmitTradeRequest request) throws IOException;

  @POST
  @Path("CancelTrade")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<List> cancelTrade(@HeaderParam("Authorization") ParamsDigest signature, CancelTradeRequest request) throws IOException;

  @POST
  @Path("GetTradeHistory")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<List<Map>> getTradeHistory(@HeaderParam("Authorization") ParamsDigest signature, GetTradeHistoryRequest request) throws IOException;

  @POST
  @Path("SubmitTransfer")
  @Consumes(MediaType.APPLICATION_JSON)
  CryptopiaBaseResponse<String> submitTransfer(@HeaderParam("Authorization") ParamsDigest signature, SubmitTransferRequest request) throws IOException;

  class SubmitTransferRequest {
    public final @JsonProperty("Currency")
    String currency;
    public final @JsonProperty("Username")
    String username;
    public final @JsonProperty("Amount")
    BigDecimal amount;

    public SubmitTransferRequest(String currency, String username, BigDecimal amount) {
      this.currency = currency;
      this.username = username;
      this.amount = amount;
    }
  }

  class GetTradeHistoryRequest {
    public final @JsonProperty("Market")
    String market;
    @Nullable
    public final @JsonProperty("Count")
    Integer count;

    public GetTradeHistoryRequest(String market, @Nullable Integer count) {
      this.market = market;
      this.count = count;
    }
  }

  class CancelTradeRequest {
    public final @JsonProperty("Type")
    String type;
    public final @JsonProperty("OrderId")
    String orderId;
    public final @JsonProperty("TradePairId")
    Long tradePairId;

    public CancelTradeRequest(String type, String orderId, Long tradePairId) {
      this.type = type;
      this.orderId = orderId;
      this.tradePairId = tradePairId;
    }
  }

  class SubmitTradeRequest {
    public final @JsonProperty("Market")
    String market;
    public final @JsonProperty("Type")
    String type;
    public final @JsonProperty("Rate")
    BigDecimal rate;
    public final @JsonProperty("Amount")
    BigDecimal amount;

    public SubmitTradeRequest(String market, String type, BigDecimal rate, BigDecimal amount) {
      this.market = market;
      this.type = type;
      this.rate = rate;
      this.amount = amount;
    }
  }

  class GetOpenOrdersRequest {
    public final @JsonProperty("Market")
    String market;

    @Nullable
    public final @JsonProperty("Count")
    Integer count;

    public GetOpenOrdersRequest(String market, @Nullable Integer count) {
      this.market = market;
      this.count = count;
    }
  }

  class GetTransactionsRequest {
    public final @JsonProperty("Type")
    String type;
    @Nullable
    public final @JsonProperty("Count")
    Integer count;

    public GetTransactionsRequest(String type, @Nullable Integer count) {
      this.type = type;
      this.count = count;
    }
  }

  class GetDepositAddressRequest {
    public final @JsonProperty("Currency")
    String currency;

    public GetDepositAddressRequest(String currency) {
      this.currency = currency;
    }
  }

  class SubmitWithdrawRequest {
    public final @JsonProperty("Currency")
    String currency;
    public final @JsonProperty("Address")
    String address;
    public final @JsonProperty("PaymentId")
    String paymentId;
    public final @JsonProperty("Amount")
    BigDecimal amount;

    public SubmitWithdrawRequest(String currency, String address, String paymentId, BigDecimal amount) {
      this.currency = currency;
      this.address = address;
      this.paymentId = paymentId;
      this.amount = amount;
    }
  }
}
