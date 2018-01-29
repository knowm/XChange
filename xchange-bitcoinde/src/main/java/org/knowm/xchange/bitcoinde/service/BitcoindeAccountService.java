package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.BitcoindeAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * 
 * @author kaiserfr
 *
 */
public class BitcoindeAccountService extends BitcoindeAccountServiceRaw implements AccountService {

	public BitcoindeAccountService(Exchange exchange) {
		super(exchange);
	}

	@Override
	public AccountInfo getAccountInfo() throws IOException {
		return BitcoindeAdapters.adaptAccountInfo(getBitcoindeAccount());
	}

	@Override
	public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}

	@Override
	public String withdrawFunds(WithdrawFundsParams params) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}

	@Override
	public String requestDepositAddress(Currency currency, String... args) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}

	@Override
	public TradeHistoryParams createFundingHistoryParams() {
		throw new NotYetImplementedForExchangeException();
	}

	@Override
	public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}

}
