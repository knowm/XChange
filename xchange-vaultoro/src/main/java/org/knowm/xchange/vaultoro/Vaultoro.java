package org.knowm.xchange.vaultoro;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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