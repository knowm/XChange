package org.knowm.xchange.cointrader;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;
import org.knowm.xchange.cointrader.dto.CointraderException;
import org.knowm.xchange.cointrader.dto.CointraderRequest;
import org.knowm.xchange.cointrader.dto.account.CointraderBalanceResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderCancelOrderRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderOpenOrdersResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrderRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderSubmitOrderResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderTradeHistoryRequest;
import org.knowm.xchange.cointrader.dto.trade.CointraderTradeHistoryResponse;
import org.knowm.xchange.cointrader.service.CointraderDigest;

@Path("api4")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CointraderAuthenticated {

  @POST
  @Path("account/balance")
  CointraderBalanceResponse getBalance(@HeaderParam("X-Auth") String publicKey, @HeaderParam("X-Auth-Hash") CointraderDigest signer,
      CointraderRequest req) throws CointraderException, IOException;

  @POST
  @Path("/order/{currency_pair}/buy")
  CointraderSubmitOrderResponse placeBuyOrder(@HeaderParam("X-Auth") String publicKey, @HeaderParam("X-Auth-Hash") CointraderDigest signer,
      @PathParam("currency_pair") Cointrader.Pair currencyPair, CointraderOrderRequest order) throws CointraderException, IOException;

  @POST
  @Path("/order/{currency_pair}/sell")
  CointraderSubmitOrderResponse placeSellOrder(@HeaderParam("X-Auth") String publicKey, @HeaderParam("X-Auth-Hash") CointraderDigest signer,
      @PathParam("currency_pair") Cointrader.Pair currencyPair, CointraderOrderRequest order) throws CointraderException, IOException;

  @POST
  @Path("order/{currency_pair}/list")
  CointraderOpenOrdersResponse getOpenOrders(@HeaderParam("X-Auth") String publicKey, @HeaderParam("X-Auth-Hash") CointraderDigest signer,
      @PathParam("currency_pair") Cointrader.Pair currencyPair, CointraderRequest req) throws CointraderException, IOException;

  @POST
  @Path("order/{currency_pair}/cancel")
  CointraderBaseResponse deleteOrder(@HeaderParam("X-Auth") String publicKey, @HeaderParam("X-Auth-Hash") CointraderDigest signer,
      @PathParam("currency_pair") Cointrader.Pair currencyPair, CointraderCancelOrderRequest req) throws CointraderException, IOException;

  @POST
  @Path("account/tradehistory/{currency_pair}")
  CointraderTradeHistoryResponse getTradeHistory(@HeaderParam("X-Auth") String publicKey, @HeaderParam("X-Auth-Hash") CointraderDigest signer,
      @PathParam("currency_pair") Cointrader.Pair currencyPair, CointraderTradeHistoryRequest req) throws CointraderException, IOException;
}
