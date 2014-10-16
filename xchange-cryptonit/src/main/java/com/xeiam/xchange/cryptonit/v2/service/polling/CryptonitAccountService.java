package com.xeiam.xchange.cryptonit.v2.service.polling;

import com.xeiam.xchange.*;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitCoins;
import com.xeiam.xchange.cryptonit.v2.dto.account.CryptonitFunds;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitAccountService extends CryptonitAccountServiceRaw implements PollingAccountService {

    public CryptonitAccountService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

        CryptonitFunds accountFunds = getAccountFunds();
        CryptonitCoins accountCoins = getAccountCoins();

        return CryptonitAdapters.adaptAccountInfo(accountFunds, accountCoins);
    }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }
}
