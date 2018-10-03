package org.knowm.xchange.btctrade;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.btctrade.dto.BTCTradeResult;
import org.knowm.xchange.btctrade.dto.BTCTradeSecretResponse;
import org.knowm.xchange.btctrade.dto.account.BTCTradeBalance;
import org.knowm.xchange.btctrade.dto.account.BTCTradeWallet;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import org.knowm.xchange.btctrade.dto.trade.BTCTradeOrder;
import org.knowm.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCTrade {

  /**
   * Returns the quotations.
   *
   * @return the quotations.
   */
  @GET
  @Path("ticker")
  BTCTradeTicker getTicker() throws IOException;

  /**
   * Returns the depth of the market.
   *
   * @return the depth of the market.
   */
  @GET
  @Path("depth")
  BTCTradeDepth getDepth() throws IOException;

  /**
   * Returns 500 recent market transactions, in reverse chronological order.
   *
   * @return 500 recent market transactions.
   */
  @GET
  @Path("trades")
  BTCTradeTrade[] getTrades() throws IOException;

  /**
   * Returns 500 market transactions which trade ID is greater than {@code since}, in reverse
   * chronological order.
   *
   * @param since the trade ID.
   * @return 500 market transactions which trade ID is grater than the given ID.
   */
  @GET
  @Path("trades")
  BTCTradeTrade[] getTrades(@QueryParam("since") long since) throws IOException;

  /**
   * Returns the secret for signing.
   *
   * @param passphrase the API private key.
   * @param key the API public key.
   * @return the secret for signing.
   */
  @POST
  @Path("getsecret")
  BTCTradeSecretResponse getSecret(
      @FormParam("api_passphrase") String passphrase, @FormParam("key") String key)
      throws IOException;

  /**
   * Returns the account balance.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature
   * @return the account balance.
   */
  @POST
  @Path("balance")
  BTCTradeBalance getBalance(
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Returns the deposit address.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @return the deposit address.
   */
  @POST
  @Path("wallet")
  BTCTradeWallet getWallet(
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Return orders.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param since unix timestamp(UTC timezone). Default is 0, returns all.
   * @param type the order type: open, all.
   */
  @POST
  @Path("orders")
  BTCTradeOrder[] getOrders(
      @FormParam("since") long since,
      @FormParam("type") String type,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Returns order information.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param id the order ID.
   */
  @POST
  @Path("fetch_order")
  BTCTradeOrder getOrder(
      @FormParam("id") String id,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Cancels order.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param id the order ID.
   */
  @POST
  @Path("cancel_order")
  BTCTradeResult cancelOrder(
      @FormParam("id") String id,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Places a buy order.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param amount the quantity to buy.
   * @param price the price to buy.
   */
  @POST
  @Path("buy")
  BTCTradePlaceOrderResult buy(
      @FormParam("amount") String amount,
      @FormParam("price") String price,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  /**
   * Places a sell order.
   *
   * @param nonce the nonce.
   * @param key the API public key.
   * @param signature the signature.
   * @param amount the quantity to sell.
   * @param price the price to sell.
   */
  @POST
  @Path("sell")
  BTCTradePlaceOrderResult sell(
      @FormParam("amount") String amount,
      @FormParam("price") String price,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("key") String key,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;
}
