package org.knowm.xchange.bitbay;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitbay.dto.acount.BitbayAccountInfoResponse;
import org.knowm.xchange.bitbay.dto.trade.BitbayCancelResponse;
import org.knowm.xchange.bitbay.dto.trade.BitbayOrder;
import org.knowm.xchange.bitbay.dto.trade.BitbayTradeResponse;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Z. Dolezal
 */
@Path("/Trading/tradingApi.php")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitbayAuthenticated {

  /**
   * info - returns information about account balances
   */
  @POST
  @FormParam("method")
  BitbayAccountInfoResponse info(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("moment") SynchronizedValueFactory<Long> timestamp) throws IOException;

  /**
   * trade - places offer at the stock market
   * <p>
   * Input:
   * <p>
   * type : offer type bid/buy or ask/sell currency : shortcut of main currency for offer (e.g. “BTC”) amount : quantity of main currency
   * payment_currency : shortcut of currency used to pay for offer (e.g. “PLN”) rate : rate for offer
   */
  @POST
  @FormParam("method")
  BitbayTradeResponse trade(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("moment") SynchronizedValueFactory<Long> timestamp, @FormParam("type") String type, @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount, @FormParam("payment_currency") String paymentCurrency,
      @FormParam("rate") BigDecimal rate) throws IOException;

  /**
   * cancel - removes offer from the stock market
   * <p>
   * Input:
   * <p>
   * id : id used to recognize offer; you get it from trade method output
   */
  @POST
  @FormParam("method")
  BitbayCancelResponse cancel(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("moment") SynchronizedValueFactory<Long> timestamp, @FormParam("id") long id) throws IOException;

  /**
   * orders - list of all your offers
   * <p>
   * Input:
   * <p>
   * (optional) limit : number of rows to show; if no specified, returns latest 50 orders
   */
  @POST
  @FormParam("method")
  List<BitbayOrder> orders(@HeaderParam("API-Key") String apiKey, @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("moment") SynchronizedValueFactory<Long> timestamp) throws IOException;

}
