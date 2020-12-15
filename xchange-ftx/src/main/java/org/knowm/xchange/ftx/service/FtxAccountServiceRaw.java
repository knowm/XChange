package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.account.*;

import java.io.IOException;
import java.net.URLEncoder;

public class FtxAccountServiceRaw extends FtxBaseService{

    public FtxAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public FtxAccountResponse getFtxBalance() throws FtxException,IOException {

        return ftx.getAccount(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            null
        );
    }

    public FtxSubAccountBalanceResponse getFtxSubAccountBalances(String nickname) throws FtxException,IOException{
        return ftx.getSubAccountBalances(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            null,
            nickname
        );
    }

    public FtxSubAccountResponse createFtxSubAccount(String nickname)throws FtxException,IOException {
        return ftx.createSubAccount(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            null,
            new FtxSubAccountRequestPOJO(nickname)
        );
    }

    public FtxSubAccountTransferResponse transferBetweenFtxSubAccount(FtxSubAccountTransferPOJO payload) throws FtxException,IOException{
        return ftx.transferBetweenSubAccounts(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            null,
            payload
        );
    }

    public FtxLeverageResponse changeLeverage(String subaccount, int leverage) throws FtxException,IOException {
        return ftx.changeLeverage(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            URLEncoder.encode(subaccount, "UTF-8"),
            new FtxLeverageDto(leverage)
        );
    }
}
