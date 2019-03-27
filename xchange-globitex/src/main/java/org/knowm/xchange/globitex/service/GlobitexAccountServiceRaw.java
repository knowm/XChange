package org.knowm.xchange.globitex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.dto.GlobitexException;
import org.knowm.xchange.globitex.dto.account.GlobitexAccounts;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;

public class GlobitexAccountServiceRaw extends GlobitexBaseService {

    public GlobitexAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public GlobitexAccounts getGlobitexAccounts() throws IOException{
        try{
            return globitex.getAccounts(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory(),
                    signatureCreator);
        }catch (HttpStatusIOException e){
            throw new ExchangeException(e.getHttpBody());
        }

    }
}
