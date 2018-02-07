package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.BitmexMargin;
import org.knowm.xchange.bitmex.dto.BitmexTransaction;
import org.knowm.xchange.bitmex.dto.BitmexUser;
import org.knowm.xchange.bitmex.dto.BitmexWallet;
import org.knowm.xchange.bitmex.util.ApiException;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;
import java.util.List;

public class BitmexAccountServiceRaw extends BitmexBaseService {

    public static final UserApi USER_API = new UserApi();
    String apiKey;

    /**
     * Constructor
     *
     * @param exchange
     */

    public BitmexAccountServiceRaw(Exchange exchange) {
        super(exchange);

    }

    public BitmexUser getBitmexAccountInfo() throws IOException {

        try {
            return USER_API.userGet( );
        } catch ( Exception e) {
            throw handleError(e);
         }
    }

    public BitmexWallet getBitmexWallet(Currency ccy) throws IOException {

        BitmexWallet bitmexWallet;
        try {
            bitmexWallet = USER_API.userGetWallet(ccy.getCurrencyCode());
        } catch (BitmexException | ApiException e) {
            throw handleError(e);
        }

        return bitmexWallet;
    }

    public List<BitmexTransaction> getBitmexWalletHistory(Currency ccy) throws IOException {

        try {
            return USER_API.userGetWalletHistory( ccy.getCurrencyCode());
        } catch ( Exception e) {
            throw handleError(e);
        }
    }

    public List<BitmexTransaction> getBitmexWalletSummary(Currency ccy) throws IOException {

        try {
            return USER_API.userGetWalletSummary( ccy.getCurrencyCode());
        } catch ( Exception e) {
            throw handleError(e);
        }
    }
/*
    public List<BitmexMargin> getBitmexMarginAccountStatus(  ) throws IOException {

        try {
            return (List<BitmexMargin>) USER_API.userGetMargin(  "all");
        } catch (Exception e) {
            throw handleError(e);
        }
    }*/
/**
//jn: there is only one currency and it is btc.  this is too polymorphic for java to return for "all"
   */
public BitmexMargin getBitmexMarginAccountStatus(Currency  ccy ) throws IOException {

        try {
            return USER_API.userGetMargin( null);
        } catch (Exception e) {
            throw handleError(e);
        }
    }

}
