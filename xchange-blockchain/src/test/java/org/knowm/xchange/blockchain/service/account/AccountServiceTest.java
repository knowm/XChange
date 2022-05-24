package org.knowm.xchange.blockchain.service.account;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class AccountServiceTest extends BlockchainBaseTest {
    private static AccountService service;

    @Before
    public void init() throws Exception {
        BlockchainExchange exchange = createExchange();
        service = exchange.getAccountService();
    }

    @Test(timeout = 2000)
    public void withdrawSuccess() throws Exception {
        String response = withdraw("withdraw-success.json", 200);
        assertThat(response).isEqualTo("3QXYWgRGX2BPYBpUDBssGbeWEa5zq6snBZ");
    }

    @Test(timeout = 2000)
    public void withdrawFailure() {
        Throwable exception = catchThrowable(() -> withdraw("withdraw-failure.json", 401));
        assertThat(exception)
                .isInstanceOf(ExchangeSecurityException.class)
                .hasMessage("Unauthorized (HTTP status code: 401)");
    }

    private String withdraw(String responseFileName, int statusCode) throws IOException {
        stubPost(responseFileName, statusCode, "/v3/exchange/withdrawals");

        return service.withdrawFunds(
                Currency.BTC, BigDecimal.TEN, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
    }

    @Test(timeout = 2000)
    public void requestDepositAddressSuccess() throws Exception {
        String response = requestDeposit("deposit-success.json", 200);
        assertThat(response).isEqualTo("3CrbF4Z45fnJs62jFs1p3LkR8KiZSGKJFL");
    }

    @Test(timeout = 2000)
    public void requestDepositAddressFailure() {
        Throwable exception = catchThrowable(() -> withdraw("deposit-failure.json", 400));
        assertThat(exception)
                .isInstanceOf(ExchangeException.class)
                .hasMessage("Bad Request (HTTP status code: 400)");
    }

    private String requestDeposit(String responseFileName, int statusCode) throws IOException {
        stubPost(responseFileName, statusCode, "/v3/exchange/deposits/BTC");

        return service.requestDepositAddress(Currency.BTC);
    }

    @Test(timeout = 2000)
    public void getWithdrawFundingHistorySuccess() throws Exception {
        List<FundingRecord> response = withdrawFundingHistory("withdrawHistory-success.json", 200);
        Assert.assertNotNull(response);

        response.forEach(
                record -> {
                    Assert.assertTrue( record.getAmount().compareTo(BigDecimal.ZERO) > 0);
                });
    }

    private List<FundingRecord> withdrawFundingHistory(String responseFileName, int statusCode) throws IOException {
        stubGet(responseFileName, statusCode, "/v3/exchange/withdrawals");
        TradeHistoryParams params = service.createFundingHistoryParams();
        if (params instanceof HistoryParamsFundingType) {
            ((HistoryParamsFundingType) params).setType(FundingRecord.Type.WITHDRAWAL);
        }
        List<FundingRecord> fundingHistory = service.getFundingHistory(params);

        return fundingHistory;
    }

    @Test(timeout = 2000)
    public void getDepositFundingHistorySuccess() throws Exception {
        List<FundingRecord> response = depositFundingHistory("depositHistory-success.json", 200);
        Assert.assertNotNull(response);

        response.forEach(
                record -> {
                    Assert.assertTrue( record.getAmount().compareTo(BigDecimal.ZERO) > 0);
                });
    }

    private List<FundingRecord> depositFundingHistory(String responseFileName, int statusCode) throws IOException {
        stubGet(responseFileName, statusCode, "/v3/exchange/deposits");
        TradeHistoryParams params = service.createFundingHistoryParams();
        if (params instanceof HistoryParamsFundingType) {
            ((HistoryParamsFundingType) params).setType(FundingRecord.Type.DEPOSIT);
        }
        List<FundingRecord> fundingHistory = service.getFundingHistory(params);

        return fundingHistory;
    }

    @Test(timeout = 2000)
    public void getDynamicTradingFeesSuccess() throws Exception {
        Map<CurrencyPair, Fee> response = tradingFees("fees.json", 200);
        Assert.assertNotNull(response);
    }

    private Map<CurrencyPair, Fee> tradingFees(String responseFileName, int statusCode) throws IOException {
        stubGet(responseFileName, statusCode, "/v3/exchange/fees");
        stubGet("symbols.json", statusCode, "/v3/exchange/symbols");
        Map<CurrencyPair, Fee> fees = service.getDynamicTradingFees();

        return fees;
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
