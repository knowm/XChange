package org.knowm.xchange.bittrex.v1;

import org.knowm.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexDepositAddressResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexDepositsHistoryResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexWithdrawResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexWithdrawalsHistoryResponse;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexCancelOrderResponse;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexOpenOrdersResponse;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexTradeHistoryResponse;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("v1.1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticated extends Bittrex {

  @GET
  @Path("account/getdepositaddress")
  BittrexDepositAddressResponse getdepositaddress(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                                  @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("currency") String currency) throws IOException;

  @GET
  @Path("account/getbalances")
  BittrexBalancesResponse balances(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                   @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("market/buylimit")
  BittrexTradeResponse buylimit(@QueryParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature,
                                @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market, @QueryParam("quantity") String quantity,
                                @QueryParam("rate") String rate) throws IOException;

  @GET
  @Path("market/selllimit")
  BittrexTradeResponse selllimit(@QueryParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature,
                                 @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market, @QueryParam("quantity") String quantity,
                                 @QueryParam("rate") String rate) throws IOException;

  @GET
  @Path("market/buymarket")
  BittrexTradeResponse buymarket(@QueryParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature,
                                 @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market,
                                 @QueryParam("quantity") String quantity) throws IOException;

  @GET
  @Path("market/sellmarket")
  BittrexTradeResponse sellmarket(@QueryParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature,
                                  @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market,
                                  @QueryParam("quantity") String quantity) throws IOException;

  @GET
  @Path("market/cancel")
  BittrexCancelOrderResponse cancel(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                    @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("uuid") String uuid) throws IOException;

  @GET
  @Path("market/getopenorders")
  BittrexOpenOrdersResponse openorders(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                       @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market) throws IOException;

  @GET
  @Path("account/getorderhistory")
  BittrexTradeHistoryResponse getorderhistory(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                              @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market) throws IOException;

  @GET
  @Path("account/withdraw")
  BittrexWithdrawResponse withdraw(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                   @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("currency") String currency, @QueryParam("quantity") String quantity,
                                   @QueryParam("address") String address, @QueryParam("paymentid") String paymentId) throws IOException;

  @GET
  @Path("account/getwithdrawalhistory")
  BittrexWithdrawalsHistoryResponse getwithdrawalhistory(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                                         @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("account/getdeposithistory")
  BittrexDepositsHistoryResponse getdeposithistory(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
                                                   @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
}