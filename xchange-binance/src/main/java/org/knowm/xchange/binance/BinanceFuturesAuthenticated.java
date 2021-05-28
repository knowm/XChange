package org.knowm.xchange.binance;

import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BinanceFuturesAuthenticated extends BinanceAuthenticated {
    @Override
    @GET
    @Path("fapi/v1/exchangeInfo")
    /**
     * Current exchange trading rules and symbol information.
     *
     * @return
     * @throws IOException
     */
    BinanceExchangeInfo exchangeInfo() throws IOException;

    @Override
    @GET
    @Path("fapi/v1/depth")
    /**
     * @param symbol
     * @param limit optional, default 100 max 5000. Valid limits: [5, 10, 20, 50, 100, 500, 1000,
     *     5000]
     * @return
     * @throws IOException
     * @throws BinanceException
     */
    BinanceOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit) throws IOException, BinanceException;

    // TODO: Shift other paths, or mark as unavailable
}
