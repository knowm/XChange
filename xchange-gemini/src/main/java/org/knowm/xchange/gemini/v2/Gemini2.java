package org.knowm.xchange.gemini.v2;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v2.dto.marketdata.GeminiCandle;
import org.knowm.xchange.gemini.v2.dto.marketdata.GeminiTicker2;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Gemini2 {
  @GET
  @Path("candles/{symbol}/{time_frame}")
  GeminiCandle[] getCandles(
      @PathParam("symbol") String symbol, @PathParam("time_frame") String time_frame)
      throws IOException, GeminiException;

  @GET
  @Path("ticker/{symbol}")
  GeminiTicker2 getTicker(@PathParam("symbol") String symbol) throws IOException, GeminiException;
}
