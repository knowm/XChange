package org.xchange.kraken.service.polling;

import java.math.BigDecimal;

import org.xchange.kraken.Kraken;

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
    private Kraken kraken;
    public KrakenPollingAccountService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
        Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
        kraken = RestProxyFactory.createProxy(Kraken.class, exchangeSpecification.getSslUri());
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, RestJsonException {
       throw new NotYetImplementedForExchangeException();
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
