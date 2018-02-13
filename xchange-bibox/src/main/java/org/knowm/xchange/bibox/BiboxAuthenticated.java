package org.knowm.xchange.bibox;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bibox.dto.BiboxSingleResponse;
import org.knowm.xchange.bibox.dto.account.BiboxCoin;
import org.knowm.xchange.bibox.dto.trade.BiboxOrders;

import si.mazi.rescu.ParamsDigest;

/**
 * @author odrotleff
 */
@Path("v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BiboxAuthenticated extends Bibox {

  static final String FORM_CMDS = "cmds";
  static final String FORM_APIKEY = "apikey";
  static final String FORM_SIGNATURE = "sign";

  /**
   * Retrieve balances of the account
   * 
   * @return list of coins
   */
  @POST
  @Path("transfer")
  BiboxSingleResponse<List<BiboxCoin>> coinList(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Get deposit address for coin
   * 
   * @return list of coins
   */
  @POST
  @Path("transfer")
  BiboxSingleResponse<String> depositAddress(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Create an order (market/limit)
   * 
   * @return order id
   */
  @POST
  @Path("orderpending")
  BiboxSingleResponse<Integer> trade(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Cancel an order
   * 
   * @return chinese for "cancelled", useless
   */
  @POST
  @Path("orderpending")
  BiboxSingleResponse<String> cancelTrade(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);

  /**
   * Obtain open order list / order history
   * 
   * @return open orders
   */
  @POST
  @Path("orderpending")
  BiboxSingleResponse<BiboxOrders> orderPendingList(
      @FormParam(FORM_CMDS) String cmds,
      @FormParam(FORM_APIKEY) String apiKey,
      @FormParam(FORM_SIGNATURE) ParamsDigest signature);
}
