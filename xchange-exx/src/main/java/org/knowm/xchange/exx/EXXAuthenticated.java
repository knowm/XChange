package org.knowm.xchange.exx;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.exx.dto.EXXException;
import org.knowm.xchange.exx.dto.account.EXXAccountInformation;
import org.knowm.xchange.exx.dto.trade.EXXCancelOrder;
import org.knowm.xchange.exx.dto.trade.EXXOrder;
import org.knowm.xchange.exx.dto.trade.EXXPlaceOrder;

/** @author kevingates */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
public interface EXXAuthenticated extends EXX {

  public static final String API_KEY = "apikey";
  public static final String API_SIGNATURE = "signature";

  @GET
  @Path("getBalance")
  EXXAccountInformation getAccountInfo(
      @QueryParam("accesskey") String accesskey,
      @QueryParam("nonce") Long nonce,
      @QueryParam("signature") String signature);

  // accesskey=accesskey&amount=1.5&currency=eth_hsr&nonce=Current time in
  // milliseconds&price=1024&type=buy
  @GET
  @Path("order")
  EXXPlaceOrder placeLimitOrder(
      @QueryParam("accesskey") String accesskey,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("currency") String currency,
      @QueryParam("nonce") Long nonce,
      @QueryParam("price") BigDecimal price,
      @QueryParam("type") String type,
      @QueryParam("signature") String signature);

  /// api/cancel?accesskey=your_access_key&currency=eth_hsr&id=123456789&nonce=Curren
  @GET
  @Path("cancel")
  EXXCancelOrder cancelOrder(
      @QueryParam("accesskey") String accesskey,
      @QueryParam("currency") String currency,
      @QueryParam("id") String id,
      @QueryParam("nonce") Long nonce,
      @QueryParam("signature") String signature)
      throws IOException, EXXException;

  /**
   * accesskey=your_access_key&currency=eth_hsr&nonce=Current time in
   * milliseconds&pageIndex=1&type=buy
   *
   * @param apiKey
   * @param signature
   * @param market
   * @param cursor
   * @return
   * @throws IOException
   * @throws EXXException
   */
  @GET
  @Path("getOpenOrders")
  List<EXXOrder> getOpenOrders(
      @QueryParam("accesskey") String accesskey,
      @QueryParam("currency") String currency,
      @QueryParam("nonce") Long nonce,
      @QueryParam("pageIndex") Integer pageIndex,
      @QueryParam("type") String type,
      @QueryParam("signature") String signature)
      throws IOException, EXXException;
}
