package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.bitmex.dto.BitmexWallet;
import org.knowm.xchange.bitmex.util.ApiClient;
import org.knowm.xchange.bitmex.util.ApiException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class BitmexAccountService extends BitmexAccountServiceRaw implements AccountService {


    /**
     * Constructor
     *
     * @param exchange
     */
    public BitmexAccountService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() {

        ApiClient swaggerApiClient = ((BitmexExchange) this.exchange).getSwaggerApiClient();
        UserApi u = new UserApi(swaggerApiClient);

        try {
            BitmexWallet xBt = u.userGetWallet(null);
            org.knowm.xchange.dto.account.Wallet wallet = new org.knowm.xchange.dto.account.Wallet(""+xBt.getAccount(),
                    Arrays.asList(new Balance(Currency.BTC,xBt.getAmount().divide(BigDecimal.valueOf( 100_000_000L))))
            );

            return  new AccountInfo(wallet);

        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {
        throw new NotYetImplementedForExchangeException();
    }

}
