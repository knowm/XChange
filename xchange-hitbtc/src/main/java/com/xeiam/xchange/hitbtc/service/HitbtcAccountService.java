package com.xeiam.xchange.hitbtc.service;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcBasePollingService;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class HitbtcAccountService extends HitbtcAccountServiceRaw implements PollingAccountService {

	public HitbtcAccountService(ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
	}

	@Override
	public AccountInfo getAccountInfo() throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {

		HitbtcBalance[] accountInfoRaw = getAccountInfoRaw();
		
		return HitbtcAdapters.adaptAccountInfo(accountInfoRaw);
	}

	@Override
	public String withdrawFunds(String currency, BigDecimal amount,
			String address) throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

	@Override
	public String requestDepositAddress(String currency, String... args)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

}
