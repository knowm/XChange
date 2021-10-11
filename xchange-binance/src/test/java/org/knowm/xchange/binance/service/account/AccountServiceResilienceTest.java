package org.knowm.xchange.binance.service.account;

import org.junit.Test;
import org.knowm.xchange.binance.AbstractResilienceTest;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AccountServiceResilienceTest extends AbstractResilienceTest {

    @Test(timeout = 2000)
    public void withdrawSuccess() throws Exception {
        String response = withdraw("withdraw-200.json", 200);
        assertThat(response).isEqualTo("9c7662xxxxxxxxxc8bd");
    }

    @Test(timeout = 2000)
    public void withdrawFailure() throws Exception {
        Throwable exception = catchThrowable(() -> withdraw("withdraw-400.json", 400));
        assertThat(exception).isInstanceOf(ExchangeSecurityException.class).hasMessage("error message (HTTP status code: 400)");
    }

    private String withdraw(String responseFileName, int statusCode) throws IOException {
        BinanceExchange exchange = createExchange();
        AccountService service = exchange.getAccountService();
        stubWithdraw(responseFileName, statusCode);

        return service.withdrawFunds(Currency.BTC, BigDecimal.TEN, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
    }


    private void stubWithdraw(String fileName, int statusCode) {
        stubFor(
                post(urlPathEqualTo("/sapi/v1/capital/withdraw/apply"))
                        .willReturn(
                                aResponse()
                                        .withStatus(statusCode)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(fileName)));
    }
}
