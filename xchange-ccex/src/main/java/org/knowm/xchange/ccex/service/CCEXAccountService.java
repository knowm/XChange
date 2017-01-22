package org.knowm.xchange.ccex.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEXAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;

public class CCEXAccountService extends CCEXAccountServiceRaw implements AccountService {

	public CCEXAccountService(Exchange exchange) {
		super(exchange);
	}

	@Override
	public AccountInfo getAccountInfo() throws IOException {
		return new AccountInfo(CCEXAdapters.adaptWallet(getCCEXAccountInfo()));
	}

	@Override
	public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws ExchangeException,
			NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
		throw new NotAvailableFromExchangeException();
	}

	@Override
	public String requestDepositAddress(Currency currency, String... args) throws IOException {
		return getCCEXDepositAddress(currency.toString().toUpperCase());
	}

}
