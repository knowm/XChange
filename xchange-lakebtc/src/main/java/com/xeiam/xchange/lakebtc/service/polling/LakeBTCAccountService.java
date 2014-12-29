package com.xeiam.xchange.lakebtc.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.lakebtc.LakeBTCAdapters;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.service.polling.PollingAccountService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCAccountService extends LakeBTCAccountServiceRaw implements PollingAccountService {


    /**
     * Constructor
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    public LakeBTCAccountService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {
        super(exchangeSpecification, tonceFactory);
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        LakeBTCAccountInfoResponse response = super.getLakeBTCAccountInfo();
        return LakeBTCAdapters.adaptAccountInfo(response.getResult());
    }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }
}
