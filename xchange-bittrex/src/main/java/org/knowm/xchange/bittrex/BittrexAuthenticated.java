package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bittrex.dto.BittrexBaseReponse;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositAddress;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositHistory;
import org.knowm.xchange.bittrex.dto.account.BittrexOrder;
import org.knowm.xchange.bittrex.dto.account.BittrexWithdrawUuid;
import org.knowm.xchange.bittrex.dto.account.BittrexWithdrawalHistory;
import org.knowm.xchange.bittrex.dto.trade.BittrexOpenOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexTradeId;
import org.knowm.xchange.bittrex.dto.trade.BittrexUserTrade;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1.1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticated extends Bittrex {

  @GET
  @Path("account/getdepositaddress")
  BittrexBaseReponse<BittrexDepositAddress> getdepositaddress(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency)
      throws BittrexException, IOException;

  @GET
  @Path("account/getbalances")
  BittrexBaseReponse<List<BittrexBalance>> getBalances(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BittrexException, IOException;

  @GET
  @Path("/account/getbalance")
  BittrexBaseReponse<BittrexBalance> getBalance(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency)
      throws BittrexException, IOException;

  @GET
  @Path("market/buylimit")
  BittrexBaseReponse<BittrexTradeId> buylimit(
      @QueryParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market,
      @QueryParam("quantity") String quantity,
      @QueryParam("rate") String rate)
      throws BittrexException, IOException;

  @GET
  @Path("market/selllimit")
  BittrexBaseReponse<BittrexTradeId> selllimit(
      @QueryParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market,
      @QueryParam("quantity") String quantity,
      @QueryParam("rate") String rate)
      throws BittrexException, IOException;

  @GET
  @Path("market/buymarket")
  BittrexBaseReponse<BittrexTradeId> buymarket(
      @QueryParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market,
      @QueryParam("quantity") String quantity)
      throws BittrexException, IOException;

  @GET
  @Path("market/sellmarket")
  BittrexBaseReponse<BittrexTradeId> sellmarket(
      @QueryParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market,
      @QueryParam("quantity") String quantity)
      throws BittrexException, IOException;

  @GET
  @Path("market/cancel")
  BittrexBaseReponse cancel(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("uuid") String uuid)
      throws BittrexException, IOException;

  @GET
  @Path("market/getopenorders")
  BittrexBaseReponse<List<BittrexOpenOrder>> openorders(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market)
      throws BittrexException, IOException;

  @GET
  @Path("/account/getorder")
  BittrexBaseReponse<BittrexOrder> getOrder(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("uuid") String uuid)
      throws BittrexException, IOException;

  @GET
  @Path("account/getorderhistory")
  BittrexBaseReponse<List<BittrexUserTrade>> getorderhistory(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market)
      throws BittrexException, IOException;

  @GET
  @Path("account/withdraw")
  BittrexBaseReponse<BittrexWithdrawUuid> withdraw(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency,
      @QueryParam("quantity") String quantity,
      @QueryParam("address") String address,
      @QueryParam("paymentid") String paymentId)
      throws BittrexException, IOException;

  @GET
  @Path("account/getwithdrawalhistory")
  BittrexBaseReponse<List<BittrexWithdrawalHistory>> getwithdrawalhistory(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency)
      throws BittrexException, IOException;

  @GET
  @Path("account/getdeposithistory")
  BittrexBaseReponse<List<BittrexDepositHistory>> getdeposithistory(
      @QueryParam("apikey") String apiKey,
      @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("currency") String currency)
      throws BittrexException, IOException;
}
