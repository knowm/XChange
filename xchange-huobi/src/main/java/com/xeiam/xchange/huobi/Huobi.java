package com.xeiam.xchange.huobi;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.huobi.dto.account.HuobiAccountInfo;
import com.xeiam.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import com.xeiam.xchange.huobi.dto.trade.HuobiOrder;
import com.xeiam.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Huobi {

  @POST
  public HuobiAccountInfo getAccountInfo(@FormParam("access_key") String accessKey, @FormParam("created") long created,
      @FormParam("method") String method, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public HuobiOrder[] getOrders(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType, @FormParam("created") long created,
      @FormParam("method") String method, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public HuobiPlaceOrderResult placeLimitOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("price") String price, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public HuobiPlaceOrderResult placeMarketOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public HuobiCancelOrderResult cancelOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("id") long id, @FormParam("method") String method, @FormParam("sign") ParamsDigest sign)
      throws IOException;
}
