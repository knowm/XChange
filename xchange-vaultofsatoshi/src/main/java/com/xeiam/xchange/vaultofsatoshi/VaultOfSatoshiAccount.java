package com.xeiam.xchange.vaultofsatoshi;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.vaultofsatoshi.dto.account.VosAccount;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosWalletAddress;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosWalletHistory;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosOrderDetail;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosTradeOrder;

/**
 * @author Michael Lagacé
 */

@Path("info")
@Produces(MediaType.APPLICATION_JSON)
public interface VaultOfSatoshiAccount {

  @POST
  @Path("account")
  public VosResponse<VosAccount> getAccount(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce) throws IOException;

  @POST
  @Path("balance")
  public VosResponse<VosCurrency>
      getBalance(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("currency") String currency) throws IOException;

  @POST
  @Path("orders")
  public VosResponse<VosTradeOrder[]> getOpenOrders(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("count") int count, /*
                                                                                                                                                                                                         * @
                                                                                                                                                                                                         * FormParam
                                                                                                                                                                                                         * (
                                                                                                                                                                                                         * "before"
                                                                                                                                                                                                         * )
                                                                                                                                                                                                         * long
                                                                                                                                                                                                         * before
                                                                                                                                                                                                         * ,
                                                                                                                                                                                                         * @
                                                                                                                                                                                                         * FormParam
                                                                                                                                                                                                         * (
                                                                                                                                                                                                         * "after"
                                                                                                                                                                                                         * )
                                                                                                                                                                                                         * long
                                                                                                                                                                                                         * after
                                                                                                                                                                                                         * ,
                                                                                                                                                                                                         * @
                                                                                                                                                                                                         * FormParam
                                                                                                                                                                                                         * (
                                                                                                                                                                                                         * "before_id"
                                                                                                                                                                                                         * )
                                                                                                                                                                                                         * long
                                                                                                                                                                                                         * before_id
                                                                                                                                                                                                         * ,
                                                                                                                                                                                                         */
      @FormParam("open_only") String open_orders) throws IOException;

  @POST
  @Path("orders")
  public VosResponse<VosTradeOrder[]> getOrders(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce, @FormParam("count") int count/*
                                                                                                                                                                                                   * , @
                                                                                                                                                                                                   * FormParam
                                                                                                                                                                                                   * (
                                                                                                                                                                                                   * "before"
                                                                                                                                                                                                   * )
                                                                                                                                                                                                   * long
                                                                                                                                                                                                   * before
                                                                                                                                                                                                   * , @
                                                                                                                                                                                                   * FormParam
                                                                                                                                                                                                   * (
                                                                                                                                                                                                   * "after"
                                                                                                                                                                                                   * )
                                                                                                                                                                                                   * long
                                                                                                                                                                                                   * after
                                                                                                                                                                                                   * , @
                                                                                                                                                                                                   * FormParam
                                                                                                                                                                                                   * (
                                                                                                                                                                                                   * "before_id"
                                                                                                                                                                                                   * )
                                                                                                                                                                                                   * long
                                                                                                                                                                                                   * before_id
                                                                                                                                                                                                   */)
      throws IOException;

  @POST
  @Path("order_detail")
  public VosResponse<VosOrderDetail> getOrderDetails(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("order_id") int order_id) throws IOException;

  @POST
  @Path("wallet_address")
  public VosResponse<VosWalletAddress> getWalletAddress(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("currency") String currency) throws IOException;

  @POST
  @Path("wallet_history")
  public VosResponse<VosWalletHistory> getWalletHistory(@HeaderParam("Api-Key") String apiKey, @HeaderParam("Api-Sign") ParamsDigest signer, @FormParam("nonce") long nonce,
      @FormParam("currency") String currency, @FormParam("count") int count, @FormParam("after") long after) throws IOException;

}
