package org.knowm.xchange.vaultoro;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroOrderBookResponse;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Vaultoro {

  @GET
  @Path("latest")
  BigDecimal getLatest() throws IOException, VaultoroException;

  @GET
  @Path("orderbook")
  VaultoroOrderBookResponse getVaultoroOrderBook() throws IOException, VaultoroException;

  @GET
  @Path("transactions/month")
  List<VaultoroTrade> getVaultoroTrades(String time) throws IOException, VaultoroException;
}
