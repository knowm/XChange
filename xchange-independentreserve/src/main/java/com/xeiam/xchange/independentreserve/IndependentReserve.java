package com.xeiam.xchange.independentreserve;

import com.xeiam.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Author: Kamil Zbikowski
 * Date: 4/9/15
 */
@Path("Public")
@Produces(MediaType.APPLICATION_JSON)
public interface IndependentReserve {

    @GET
    @Path("/GetOrderBook")
    public IndependentReserveOrderBook getOrderBook(@QueryParam("primaryCurrencyCode") String primaryCurrencyCode,
                                                    @QueryParam("secondaryCurrencyCode") String secondaryCurrencyCode) throws IOException;

//    @GET
//    @Path("ticker/")
//    public IndependentReserveTicker getTicker() throws IOException;
//
//    /**
//     * Returns descending list of transactions.
//     */
//    @GET
//    @Path("transactions/")
//    public IndependentReserveTransaction[] getTransactions() throws IOException;
//
//    /**
//     * Returns descending list of transactions.
//     */
//    @GET
//    @Path("transactions/")
//    public IndependentReserveTransaction[] getTransactions(@QueryParam("time") String time) throws IOException;
}
