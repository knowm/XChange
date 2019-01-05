package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.dto.account.BithumbWalletAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class BithumbAccountService extends BithumbAccountServiceRaw implements AccountService {

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return BithumbAdapters.adaptAccountInfo(getBithumbAddress(), getBithumbBalance());
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        return getBithumbWalletAddress(currency)
                .map(BithumbWalletAddress::getWalletAddress)
                .orElse(null);
    }

}
