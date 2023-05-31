package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v4")
@Produces(MediaType.APPLICATION_JSON)
public interface GateioV4Authenticated {

  @GET
  @Path("wallet/deposit_address")
  GateioDepositAddress getDepositAddress(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


  @GET
  @Path("wallet/withdraw_status")
  List<GateioWithdrawStatus> getWithdrawStatus(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


  @GET
  @Path("spot/accounts")
  List<GateioCurrencyBalance> getSpotAccounts(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


}
