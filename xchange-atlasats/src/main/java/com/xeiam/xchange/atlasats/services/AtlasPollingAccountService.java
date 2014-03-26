package com.xeiam.xchange.atlasats.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class AtlasPollingAccountService extends BasePollingExchangeService
		implements PollingAccountService {

	public AtlasPollingAccountService(
			ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
		
	}

	@Override
	public AccountInfo getAccountInfo() throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {

		return null;
	}

	@Override
	public String withdrawFunds(String currency, BigDecimal amount,
			String address) throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String requestDepositAddress(String currency, String... args)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
