package org.xchange.kraken.service.polling;

import java.math.BigDecimal;

import si.mazi.rescu.RestJsonException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class KrakenPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

    protected KrakenPollingAccountService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
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
