package org.xchange.kraken.service.polling;

import java.math.BigDecimal;

import org.xchange.kraken.KrakenAdapters;
import org.xchange.kraken.KrakenAuthenticated;
import org.xchange.kraken.KrakenUtils;
import org.xchange.kraken.dto.account.KrakenBalanceResult;
import org.xchange.kraken.service.KrakenDigest;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestJsonException;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.utils.Assert;

public class KrakenPollingAccountService extends BasePollingExchangeService implements PollingAccountService {
    private KrakenAuthenticated krakenAuthenticated;
    private ParamsDigest signatureCreator;
    public KrakenPollingAccountService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
        Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
        krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
        signatureCreator=KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, RestJsonException {
      
     KrakenBalanceResult result = krakenAuthenticated.getBalance(exchangeSpecification.getApiKey(), signatureCreator,KrakenUtils.getNonce());
     
     return KrakenAdapters.adaptBalance(result, exchangeSpecification.getUserName());
    }

    @Override
    public String withdrawFunds(BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException,
            NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String requestBitcoinDepositAddress(String... arguments) throws ExchangeException, NotAvailableFromExchangeException,
            NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

}
