package org.knowm.xchange.blockchain.service.account;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.*;

public class AccountServiceTest extends BlockchainBaseTest {
    private AccountService service;

    @Before
    public void init() {
        BlockchainExchange exchange = createExchange();
        service = exchange.getAccountService();
    }

    @Test(timeout = 2000)
    public void getAccountInfoSuccess() throws Exception {
        AccountInfo response = getAccountInfo();
        System.out.println(response);
        Assert.assertNotNull(response);
    }

    @Test(timeout = 2000)
    public void withdrawSuccess() throws Exception {
        String response = withdraw(WITHDRAWAL_SUCCESS_JSON, 200);
        assertThat(response).isEqualTo(WITHDRAWAL_ID);
    }

    @Test(timeout = 2000)
    public void withdrawFailure() {
        Throwable exception = catchThrowable(() -> withdraw(WITHDRAWAL_FAILURE_JSON, 401));
        assertThat(exception)
                .isInstanceOf(ExchangeSecurityException.class)
                .hasMessage(STATUS_CODE_401);
    }

    @Test(timeout = 2000)
    public void requestDepositAddressSuccess() throws Exception {
        String response = requestDeposit(DEPOSIT_SUCCESS_JSON, 200);
        assertThat(response).isEqualTo(ADDRESS_DEPOSIT);
    }

    @Test(timeout = 2000)
    public void requestDepositAddressFailure() {
        Throwable exception = catchThrowable(() -> requestDeposit(DEPOSIT_FAILURE_JSON, 400));
        assertThat(exception)
                .isInstanceOf(ExchangeException.class)
                .hasMessage(STATUS_CODE_400);
    }

    @Test(timeout = 2000)
    public void getWithdrawFundingHistorySuccess() throws Exception {
        List<FundingRecord> response = withdrawFundingHistory();
        Assert.assertNotNull(response);

        response.forEach(
                record -> {
                    Assert.assertTrue( record.getAmount().compareTo(BigDecimal.ZERO) > 0);
                });
    }

    @Test(timeout = 2000)
    public void getDepositFundingHistorySuccess() throws Exception {
        List<FundingRecord> response = depositFundingHistory();
        Assert.assertNotNull(response);

        response.forEach(
                record -> {
                    Assert.assertTrue( record.getAmount().compareTo(BigDecimal.ZERO) > 0);
                });
    }

    @Test(timeout = 2000)
    public void getDynamicTradingFeesSuccess() throws Exception {
        Map<CurrencyPair, Fee> response = tradingFees();
        Assert.assertNotNull(response);
    }

    private AccountInfo getAccountInfo() throws IOException {
        stubGet(ACCOUNT_INFORMATION_JSON, 200, URL_ACCOUNT);

        return service.getAccountInfo();
    }

    private String withdraw(String responseFileName, int statusCode) throws IOException {
        stubPost(responseFileName, statusCode, URL_WITHDRAWALS);

        return service.withdrawFunds(
                Currency.BTC, BigDecimal.valueOf(0.005), ADDRESS);
    }

    private String requestDeposit(String responseFileName, int statusCode) throws IOException {
        stubPost(responseFileName, statusCode, URL_DEPOSIT_BY_CURRENCY);

        return service.requestDepositAddress(Currency.BTC);
    }

    private List<FundingRecord> withdrawFundingHistory() throws IOException {
        stubGet(WITHDRAWAL_HISTORY_SUCCESS_JSON, 200, URL_WITHDRAWALS);
        TradeHistoryParams params = service.createFundingHistoryParams();
        if (params instanceof HistoryParamsFundingType) {
            ((HistoryParamsFundingType) params).setType(FundingRecord.Type.WITHDRAWAL);
        }

        return service.getFundingHistory(params);
    }

    private List<FundingRecord> depositFundingHistory() throws IOException {
        stubGet(DEPOSIT_HISTORY_SUCCESS_JSON, 200, URL_DEPOSITS);
        TradeHistoryParams params = service.createFundingHistoryParams();
        if (params instanceof HistoryParamsFundingType) {
            ((HistoryParamsFundingType) params).setType(FundingRecord.Type.DEPOSIT);
        }

        return service.getFundingHistory(params);
    }

    private Map<CurrencyPair, Fee> tradingFees() throws IOException {
        stubGet(FEES_JSON, 200, URL_FEES);
        stubGet(SYMBOL_JSON, 200, URL_SYMBOLS);

        return service.getDynamicTradingFees();
    }

    private void stubPost(String fileName, int statusCode, String url) {
        stubFor(
                post(urlPathEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(statusCode)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(fileName)));
    }

    private void stubGet(String fileName, int statusCode, String url) {
        stubFor(
                get(urlPathEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(statusCode)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(fileName)));
    }

}
