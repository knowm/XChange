package org.knowm.xchange.bitmex;


public interface Bitmex {

/*    @GET
    @Path("user")
    BitmexAccount getAccount(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce, @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest)
            throws IOException;*/
/*

    @GET
    @Path("user/wallet")
    BitmexWallet getWallet(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce, @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
                           @Nullable @PathParam("currency") String currency) throws IOException;
*/

/*
    // Get a history of all of your wallet transactions (deposits, withdrawals, PNL)
    @GET
    @Path("user/walletHistory")
    List<BitmexWalletTransaction> getWalletHistory(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
                                                   @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest, @Nullable @PathParam("currency") String currency) throws IOException;
*/

/*    // Get a summary of all of your wallet transactions (deposits, withdrawals, PNL)
    @GET
    @Path("user/walletSummary")
    List<BitmexWalletTransaction> getWalletSummary(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
                                                   @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest, @Nullable @PathParam("currency") String currency) throws IOException;

 *//*   @GET
    @Path("user/margin")
    BitmexMarginAccount getMarginAccountStatus(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
                                               @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest, @Nullable @PathParam("currency") String currency) throws IOException;
*/
/*
    @GET
    @Path("user/margin?currency=all")
    List<BitmexMarginAccount> getMarginAccountsStatus(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce,
                                                      @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest) throws IOException;
*/

 /*   @GET
    @Path("trade")
    BitmexPublicTrade[] getTrades(@QueryParam("symbol") String currencyPair, @QueryParam("reverse") Boolean reverse) throws IOException;
*/
/*
    @GET
    @Path("orderBook/L2")
    BitmexPublicOrder[] getDepth(@QueryParam("symbol") String currencyPair, @QueryParam("depth") Double depth) throws IOException;
*/
/*

    @GET
    @Path("position")
    List<BitmexPosition> getPositions(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce, @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest)
            throws IOException;
*/
/*

    @GET
    @Path("position")
    List<BitmexPosition> getPositions(@HeaderParam("API-KEY") String apiKey, @HeaderParam("API-NONCE") SynchronizedValueFactory<Long> nonce, @HeaderParam("API-SIGNATURE") ParamsDigest paramsDigest,
                                      @Nullable @QueryParam("symbol") String symbol, @Nullable @QueryParam("filter") String filter) throws IOException;
*/
/*
    @GET
    @Path("instrument")
    List<BitmexTicker> getTickers() throws IOException, BitmexException;*/
/*

    @GET
    @Path("instrument")
    List<BitmexTicker> getTicker(@PathParam("symbol") String symbol) throws IOException, BitmexException;
*/
/*

    @GET
    @Path("instrument/active")
    List<BitmexTicker> getActiveTickers() throws IOException, BitmexException;
*/
/*
    @GET
    @Path("instrument/activeIntervals")
    BitmexSymbolsAndPromptsResult getPromptsAndSymbols() throws IOException, BitmexException;*/
}