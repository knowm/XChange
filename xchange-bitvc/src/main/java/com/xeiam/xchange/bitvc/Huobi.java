package com.xeiam.xchange.bitvc;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitvc.dto.account.HuobiAccountInfo;
import com.xeiam.xchange.bitvc.dto.trade.BitVcCancelOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Huobi {

  @POST
  public HuobiAccountInfo getAccountInfo(@FormParam("access_key") String accessKey, @FormParam("created") long created,
      @FormParam("method") String method, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public BitVcOrder[] getOrders(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType, @FormParam("created") long created,
      @FormParam("method") String method, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public BitVcPlaceOrderResult placeLimitOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("price") String price, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public BitVcPlaceOrderResult placeMarketOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  public BitVcCancelOrderResult cancelOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("id") long id, @FormParam("method") String method, @FormParam("sign") ParamsDigest sign)
      throws IOException;
}
