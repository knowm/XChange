package com.xeiam.xchange.coinbaseex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExAccountService extends CoinbaseExAccountServiceRaw implements PollingAccountService {

	public CoinbaseExAccountService(Exchange exchange) {

		super(exchange);
	}

	@Override
	public AccountInfo getAccountInfo() throws ExchangeException,
	NotAvailableFromExchangeException,
	NotYetImplementedForExchangeException, IOException {

		this.getCoinbaseExAccountInfo();
		
		return null;
	}

	@Override
	public String withdrawFunds(String currency, BigDecimal amount, String address)
			throws ExchangeException, NotAvailableFromExchangeException,
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
}
