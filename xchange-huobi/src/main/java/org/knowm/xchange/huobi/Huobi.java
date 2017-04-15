package org.knowm.xchange.huobi;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.huobi.dto.account.HuobiAccountInfo;
import org.knowm.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiOrderInfo;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

import si.mazi.rescu.ParamsDigest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Huobi {

  @POST
  HuobiAccountInfo getAccountInfo(@FormParam("access_key") String accessKey, @FormParam("created") long created, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  HuobiOrder[] getOrders(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType, @FormParam("created") long created,
      @FormParam("method") String method, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  HuobiOrderInfo getOrderInfo(@FormParam("access_key") String accessKey, @FormParam("id") long orderId, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("method") String method, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  HuobiPlaceOrderResult placeLimitOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("price") String price, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  HuobiPlaceOrderResult placeMarketOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  HuobiCancelOrderResult cancelOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("id") long id, @FormParam("method") String method,
      @FormParam("sign") ParamsDigest sign) throws IOException;
}
