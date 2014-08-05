package com.xeiam.xchange.vaultofsatoshi;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTicker;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;

/**
 * @author Michael Lagacé
 */

@Path("public")
@Produces(MediaType.APPLICATION_JSON)
public interface VaultOfSatoshi {

  @GET
  @Path("ticker?order_currency={orderCurrency}&payment_currency={paymentCurrency}")
  public VosResponse<VosTicker> getTicker(@PathParam("orderCurrency") String orderCurrency, @PathParam("paymentCurrency") String paymentCurrency) throws IOException;

  @GET
  @Path("orderbook?order_currency={orderCurrency}&payment_currency={paymentCurrency}&count=100&round=8&group_orders=0")
  public VosResponse<VosDepth> getFullDepth(@PathParam("orderCurrency") String orderCurrency, @PathParam("paymentCurrency") String paymentCurrency) throws IOException;

  @GET
  @Path("recent_transactions")
  public VosResponse<List<VosTrade>> getTrades(@QueryParam("order_currency") String orderCurrency, @QueryParam("payment_currency") String paymentCurrency, @QueryParam("since_id") Long sinceId,
      @QueryParam("count") int count) throws IOException;

}
