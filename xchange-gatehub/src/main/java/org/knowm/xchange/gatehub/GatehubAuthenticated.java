package org.knowm.xchange.gatehub;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.gatehub.dto.Account;
import org.knowm.xchange.gatehub.dto.Balance;
import org.knowm.xchange.gatehub.dto.BearerToken;
import org.knowm.xchange.gatehub.dto.GatehubException;
import org.knowm.xchange.gatehub.dto.Payment;

@Produces(MediaType.APPLICATION_JSON)
@Path("core/v1")
public interface GatehubAuthenticated {

  @GET
  @Path("wallets/{walletAddress}/balances")
  List<Balance> getBalances(
      @HeaderParam("Authorization") BearerToken token,
      @PathParam("walletAddress") String walletAddress
  ) throws IOException, GatehubException;

  /**
   * This is not necessary, it is just used for browser-based cross-origin access policy.
   */
  @OPTIONS
  @Path("transactions")
  Payment authorizePayment(
            @HeaderParam("Access-Control-Request-Headers") String acrh,
            @HeaderParam("Access-Control-Request-Method") String acrm,
            @HeaderParam("Origin") String origin,
            @HeaderParam("Referer") String referer
  ) throws IOException, GatehubException;

  @POST
  @Path("transactions")
  @Consumes(MediaType.APPLICATION_JSON)
  Payment pay(
      @HeaderParam("Authorization") BearerToken token,
      Payment request
  ) throws IOException, GatehubException;

  @POST
  @Path("users/{userUid}/accounts")
  @Consumes(MediaType.APPLICATION_JSON)
  Account getAccount(
      @HeaderParam("Authorization") BearerToken token,
      @PathParam("userUid") String userUid,
      Account query
  ) throws IOException, GatehubException;
}
