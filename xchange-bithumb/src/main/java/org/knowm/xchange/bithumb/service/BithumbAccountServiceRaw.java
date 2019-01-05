package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbWalletAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.Optional;

public class BithumbAccountServiceRaw {

    public BithumbBalance getBithumbBalance() {
        throw new NotYetImplementedForExchangeException();
    }

    public BithumbAccount getBithumbAddress() {
        throw new NotYetImplementedForExchangeException();
    }


    public Optional<BithumbWalletAddress> getBithumbWalletAddress(Currency currency) {
        throw new NotYetImplementedForExchangeException();
    }
}
