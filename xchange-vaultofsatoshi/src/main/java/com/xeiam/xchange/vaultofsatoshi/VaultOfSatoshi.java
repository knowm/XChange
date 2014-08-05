package com.xeiam.xchange.vaultofsatoshi;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTicker;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;

/**
 * @author Michael Lagacé
 */
@Path("public")
@Produces(MediaType.APPLICATION_JSON)
public interface VaultOfSatoshi {

  @GET
  @Path("ticker")
  public VosResponse<VosTicker> getTicker(@QueryParam("order_currency") String orderCurrency, @QueryParam("payment_currency") String paymentCurrency) throws IOException;

  @GET
  @Path("orderbook")
  public VosResponse<VosDepth> getFullDepth(@QueryParam("order_currency") String orderCurrency, @QueryParam("payment_currency") String paymentCurrency,
      @Nullable @QueryParam("round") @DefaultValue("2") int round, @Nullable @QueryParam("count") @DefaultValue("20") int count,
      @Nullable @QueryParam("group_orders") @DefaultValue("1") int group_orders) throws IOException;

  @GET
  @Path("recent_transactions")
  public VosResponse<VosTrade[]> getTrades(@QueryParam("order_currency") String orderCurrency, @QueryParam("payment_currency") String paymentCurrency, @QueryParam("since_id") Long sinceId,
      @QueryParam("count") @DefaultValue("100") int count) throws IOException;

}
