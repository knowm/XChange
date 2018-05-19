package org.knowm.xchange.bl3p;

import org.knowm.xchange.bl3p.dto.account.Bl3pAccountInfo;
import org.knowm.xchange.bl3p.dto.account.Bl3pNewDepositAddress;
import org.knowm.xchange.bl3p.dto.account.Bl3pTransactionHistory;
import org.knowm.xchange.bl3p.dto.trade.Bl3pOpenOrders;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Defines methods which need authentication
 *
 * GENMKT is presumably short for GENERIC MARKET.
 */
@Path("1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bl3pAuthenticated extends Bl3p {

    /**
     * Get account info and balance
     *
     * @param restKey
     * @param restSign
     * @param nonce
     * @return
     * @throws IOException
     */
    @GET
    @Path("/GENMKT/money/info")
    Bl3pAccountInfo getAccountInfo(@HeaderParam("Rest-Key") String restKey,
                                   @HeaderParam("Rest-Sign") ParamsDigest restSign,
                                   @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

    /**
     * Create a new deposit address
     *
     * @param restKey
     * @param restSign
     * @param nonce
     * @param currency Currency (Can be: 'BTC')
     * @return
     * @throws IOException
     */
    @GET
    @Path("/GENMKT/money/new_deposit_address")
    Bl3pNewDepositAddress createNewDepositAddress(@HeaderParam("Rest-Key") String restKey,
                                                  @HeaderParam("Rest-Sign") ParamsDigest restSign,
                                                  @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                                                  @FormParam("currency") String currency) throws IOException;

    /**
     * Get your transaction history
     *
     * TODO: There are more optional parameters but these are very tedious to implement
     *
     * @param restKey
     * @param restSign
     * @param nonce
     * @param currency Currency of the wallet. (Can be: 'BTC', 'EUR')
     * @param page Page number. (1 = most recent transactions)
     * @return
     * @throws IOException
     */
    @GET
    @Path("/GENMKT/money/wallet/history")
    Bl3pTransactionHistory getTransactionHistory(@HeaderParam("Rest-Key") String restKey,
                                                 @HeaderParam("Rest-Sign") ParamsDigest restSign,
                                                 @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                                                 @FormParam("currency") String currency,
                                                 @FormParam("page") int page) throws IOException;

    @GET
    @Path("/{currencyPair}/money/orders")
    Bl3pOpenOrders getOpenOrders(@HeaderParam("Rest-Key") String restKey,
                                 @HeaderParam("Rest-Sign") ParamsDigest restSign,
                                 @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
                                 @PathParam("currencyPair") String currencyPair);

}
