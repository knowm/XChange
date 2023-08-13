package org.knowm.xchange.ccex;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.ccex.dto.account.CCEXBalanceResponse;
import org.knowm.xchange.ccex.dto.account.CCEXBalancesResponse;
import org.knowm.xchange.ccex.dto.trade.CCEXBuySellLimitResponse;
import org.knowm.xchange.ccex.dto.trade.CCEXCancelResponse;
import org.knowm.xchange.ccex.dto.trade.CCEXGetopenordersResponse;
import org.knowm.xchange.ccex.dto.trade.CCEXGetorderhistoryResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("t")
@Produces(MediaType.APPLICATION_JSON)
public interface CCEXAuthenticated extends CCEX {

  @GET
  @Path("api.html?a=getbalances&apikey={apikey}&nonce={nonce}")
  CCEXBalancesResponse balances(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @GET
  @Path("api.html?a=getbalance&apikey={apikey}&nonce={nonce}&currency={currency}")
  CCEXBalanceResponse getdepositaddress(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("api.html?a=getopenorders&apikey={apikey}&nonce={nonce}")
  CCEXGetopenordersResponse getopenorders(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @GET
  @Path("api.html?a=getorderhistory&apikey={apikey}&nonce={nonce}")
  CCEXGetorderhistoryResponse getorderhistory(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @GET
  @Path("api.html?a=cancel&apikey={apikey}&nonce={nonce}&uuid={uuid}")
  CCEXCancelResponse cancel(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("uuid") String uuid)
      throws IOException;

  @GET
  @Path(
      "api.html?a=buylimit&apikey={apikey}&nonce={nonce}&market={lmarket}-{rmarket}&quantity={quantity}&rate={rate}")
  CCEXBuySellLimitResponse buylimit(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("lmarket") String lmarket,
      @PathParam("rmarket") String rmarket,
      @PathParam("quantity") String quantity,
      @PathParam("rate") String rate)
      throws IOException;

  @GET
  @Path(
      "api.html?a=selllimit&apikey={apikey}&nonce={nonce}&market={lmarket}-{rmarket}&quantity={quantity}&rate={rate}")
  CCEXBuySellLimitResponse selllimit(
      @PathParam("apikey") String apikey,
      @HeaderParam("apisign") ParamsDigest signature,
      @PathParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("lmarket") String lmarket,
      @PathParam("rmarket") String rmarket,
      @PathParam("quantity") String quantity,
      @PathParam("rate") String rate)
      throws IOException;
}