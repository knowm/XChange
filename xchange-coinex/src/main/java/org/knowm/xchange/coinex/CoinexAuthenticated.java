package org.knowm.xchange.coinex;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.CoinexResponse;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;
import org.knowm.xchange.coinex.dto.account.CoinexOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinexAuthenticated {

  @GET
  @Path("v2/assets/spot/balance")
  CoinexResponse<List<CoinexBalanceInfo>> balances(
      @HeaderParam("X-COINEX-KEY") String apiKey,
      @HeaderParam("X-COINEX-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("X-COINEX-SIGN") ParamsDigest signer)
      throws IOException, CoinexException;


  @GET
  @Path("v2/spot/order-status")
  CoinexResponse<CoinexOrder> orderStatus(
      @HeaderParam("X-COINEX-KEY") String apiKey,
      @HeaderParam("X-COINEX-TIMESTAMP") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("X-COINEX-SIGN") ParamsDigest signer,
      @QueryParam("market") String market,
      @QueryParam("order_id") String orderId)
      throws IOException, CoinexException;


}
