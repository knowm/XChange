package org.knowm.xchange.coinsuper;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// import org.knowm.xchange.binance.dto.BinanceException;
// import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
// import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
// import org.knowm.xchange.binance.dto.marketdata.BinancePrice;
// import org.knowm.xchange.binance.dto.marketdata.BinancePriceQuantity;
// import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
// import org.knowm.xchange.binance.dto.meta.BinanceTime;
// import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;

@Path("api/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface Coinsuper {

  @GET
  @Path("pubticker/btcusd")
  /**
   * Test connectivity to the Rest API. BTC/USD
   *
   * @return
   * @throws IOException
   */
  String getTickerString() throws IOException;
}
